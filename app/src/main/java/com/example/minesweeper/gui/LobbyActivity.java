package com.example.minesweeper.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Space;
import android.widget.Spinner;

import com.example.minesweeper.R;
import com.example.minesweeper.listener.LobbyActivityListener;

public class LobbyActivity extends AppCompatActivity  {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init

    private LobbyActivityListener listener;

    private Spinner spSize;
    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout
        this.setContentView(R.layout.lobby_activity_layout);

        //Setting the Listener
        this.listener= new LobbyActivityListener(this);

        //Setting the spinner
        this.spSize=findViewById(R.id.spSize);
        setSpinner();
    }

    //endregion

    //region 3. Setting the Spinner
    private void setSpinner(){
        //Setting the adapter
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the spinner with the adapter
        spSize.setAdapter(adapter);

        //Setting the listener
        spSize.setOnItemSelectedListener(listener);

    }
    //endregion


}
