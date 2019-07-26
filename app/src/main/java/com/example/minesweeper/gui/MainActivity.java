package com.example.minesweeper.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.example.minesweeper.R;
import com.example.minesweeper.listener.MainActivityListener;
import com.example.minesweeper.model.NumberTable;

public class MainActivity extends AppCompatActivity {
    //region 0. Constants

    //endregion

    //region 1. Decl
    private TableLayout tblAllField;

    private MainActivityListener listener;
    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the Layout
        this.setContentView(R.layout.main_activity_layout);

        //Gender the Widgets
        this.tblAllField=findViewById(R.id.tblAllField);

        //Init widgets
        this.listener=new MainActivityListener(this,  tblAllField);

        //TODO Calculate the iSpaceSize and iImageSize
        this.listener.setImageSize(80);
        this.listener.setSpaceSize(600);

        //Creating the Table
        this.listener.populateButtons();

    }
    //endregion

    //region 3. Menu Generation and Handling

    @Override
    public boolean onCreateOptionsMenu(Menu mainActivityMenu) {
        //Inherited Method generates the Menu
        this.getMenuInflater().inflate(R.menu.main_activity_menu, mainActivityMenu);

        //Setting the Step MenuItem invisible
        mainActivityMenu.findItem(R.id.mnuStep).setVisible(false);

        //Setting the picture for the MenuItems
        this.listener.setMnuFlag(mainActivityMenu.findItem(R.id.mnuFlag));
        this.listener.setMnuStep(mainActivityMenu.findItem(R.id.mnuStep));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem clickedMnuItem) {
        return this.listener.onMenuItemClick(clickedMnuItem);
    }

    //endregion

}
