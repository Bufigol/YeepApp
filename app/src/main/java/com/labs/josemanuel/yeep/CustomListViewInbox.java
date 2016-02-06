package com.labs.josemanuel.yeep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by javier on 5/02/16.
 */
public class CustomListViewInbox extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public CustomListViewInbox(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);
        mContext = context;
        mMessages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = new ViewHolder();
        // View grid;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridlayout, null);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.inboxtext);
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.grid_image);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject message = mMessages.get(position);

        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
            holder.iconImageView.setImageResource(R.drawable.image_Image_Inbox);
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_IMAGE));
    }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_VIDEO)){
            holder.iconImageView.setImageResource(R.drawable.image_video_inbox);
            holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_IMAGE));
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
    }



}