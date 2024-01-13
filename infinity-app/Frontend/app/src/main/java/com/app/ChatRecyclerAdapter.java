package com.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.guild.*;

import org.json.JSONObject;

import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {

    public static String guildID;

    private List<ChatLink> mServers;
    private Context mContext;

    public ChatRecyclerAdapter(Context context, List<ChatLink> guilds){
        mServers = guilds;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_buttons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chatButton.setText(mServers.get(position).getChatName());
        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(mContext, GuildChatListActivity.class);
                //intent.putExtra("chatID", mServers.get(holder.getAdapterPosition()).getChatID());
                mContext.startActivity(intent);
                //chatID = mServers.get(holder.getAdapterPosition()).getChatID());

                 */
                Intent intent = new Intent(mContext, GuildActivity.class);
                intent.putExtra("chatID", mServers.get(holder.getAdapterPosition()).getChatID());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mServers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button chatButton;
        String chatID;
        String chatName;

        @SuppressLint("ResourceAsColor")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO --> create chat button in xml
            chatButton = itemView.findViewById(R.id.chat_button);
            chatButton.setBackgroundColor(R.color.dark_blue);
        }
    }

}
