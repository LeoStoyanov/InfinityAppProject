package com.friends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.net_utils.Const;
import com.app.User;

import org.json.JSONObject;

import java.util.ArrayList;
/**
 * @author Leo Stoyanov
 */

/**
 * Class handles the creation and formatting of the list of pending friends.
 */
public class PendingAdapter extends ArrayAdapter<PendingFriend> {

    private Context context;
    private int resource;
    private RequestQueue Queue;

    /**
     * Constructor assigns the context to the context of the class which uses the ListAdapter,
     * assigns the resource to a layout xml file, and passes an ArrayList of PendingFriend objects.
     *
     * @param context  context of the passed class
     * @param resource layout xml file
     * @param objects  Friend objects
     */
    public PendingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PendingFriend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        Queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Each pending friend within the pending friends list is assigned his or her respective attributes (i.e., display
     * name, username, ID, and image ID) in order for the friend to be displayed on the screen within
     * the list.
     *
     * @param position    position of the PendingFriend object
     * @param convertView pending friend within the list to be displayed
     * @param parent
     * @return returns the convertView
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(resource, parent, false);

        ImageView nonfriend_image = convertView.findViewById(R.id.friend_image);
        TextView nonfriend_name = convertView.findViewById(R.id.friend_name);
        TextView nonfriend_username = convertView.findViewById(R.id.friend_username);
        Button acceptFriend = convertView.findViewById(R.id.button_accept_friend);
        Button declineFriend = convertView.findViewById(R.id.button_decline_friend);

        nonfriend_image.setImageResource(getItem(position).imageId);
        nonfriend_name.setText(getItem(position).displayName);
        nonfriend_username.setText(getItem(position).username);
        acceptFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAcceptFriend(getItem(position).getId());
            }
        });
        declineFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDeclineFriend(getItem(position).getId());
            }
        });

        return convertView;
    }

    private void postAcceptFriend(int acceptFriendId) {
        String TAG = this.getClass().getSimpleName();
        JsonObjectRequest post_req = new JsonObjectRequest(Request.Method.POST,
                Const.ACCEPT_FRIEND + User.getUserID() + "/" + acceptFriendId, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        Queue.add(post_req);
    }

    private void postDeclineFriend(int declineFriendId) {
        String TAG = this.getClass().getSimpleName();
        JsonObjectRequest post_req = new JsonObjectRequest(Request.Method.POST,
                Const.DECLINE_FRIEND + User.getUserID() + "/" + declineFriendId, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        Queue.add(post_req);
    }
}