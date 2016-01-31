package com.labs.josemanuel.yeep;

import android.app.ListActivity;
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
    ArrayList<String> objectIds; // guardará el id de cada   usuario en un ArrayList de Strings


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        // iniciamos el progressBar Spinner
        pgrsBar = (ProgressBar) findViewById(R.id.progressBar2);
        // activar los checks Y permitir la selección múltiple de registros
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


    // onResume es llamado siempre que la actividad es mostrada
    @Override
    protected void onResume() {
        super.onResume();
        // Inicializamos el ArrayList, el adaptador y lo fijamos el adaptador al listView
        objectIds = new ArrayList<>();
        usernames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
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
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {
                    //sucess
                    mUsers = parseUsers;
                    // Recorre lista mUsers y añadimos a listView mediante el adaptador
                    for (ParseUser user : parseUsers) {
                        // guardará el id de cada usuario y su nombre
                        objectIds.add(user.getObjectId());
                        adapter.add(user.getUsername());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            EditFriendsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                    addFriendCheckmarks();
                } else {
                    Log.e(TAG, "Error al realizar la consulta: ", e);
                    errorEditFriendsdDialog(getString(R.string.error_message));
                }
                // oculta el progressBar al finalizar query
                pgrsBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    // Se ejecuta cada vez que pulsemos en algún elemento de la lista
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // comprobar que además de pulsar en un usuario, está seleccionado en check verificación
        if (getListView().isItemChecked(position)) {
            mFriendsRelation.add(mUsers.get(position));
            Toast.makeText(EditFriendsActivity.this, R.string.user_added_toast, Toast.LENGTH_SHORT).show();
            // añade una relación con un usuario seleccionado
            // recibirá un usuario que obtendremos del array de ParseUser mUsers con la variable position,
            // que es la posición del elemento pulsado*//*
        } else {
            // Eliminando de nuestra lista de amigos
            mFriendsRelation.remove(mUsers.get(position));
            Toast.makeText(EditFriendsActivity.this, R.string.user_deleted_toast, Toast.LENGTH_SHORT).show();
        }
        // en segundo plano añade el usuario en Parse
        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e != null) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    // Obtener las relaciones del usuario actual y añadir los checks
    private void addFriendCheckmarks() {

        mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {
                    //sucess
                    for (ParseUser user : parseUsers) {
                        Log.d(TAG, "id" + user.getObjectId());
                        if (objectIds.contains(user.getObjectId())) {
                            getListView().setItemChecked(objectIds.indexOf(user.getObjectId()), true);
                        }
                    }
                    pgrsBar.setVisibility(View.INVISIBLE);
                } else {
                    Log.e(TAG, "ParseException caught", e);
                    errorEditFriendsdDialog(getString(R.string.error_message));
                }
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
        builder.setMessage(string);
        builder.setTitle("Error saving");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}