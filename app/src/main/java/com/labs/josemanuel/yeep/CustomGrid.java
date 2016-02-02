package com.labs.josemanuel.yeep;

/**
 * Created by javier on 1/02/16.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] names;
    private final int[] Imageid;
    private String gravatarURL ="http://www.gravatar.com/avatar/HASH";
    FriendsFragment friends = new FriendsFragment();
   public String[] emails;
    public CustomGrid(Context c,String[] web,String[] emails, int[] Imageid ) {
        mContext = c;
        this.names = web;
        this.emails= emails;
        this.Imageid = Imageid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridlayout, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(names[position]);
            Log.i("CustomGrid.","About to check if the email it's empty or not.");
            if(emails[position].equals("")) {
                imageView.setImageResource(R.drawable.avatar_empty);
            }else{
                String hash = Hash.md5Hex(emails[position]);
                String gravatarurl ="http://gravatar.com/avatar/"+hash+"?s=100&d=wavatar";
                Picasso.with(mContext).load(gravatarurl).into(imageView);
            }

        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
