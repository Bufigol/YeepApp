package com.labs.josemanuel.yeep;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bufigol on 28/01/16.
 */
public class InboxFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.activity_inboxlist,container,
                false);
        return rootView;
    }
}