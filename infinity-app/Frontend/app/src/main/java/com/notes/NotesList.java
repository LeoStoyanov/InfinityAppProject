package com.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.dashboard.DashActivity;

import java.util.ArrayList;

public class NotesList extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();
    static NoteAdapter notesAdapter;
    ImageButton homeButton;
    ImageButton createNoteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        homeButton = findViewById(R.id.home_button);
        createNoteButton = findViewById(R.id.new_note);
        ListView notesList = findViewById(R.id.notes_list);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashActivity.class);
                startActivity(intent);
            }
        });

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
                startActivity(intent);
            }
        });

        notesAdapter = new NoteAdapter(this, R.layout.activity_note, notes); // Change this and create a note layout!
        notesList.setAdapter(notesAdapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NotesEditor.class);
                intent.putExtra("noteID", i);
                startActivity(intent);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemToDelete = i;
                new AlertDialog.Builder(NotesList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete);
                                notesAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
