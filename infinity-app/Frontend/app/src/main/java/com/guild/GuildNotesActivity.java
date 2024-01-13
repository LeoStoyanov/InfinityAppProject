package com.guild;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.R;
import com.loginscreen.IView;

import org.json.JSONArray;

public class GuildNotesActivity extends Activity implements IView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.guild_notes_list_activity);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void useResponse(JSONArray view) {

    }
}
