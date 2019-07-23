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

    private DisplayMetrics displayMetrics;

    private float displayHeight;
    private float displayWidht;
    private float imageHeight;
    private float imageWidth;
    private int imageSize;
    private int iSpaceSize;
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
        this.displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.displayHeight=displayMetrics.heightPixels;
        this.displayWidht=displayMetrics.widthPixels;

        this.imageHeight=displayHeight/NUMBER_ROWS;
        this.imageWidth=displayWidht/NUMBER_COLUMNS;

        this.imageSize=(int)min(imageHeight, imageWidth);

        this.iSpaceSize=(int)(displayWidht-displayHeight)/2;

        //Setting the Matrix
        populateButtons();

    }
    //endregion

    //region 3. Functions and Methods
    private void populateButtons(){
        //Setting the tables Margin
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(iSpaceSize, 0, iSpaceSize, 0);
        tblAllField.setLayoutParams(params);

        //Adding the size for the matrix
        this.allButton=new Button[NUMBER_ROWS+5][NUMBER_COLUMNS+5];

        for(int row=1;row<=NUMBER_ROWS;row++){

            //Create a new TableRow
            TableRow tableRow=new TableRow(this);

            //Setting the TableRow
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));

            //Add the TableRow to the TableLayout
            tblAllField.addView(tableRow);

            for(int column=1;column<=NUMBER_COLUMNS;column++){

                final int finalRow=row;
                final int finalColumn=column;

                //Init the Button
                allButton[row][column]=new Button(this);

                //Setting the Button
                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));

                //Set Background
                allButton[row][column].setBackgroundResource(R.drawable.one);

                //Set size

                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(imageSize, imageSize));


                //Add Listener
                allButton[row][column].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        ButtonClicked(finalRow, finalColumn);
                    }
                });


                //Add the Button to the TableRow
                tableRow.addView(allButton[row][column]);
            }
        }
    }

    private void ButtonClicked(int row, int column){
        //Toast
        Toast.makeText(this,
                "Row: " + String.valueOf(row)+ "\nColumn: " + String.valueOf(column),
                Toast.LENGTH_SHORT).show();

        //Set Background
        allButton[row][column].setBackgroundResource(R.drawable.question_mark);

    }
    private float min(float a, float b) {
        if(a<b)
            return a;
        else
            return b;
    }

    //endregion
}
