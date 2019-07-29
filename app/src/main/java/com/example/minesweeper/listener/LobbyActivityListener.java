package com.example.minesweeper.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.minesweeper.R;
import com.example.minesweeper.gui.LobbyActivity;

public class LobbyActivityListener implements AdapterView.OnItemSelectedListener, View.OnClickListener {
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

        int iTableSize=5;

        switch (strText){
            case "5*5":
                iTableSize=5;
                break;
            case "8*8":
                iTableSize=8;
                break;
            case "10*10":
                iTableSize=10;
                break;
            case "15*15":
                iTableSize=15;
                break;
        }

        activity.setTableSize(iTableSize);

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //endregion
    
    //region 4. Buttons is clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart :
                //Toast.makeText(activity, "Start", Toast.LENGTH_SHORT).show();
                activity.openGameActivity();
                break;
                
            case R.id.btnInfo:
                //Toast.makeText(activity, "Info", Toast.LENGTH_SHORT).show();
                activity.openInfoActivity();
                break;
        }
    }
    //endregion

}
