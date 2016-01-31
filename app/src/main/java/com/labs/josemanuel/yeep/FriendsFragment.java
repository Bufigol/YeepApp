package com.labs.josemanuel.yeep;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JoseManuel on 28/01/2016.
 */
public class FriendsFragment extends ListFragment {

    List<ParseUser> mUsers; // List de objetos ParseUsers  mUsers
    ArrayList<String> usernames; //  ArrayList de Strings  usernames
    ArrayAdapter<String> adapter; // ArrayAdapter de String adapter
    ParseRelation<ParseUser> mFriendsRelation; // Objeto ParseRelation asociado con objeto Usuario Logueado
    ParseUser mCurrentUser; // usuario actual
    ArrayList<String> objectIds;
    ProgressBar spinner;


    // Carga por primera vez e inflater infla el fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // infla la vista con el fragment_inbox
        View rootView = inflater.inflate(R.layout.fragment_friends, container,
                false);

        // oculta el progressBar
        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        usernames = new ArrayList<String>();
        objectIds = new ArrayList<String>();
        adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, usernames);
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
                    for (ParseUser user : mUsers) {
                        // añade el usuario de forma local
                        adapter.add(user.getObjectId());
                    }
                } else {
                    Log.e("FriendsFragment", "Error al realizar la consulta: ", e);
                }
                // oculta el progressBar al finalizar query
                spinner.setVisibility(View.INVISIBLE);

            }
        });

    }



}
