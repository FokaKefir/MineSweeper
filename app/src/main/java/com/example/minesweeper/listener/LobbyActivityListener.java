package com.example.minesweeper.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.minesweeper.gui.LobbyActivity;

public class LobbyActivityListener implements AdapterView.OnItemSelectedListener {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
    private LobbyActivity activity;
    //endregion


    //region 2. Constructor
    public LobbyActivityListener(LobbyActivity activity){
        this.activity=activity;
    }
    //endregion


    //region 3. Spinner listening functions and methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String strText = parent.getItemAtPosition(position).toString();

        Toast.makeText(activity, strText, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //endregion

}
