package com.example.minesweeper.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private Button btnStart;
    private Button btnInfo;

    private int iTableSize;

    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout
        this.setContentView(R.layout.lobby_activity_layout);

        //Setting the Listener
        this.listener= new LobbyActivityListener(this);

        //Setting the Variables
        setVariables();

        //Setting the spinner
        this.spSize=findViewById(R.id.spSize);
        setSpinner();

        //Setting the listening
        setObjectListeners();
    }

    //endregion

    //region 3. Setting the Variables
    private void setVariables(){
        this.spSize=findViewById(R.id.spSize);
        this.btnStart=findViewById(R.id.btnStart);
        this.btnInfo=findViewById(R.id.btnInfo);
    }
    //endregion

    //region 4. Setting the Spinner
    private void setSpinner(){
        //Setting the adapter
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the spinner with the adapter
        spSize.setAdapter(adapter);


    }
    //endregion

    //region 5. Setting the Listener
    private void setObjectListeners(){
        //Setting spinner
        spSize.setOnItemSelectedListener(listener);

        //Setting the Buttons
        btnStart.setOnClickListener(listener);
        btnInfo.setOnClickListener(listener);
    }
    //endregion

    //region 6. Getters and Setters

    public int getTableSize() {
        return iTableSize;
    }

    public void setTableSize(int iTableSize) {
        this.iTableSize = iTableSize;
    }

    //endregion

    //region 7. TODO jumping into a new activity
    public void openInfoActivity(){
        //Creating intent
        Intent  intent = new Intent(this, InfoActivity.class);

        //Starting the InfoActivity
        startActivity(intent);
    }

    public void openGameActivity(){
        //Creating intent
        Intent intent = new Intent(this, GameActivity.class);

        //Putting the Extra
        String strName=String.valueOf(R.string.size);
        intent.putExtra(strName, iTableSize);

        //Starting the GameActivity
        startActivity(intent);
    }
    //endregion

}
