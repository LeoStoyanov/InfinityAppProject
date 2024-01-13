package com.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import com.guild.GuildActivity;

import java.util.List;

public class GuildRecyclerAdapter extends RecyclerView.Adapter<GuildRecyclerAdapter.ViewHolder> {

    public static String guildID;

    private List<Server> mServers;
    private Context mContext;

    public GuildRecyclerAdapter(Context context, List<Server> guilds){
        mServers = guilds;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_buttons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.server_button.setImageResource(R.drawable.guild_default_pic);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        ImageRequest imageRequest = new ImageRequest( mServers.get(position).getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.server_button.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(imageRequest);

        holder.server_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GuildActivity.class);
                intent.putExtra("image_url", mServers.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("guildID", mServers.get(holder.getAdapterPosition()).getServerID());
                mContext.startActivity(intent);
                guildID = mServers.get(holder.getAdapterPosition()).getServerID();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mServers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton server_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            server_button = itemView.findViewById(R.id.server_button);
            server_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
