package com.labs.josemanuel.yeep;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoseManuel on 27/01/2016.
 */
public class InboxFragment extends ListFragment {

    protected List<ParseObject> mMessages;
    private ArrayList<String> message;
    private ArrayAdapter adapter;
    private ProgressBar spinner;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String TAG = InboxFragment.class.getSimpleName();

    // Carga por primera vez y fragmenter y lo infla
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // infla la vista con el fragment_inbox
        View rootView = inflater.inflate(R.layout.fragment_inbox, container,
                false);

        // oculta el progressBar
        spinner = (ProgressBar)
                rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.SwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        return rootView;
    }
    @Override
    public void onResume(){
        super.onResume();
        reloadMessage();
    }

    private void reloadMessage() {
        message = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENTS_ID, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    mMessages = parseObjects;
                    for (ParseObject message : mMessages) {
                        adapter.add(message.getString(ParseConstants.KEY_SENDER_ID));
                    }

                    spinner.setVisibility(View.INVISIBLE);
                    MessageAdapter adapter = new MessageAdapter(
                            getListView().getContext(),
                            mMessages);
                    setListAdapter(adapter);
                } else {
                    Log.e(TAG, "ParseExeception caught: ", e);
                    errorEditFriendsFragment(getString(R.string.error_message));

                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l,v,position,id);
        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file =  message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());
        if(fileUri != null){
            if(messageType.equals(ParseConstants.TYPE_IMAGE)){
                Intent i = new Intent(getActivity(),ViewImageActivity.class);
                i.setData(fileUri);
                startActivity(i);
            }else{
                Intent i = new Intent(Intent.ACTION_VIEW,fileUri);
                i.setDataAndType(fileUri,"video/*");
                startActivity(i);
            }
        }

        List<String>ids=message.getList(ParseConstants.KEY_RECIPIENTS_ID);

        if(ids.size()>1){
            ids.remove(ParseUser.getCurrentUser().getObjectId());
            ArrayList<String>idsToRemove=new ArrayList();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
            message.removeAll(ParseConstants.KEY_RECIPIENTS_ID,idsToRemove);
        }else{
            message.deleteInBackground();
        }

    }

    private void errorEditFriendsFragment(String mensaje) {

        final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(InboxFragment.this.getContext());
        Log.d(TAG, " -*- El popup Dialog se ha creado -*-");
        alertaSimple.setTitle("Error");
        alertaSimple.setMessage(mensaje);
        alertaSimple.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  setContentView(R.layout.activity_main);

            }
        });

        alertaSimple.setIcon(R.mipmap.ic_launcher);
        alertaSimple.create();
        alertaSimple.show();
    }

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh(){
            reloadMessage();
        }
    };
}









