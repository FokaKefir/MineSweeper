package com.example.minesweeper.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.minesweeper.R;

public class MainActivity extends AppCompatActivity {
    //region 0. Constants
    private static final int NUMBER_ROWS=1;
    private static final int NUMBER_COLUMNS=1;
    //endregion

    //region 1. Decl and Init
    private TableLayout tblAllField;

    private Button[][] allButton;
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

        //


    }
    //endregion

}
