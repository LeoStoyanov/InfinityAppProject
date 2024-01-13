package com.friends;

import static com.friends.FriendsList.friendArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.*;
import com.notes.NotesList;

import java.util.ArrayList;
/**
 * @author Leo Stoyanov
 */

/**
 * Class handles the creation and formatting of the friends list.
 */
public class ListAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private int resource;

    /**
     * Constructor assigns the context to the context of the class which uses the ListAdapter,
     * assigns the resource to a layout xml file, and passes an ArrayList of Friend objects.
     * @param context context of the passed class
     * @param resource layout xml file
     * @param objects Friend objects
     */
    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Friend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * Each friend within the friends list is assigned his or her respective attributes (i.e., display
     * name, username, ID, and image ID) in order for the friend to be displayed on the screen within
     * the list.
     * @param position position of the Friend object
     * @param convertView friend within the list to be displayed
     * @param parent
     * @return returns the convertView
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(resource, parent, false);

        ImageView friend_image = convertView.findViewById(R.id.friend_image);
        TextView friend_name = convertView.findViewById(R.id.friend_name);
        TextView friend_username = convertView.findViewById(R.id.friend_username);

        friend_image.setImageResource(getItem(position).imageId);
        friend_name.setText(getItem(position).displayName);
        friend_username.setText(getItem(position).username);

        return convertView;
    }
}
