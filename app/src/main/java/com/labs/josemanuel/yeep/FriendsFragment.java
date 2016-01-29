package com.labs.josemanuel.yeep;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * Created by JoseManuel on 28/01/2016.
 */
public class FriendsFragment extends ListFragment {

    // Carga por primera vez e inflater infla el fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // infla la vista con el fragment_inbox
        View rootView = inflater.inflate(R.layout.fragment_friends, container,
                false);

        // oculta el progressBar
        ProgressBar spinner = (ProgressBar)
                rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        return rootView;
    }


}
