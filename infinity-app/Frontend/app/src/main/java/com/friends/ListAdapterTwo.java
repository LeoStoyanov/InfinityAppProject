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

import com.R;
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
 * Class handles the creation and formatting of the friends list.
 */
public class ListAdapterTwo extends ArrayAdapter<NonFriend> {

    private Context context;
    private int resource;
    private RequestQueue Queue;

    /**
     * Constructor assigns the context to the context of the class which uses the ListAdapter,
     * assigns the resource to a layout xml file, and passes an ArrayList of Friend objects.
     * @param context context of the passed class
     * @param resource layout xml file
     * @param objects Friend objects
     */
    public ListAdapterTwo(@NonNull Context context, int resource, @NonNull ArrayList<NonFriend> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        Queue = Volley.newRequestQueue(context.getApplicationContext());
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

        ImageView nonfriend_image = convertView.findViewById(R.id.friend_image);
        TextView nonfriend_name = convertView.findViewById(R.id.friend_name);
        TextView nonfriend_username = convertView.findViewById(R.id.friend_username);
        Button addFriend = convertView.findViewById(R.id.button_addfriend);

        nonfriend_image.setImageResource(getItem(position).imageId);
        nonfriend_name.setText(getItem(position).displayName);
        nonfriend_username.setText(getItem(position).username);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAddFriend(getItem(position).getId());
            }
        });

        return convertView;
    }


    public void postAddFriend(int addFriendId) {
        String TAG = this.getClass().getSimpleName();

        JsonObjectRequest post_req = new JsonObjectRequest(Request.Method.POST,
                Const.REQUEST_FRIEND + User.getUserID() + "/" + addFriendId, null,
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
