package com.labs.josemanuel.yeep;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JoseManuel on 28/01/2016.
 */
public class Recipients extends ListActivity {
    public static final String TAG = Recipients.class.getSimpleName();
    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    public ParseUser mCurrentUser;
    protected GridView grid;
    protected TextView empty;
    protected String[] usernames;
    protected int[] images;
    public String[] emails;
    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);
        // iniciamos el progressBar Spinner
        //pgrsBar = (ProgressBar) findViewById(R.id.progressBar2);
        // activar los checks Y permitir la selección múltiple de registros
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


    @Override
    public void onResume() {
        super.onResume();
        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        this.setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {


                if (e == null) {
                    mFriends = friends;
                    usernames = new String[mFriends.size()];
                    emails = new String[usernames.length];
                    Log.i("FriendsFragment.", "Just finishing to set the size of the email array, which it will be: " + emails.length);
                    int i = 0;

                    for (ParseUser user : mFriends) {
                        usernames[i] = user.getUsername();
                        emails[i] = user.getEmail().toLowerCase();
                        i++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            Recipients.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                    if (mFriends.size() > 0) {
                        Log.i("FriendsFragment.", "Empty array.");
                        empty.setVisibility(View.INVISIBLE);
                    } else {
                        empty.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e(TAG, e.getMessage());

                    AlertDialog.Builder builder = new AlertDialog.Builder(Recipients.this);
                    builder.setTitle(R.string.error_message)
                            .setMessage(e.getMessage())
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }
}