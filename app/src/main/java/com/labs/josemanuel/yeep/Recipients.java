package com.labs.josemanuel.yeep;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Recipients extends ListActivity {
    public static final String TAG = Recipients.class.getSimpleName();
    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    public ParseUser mCurrentUser;
    protected TextView empty;
    protected String[] usernames;
    protected int[] images;
    public String[] emails;
    public String email;
    public ImageButton btnsend;
    private Uri mMediaUri;
    private String mFileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);
        btnsend = (ImageButton) findViewById(R.id.sendbutton);
        btnsend.setVisibility(View.INVISIBLE);
        // iniciamos el progressBar Spinner
        //pgrsBar = (ProgressBar) findViewById(R.id.progressBar2);
        // activar los checks Y permitir la selección múltiple de registros
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Intent intent = getIntent();
        mMediaUri = intent.getData();
        mFileType = intent.getStringExtra(ParseConstants.KEY_FILE_TYPE);
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
                    Log.i(TAG, "About to load the arrayadapter.");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            Recipients.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    if (mFriends.size() > 0) {
                        Log.i("FriendsFragment.", "Not empty array.");
                    } else {
                        // empty.setVisibility(View.VISIBLE);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mSendItem;
        getMenuInflater().inflate(R.menu.menu_recipients, menu);
        mSendItem = menu.getItem(0);
        return true;
    }

    public void showAction(View view) {
        Log.i(TAG, "Pushing send friends.");
        if (createMessage() == null) {
            //mensaje de error
        } else {
            send(createMessage());
            finish();
        }
    }

    private void send(final ParseObject message) {
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(Recipients.this, "¡Mensaje enviado", Toast.LENGTH_SHORT).show();
                } else {
                    //mensaje de error
                    Toast.makeText(Recipients.this, "¡Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (l.getCheckedItemCount() > 0) {
            btnsend.setVisibility(View.VISIBLE);
        } else {
            btnsend.setVisibility(View.INVISIBLE);
        }
    }


    private ArrayList<String> getRecipientsIds() {
        ArrayList<String> recipientList = new ArrayList<>();
        for (int i = 0; i < getListView().getCount(); i++) {
            if (getListView().isItemChecked(i)) {
                recipientList.add(mFriends.get(i).getObjectId());
            }
        }
        return recipientList;
    }

    private ParseObject createMessage() {
        ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
        message.put(ParseConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
        message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
        message.put(ParseConstants.KEY_RECIPIENTS_ID, getRecipientsIds());
        message.put(ParseConstants.KEY_FILE_TYPE, mFileType);
        if(mFileType.equals(ParseConstants.TYPE_IMAGE)){
            byte[] fileBytes = FileHelper.getByteArrayFromFile(this, mMediaUri);
            fileBytes = FileHelper.reduceImageForUpload(fileBytes);
            String fileName = FileHelper.getFileName(this, mMediaUri, mFileType);
            ParseFile file = new ParseFile(fileName, fileBytes);
            message.put(ParseConstants.KEY_FILE, file);
        }
        if(mFileType.equals(ParseConstants.TYPE_VIDEO)){
            try{
                InputStream inputStream = this.getContentResolver().openInputStream(mMediaUri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytesFromFile = new byte[1024*1024];
                int bytesReaded = inputStream.read(bytesFromFile);
                while(bytesReaded !=-1){
                    outputStream.write(bytesFromFile,0,bytesReaded);
                    bytesReaded = inputStream.read(bytesFromFile);
                }
                byte[] fileBytes = outputStream.toByteArray();
                String fileName = FileHelper.getFileName(this, mMediaUri, mFileType);
                ParseFile file = new ParseFile(fileName, fileBytes);
                message.put(ParseConstants.KEY_FILE, file);
                inputStream.close();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
