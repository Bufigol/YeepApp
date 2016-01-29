package com.labs.josemanuel.yeep;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



/**
 * Created by bufigol on 28/01/16.
 */
public class FriendsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){

        View rootView=inflater.inflate(R.layout.activity_userlist,container,
                false);


        /*ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(
               this.getContext(), R.layout.activity_userlist, R.id.list);
        setListAdapter(adapter);*/
        return rootView;
    }
}