package com.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.R;
import com.friends.Friend;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int resource;

    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        String fitTitle;
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView note_title = convertView.findViewById(R.id.single_note_title);
        TextView note_date = convertView.findViewById(R.id.single_note_date);

        if (getItem(position).getTitle().length() >= 16) {
            fitTitle = getItem(position).getTitle().substring(0, 16)+ "...";

        } else {
            fitTitle = getItem(position).getTitle();
        }

        note_title.setText(fitTitle);
        note_date.setText(getItem(position).getDate());

        return convertView;
    }
}
