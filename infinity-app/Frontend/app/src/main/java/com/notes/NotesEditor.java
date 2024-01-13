package com.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesEditor extends AppCompatActivity {

    ImageButton backButton;
    EditText editNoteDesc;
    EditText editNoteTitle;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        backButton = findViewById(R.id.back_note);
        editNoteDesc = findViewById(R.id.edit_note_desc);
        editNoteTitle = findViewById(R.id.edit_note_title);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteID", -1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotesList.class);
                startActivity(intent);
            }
        });

        if (noteId != -1) {
            editNoteTitle.setText(NotesList.notes.get(noteId).getTitle());
            editNoteDesc.setText(NotesList.notes.get(noteId).getDesc());
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
            Date date = new Date();
            NotesList.notes.add(new Note("Title here...", "Description here...", dateFormat.format(date)));
            noteId = NotesList.notes.size() - 1;
            NotesList.notesAdapter.notifyDataSetChanged();
        }

        editNoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();
                NotesList.notes.get(noteId).setTitle(String.valueOf(charSequence));
                NotesList.notes.get(noteId).setDate(dateFormat.format(date));
                NotesList.notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editNoteDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();
                NotesList.notes.get(noteId).setDesc(String.valueOf(charSequence));
                NotesList.notes.get(noteId).setDate(dateFormat.format(date));
                NotesList.notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}