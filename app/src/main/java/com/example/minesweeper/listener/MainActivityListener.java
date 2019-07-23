package com.example.minesweeper.listener;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.minesweeper.R;
import com.example.minesweeper.gui.MainActivity;

public class MainActivityListener {
    //region 0. Constants
    private static final int NUMBER_ROWS=1;
    private static final int NUMBER_COLUMNS=1;
    //endregion

    //region 1. Decl and Init
    private TableLayout tblAllField;

    private MainActivity activity;

    private Button[][] allButton;

    private int iSpaceSize;
    private int iImageSize;
    //endregion

    //region 2. Constructor
    public MainActivityListener(MainActivity activity, TableLayout tblAllField){
        this.activity=activity;
        this.tblAllField=tblAllField;
    }
    //endregion

    //region 3. Getters and Setters

    public int getImageSize() {
        return iImageSize;
    }
    public int getSpaceSize() {
        return iSpaceSize;
    }

    public void setImageSize(int iImageSize) {
        this.iImageSize = iImageSize;
    }
    public void setSpaceSize(int iSpaceSize) {
        this.iSpaceSize = iSpaceSize;
    }

    //endregion

    //region 4. Functions and Methods
    public void populateButtons(){
        //Setting the tables Margin
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(this.iSpaceSize, 0, this.iSpaceSize, 0);
        tblAllField.setLayoutParams(params);

        //Adding the size for the matrix
        this.allButton=new Button[NUMBER_ROWS+5][NUMBER_COLUMNS+5];

        for(int row=1;row<=NUMBER_ROWS;row++){

            //Create a new TableRow
            TableRow tableRow=new TableRow(activity);

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
                allButton[row][column]=new Button(activity);

                //Setting the Button
                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));

                //Set Background
                allButton[row][column].setBackgroundResource(R.drawable.one);

                //Set size

                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(this.iImageSize, this.iImageSize));


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

    public void ButtonClicked(int row, int column){
        //Toast
        Toast.makeText(activity,
                "Row: " + String.valueOf(row)+ "\nColumn: " + String.valueOf(column),
                Toast.LENGTH_SHORT).show();

        //Set Background
        allButton[row][column].setBackgroundResource(R.drawable.question_mark);

    }

    //endregion


}
