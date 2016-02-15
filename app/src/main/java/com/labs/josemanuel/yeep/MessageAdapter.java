package com.labs.josemanuel.yeep;

/**
 * Created by JoseManuel on 07/02/2016.
 */
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
 * Created by JoseManuel on 05/02/2016.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {


    protected Context mContext;
    protected List<ParseObject> mMessages;

    // constructor
    public MessageAdapter(Context context, List<ParseObject>messages){
        super(context, R.layout.message_item, messages); // asignado el layout al padre
        mContext = context;
        mMessages = messages;

    }

    // el  adaptador llama a un  metodo para incorporar la lista, que es el layout message_item
    // recicla la vista si ya existe. Primero crea la lista y luego lo infla
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder; // se va inflando la unica vista con las filas y  contenido

        // si no existe la vista, la crea y la infla
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
        }
        else{
            // si ya esta creado, obtiene la vista de fila que estaba creada
            holder = (ViewHolder)convertView.getTag();
        }

        // colocar los datos dentro de la lista

        ParseObject message = mMessages.get(position);
        // pregunta a parse si es imagen o  video

        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
            holder.iconImageView.setImageResource(R.drawable.ic_picture);

        }else{
            holder.iconImageView.setImageResource(R.drawable.ic_video);
        }

        holder.iconImageView.setImageResource(R.drawable.ic_picture); // ic_picture
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));

        return convertView;
    }

    private static class ViewHolder{
        ImageView iconImageView;
        TextView nameLabel;
    }


}
