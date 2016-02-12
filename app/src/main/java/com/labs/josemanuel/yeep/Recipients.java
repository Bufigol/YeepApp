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


public class Recipients extends ListActivity {

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
        objectIds = new ArrayList<String>();
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

        realizarConsulta();

    }

    // Se ejecuta cada vez que pulsemos en algún elemento de la lista
    @Override
    protected void onListItemClick(ListView l,View v, int position, long id)
    {
     super.onListItemClick(l,v,position, id);
        setVisible(true);
    }



    /**
     * Método con el cual se realiza la consulta con el backend para obtener las relaciones que tiene
     * el usuario actual. Una vez obtenidas las relaciones, se le muestran al usuario en forma
     * de lista.
     */
    private void realizarConsulta() {
        ParseQuery query = ParseUser.getQuery();
        //orden result ascendent  // campo por el que vamos a ordenarlo (Extract to ParseConnstant)
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS); // limite de consulta
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
                            Recipients.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                    addFriendCheckmarks();
                } else {
                    Log.e(TAG, "Error al realizar la consulta: ", e);
                    showErrorMsg("Error interno", getString(R.string.error_message));
                }
                // oculta el progressBar al finalizar query
                pgrsBar.setVisibility(View.INVISIBLE);

            }
        });
    }



    /**
     * Obtener las relaciones del usuario actual y añadir los checks
     *
     */
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
                    showErrorMsg("Error loading Friends", getString(R.string.error_message));
                }
            }
        });

    }

    // metodo para ParseException capturada en la carga de lista de amigos

    /**
     * Método generico utilizado para mostrar un mensaje de alerta en caso de que se haya experimentado
     * algun error de funcionamiento dentro de esta clase.
     * @param title String utilizado para definir el titulo del mensaje de alerta
     * @param message String que contiene el mensaje que se le va mostrar al usuario.
     */
    private void showErrorMsg(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}