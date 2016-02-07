package com.labs.josemanuel.yeep;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by JoseManuel on 27/01/2016.
 */
public class InboxFragment extends ListFragment {


    protected List<ParseObject> mMessages;

    // Carga por primera vez y fragmenter y lo infla
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // infla la vista con el fragment_inbox
        View rootView = inflater.inflate(R.layout.fragment_inbox, container,
                false);

        // oculta el progressBar
        ProgressBar spinner = (ProgressBar)
                rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        return rootView;
    }




}










