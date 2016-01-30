package com.labs.josemanuel.yeep;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class EditFriendsActivity extends ListActivity {

    private static final String TAG = "Error Users";

    List<ParseUser> mUsers; // List de objetos ParseUsers  mUsers
    ArrayList<String> usernames; //  ArrayList de Strings  usernames
    ArrayAdapter<String> adapter; // ArrayAdapter de String adapter
    ProgressBar pgrsBar;
    ParseRelation<ParseUser> mFriendsRelation; // Objeto ParseRelation asociado con objeto Usuario Logueado
    ParseUser mCurrentUser; // usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        // iniciamos el progressBar
        pgrsBar = (ProgressBar) findViewById(R.id.progressBar2);
        // activar los checks Y permitir la selección múltiple de registros
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


    }


    // Se ejecuta cada vez que pulsemos en algún elemento de la lista

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast toast = Toast.makeText(getApplicationContext(), "Amigo seleccionado", Toast.LENGTH_SHORT);
        toast.show();

        // añade una relación con un usuario seleccionado
        /*recibirá un usuario que obtendremos del array de ParseUser mUsers con la variable position,
        que es la posición del elemento pulsado*/
        mFriendsRelation.add(mUsers.get(position));

        // en segundo plano añade el usuario en Parse
        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // control si error..
                if (e == null) {

                    Log.d(TAG, " -*- Relación editada satisfactoriamente -*-");

                } else {
                    Log.d(TAG, " -*- Error durante el proceso de relación -*-");

                }

            }
        });


    }


    // onResume es llamado siempre que la actividad es mostrada

    @Override
    protected void onResume() {
        super.onResume();

        // Inicializamos el ArrayList, el adaptador y lo fijamos el adaptador al listView

        usernames = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, usernames);
        setListAdapter(adapter);

        // objeto query va a guardar una lista de objetos ParseUser
        ParseQuery query = ParseUser.getQuery();
        //orden result ascendent  // campo por el que vamos a ordenarlo (Extract to ParseConnstant)
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS); // limite de consulta

        mCurrentUser = ParseUser.getCurrentUser(); // obtener el usuario actual

        // instanciar la relación // recibe el nombre de la relación con una constante
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);


        // ejecutar la consulta en segundo plano
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    //sucess
                    // Recorremos la lista de usuarios de mUsers
                    // y la añadimos a listView mediante el adaptador
                    mUsers = users;
                    for (ParseUser user : mUsers) {
                        // añade el usuario de forma local
                        adapter.add(user.getUsername());


                    }

                } else {
                    Log.e(TAG, "Error al realizar la consulta: ", e);
                    errorEditFriendsdDialog(getString(R.string.error_message));
                }
                // oculta el progressBar al finalizar query
                pgrsBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    // metodo para ParseException capturada en la carga de lista de amigos
    private void errorEditFriendsdDialog(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditFriendsActivity.this);
        builder.setMessage(string);
        builder.setTitle("Error loading Friends");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // metodo de error al salvar relaciones en Parse.com
    private void errorSavingRelation(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditFriendsActivity.this);
        String message = string;
        builder.setMessage(message);
        builder.setTitle("Error saving");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
