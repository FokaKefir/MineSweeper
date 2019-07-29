package com.example.minesweeper.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.minesweeper.R;
import com.example.minesweeper.listener.MainActivityListener;
import com.example.minesweeper.model.NumberTable;

public class MainActivity extends AppCompatActivity {
    //region 0. Constants
    private static final int DEF_NUMBER_OF_ROWS=10;
    //endregion

    //region 1. Decl
    private TableLayout tblAllField;

    private TextView txtvRestFlags;

    private MainActivityListener listener;

    private int iImageSize;
    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the Layout
        this.setContentView(R.layout.main_activity_layout);

        //Gender the Widgets
        this.tblAllField=findViewById(R.id.tblAllField);
        this.txtvRestFlags=findViewById(R.id.txtvRestFlags);

        //Init widgets
        this.listener=new MainActivityListener(this);

        //Calculate the iSpaceSize and iImageSize
        calculatingTableResolution();

        //Setting the Variables on the listener
        setVariablesOnListener();

        //Creating the Table
        this.listener.declVariables();
        this.listener.populateButtons();


    }
    //endregion

    //region 3. Setting the Variables on the listener
    private void setVariablesOnListener(){
        this.listener.setTblAllField(this.tblAllField);
        this.listener.setTxtvRestFlags(this.txtvRestFlags);
        this.listener.setiRow(this.DEF_NUMBER_OF_ROWS);
        this.listener.setImageSize(iImageSize);
        this.listener.setSpaceSize(0);
    }
    //endregion

    //region 4. Menu Generation and Handling

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

    //region 5. Calculating the table resolution
    private void calculatingTableResolution()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(this.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        iImageSize=screenWidth/DEF_NUMBER_OF_ROWS;
    }
    //endregion
}
