package com.labs.josemanuel.yeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    // constantes para cada una de las acciones, numero que lo identifica (requestCode)
    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int PICK_VIDEO_REQUEST = 3;
    static final int FILE_SIZE_LIMIT = 10485760;
    protected Uri mMediaUri; // permite identificar ficheros

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpened(getIntent());
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            // navigateToLogin();
            Log.i(TAG, currentUser.getUsername());
        } else {
            Log.i(TAG, currentUser.getUsername());
        }

/*        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); // lo centra en landscape
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_archive_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_people_24dp);


        // Sobre de enviar mail situado en esquina inferior derecha de Main
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


     /*  private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    // creado un array de strings llamado camera_choices en el archivo strings

    // creando metod dialogCameraChoices para la declaracion del dialogo de la opcion camara
    // se encargara de mostrar el dialogo con las opciones
    public void dialogCameraChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.camera_choices, mDialogListener());
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    // Creado metodo mDialogListener que implementa variable dialogListener
    // de tipo DialogInterface.OnClickListener que sobreescribe el método onClick.
    // onClick implementa un switch-case que se encaragar� de realizar los distintos
    // Intents correspondientes a las opciones de la camara
    private DialogInterface.OnClickListener mDialogListener() {
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                        mMediaUri = FileUtilities.getOutputMediaFileUri(FileUtilities.MEDIA_TYPE_IMAGE);
                        // Si no existe identificador
                        if (mMediaUri == null) {
                            mensajeAlerta();
                           // Toast.makeText(MainActivity.this, R.string.error_external_storage, Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Error en el almacenamiento externo");
                        } else {
                            // a�adiremos informacion extra al intent
                            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);

                            Log.i(TAG, "Take Photo Option is selected");
                        }
                        break;

                    case 1:
                        Log.i(TAG, "Take Video Option is selected");
                        break;

                    case 2:
                        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        choosePhotoIntent.setType("image/*");
                        startActivityForResult(choosePhotoIntent,PICK_PHOTO_REQUEST);
                        Log.i(TAG, "Choice Photo Option is selected");
                        break;

                    case 3:
                        Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        chooseVideoIntent.setType("video/*");
                        startActivityForResult(chooseVideoIntent, PICK_VIDEO_REQUEST);
                        Log.i(TAG, "Choice Video Option is selected");
                        break;

                }

            }
        };
        return dialogListener;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT_OK && requestCode == PICK_VIDEO_REQUEST){
            if(data != null){
                try {
                    mMediaUri = data.getData();
                    InputStream in =getContentResolver().openInputStream(mMediaUri);
                    int fileSize = in.available();
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                mensajeAlerta();
            }

            Intent intent = new Intent(MainActivity.this, Recipients.class);
            intent.setData(mMediaUri);
            startActivity(intent);
        }
        if(requestCode == RESULT_OK && requestCode == PICK_PHOTO_REQUEST){
            if(data != null){
                mMediaUri = data.getData();
                Intent intent = new Intent(MainActivity.this,Recipients.class);
                intent.setData(mMediaUri);
                startActivity(intent);
            }else{
                mensajeAlerta();
            }
        }
    }
    // modificado el metodo por un bloque switch-case en lugar de bloque if

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                ParseUser.logOut();
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                intentLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLogout);

                break;

            case R.id.action_edit_Friends:
                Intent intentFriends = new Intent(MainActivity.this, EditFriendsActivity.class);
                startActivity(intentFriends);

                break;

            case R.id.action_repositorio:
                Intent intentRepo = new Intent(MainActivity.this, WebviewActivity.class);
                startActivity(intentRepo);

                break;

            case R.id.action_camera:
                dialogCameraChoices();
                break;

            case R.id.action_send:
                dialogCameraChoices();
                break;


        }

        return super.onOptionsItemSelected(item);

    }


    // metodo para el mensaje de Alerta
    private void mensajeAlerta() {

        final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(MainActivity.this);
        Log.d(TAG, " -*- El popup Dialog se ha creado -*-");
        alertaSimple.setTitle("Error Storage");
        alertaSimple.setMessage("Error con el almacenamiento externo");
        alertaSimple.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setContentView(R.layout.activity_main);

            }
        });

        alertaSimple.setIcon(R.mipmap.ic_launcher);
        alertaSimple.create();
        alertaSimple.show();
    }


}