package com.example.minesweeper.listener;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.minesweeper.R;
import com.example.minesweeper.gui.MainActivity;
import com.example.minesweeper.model.NumberTable;

public class MainActivityListener implements MenuItem.OnMenuItemClickListener {
    //region 0. Constants
    private static final int NUMBER_ROWS=10;
    private static final int NUMBER_COLUMNS=10;

    private static final String DEF_STEP="step";
    private static final String DEF_FLAG="flag";
    //endregion

    //region 1. Decl
    private MenuItem mnuStep;
    private MenuItem mnuFlag;

    private TableLayout tblAllField;

    private NumberTable numberTable;

    private MainActivity activity;

    private Button[][] allButton;
    private boolean[][] allStepPosition;
    private boolean[][] allFlagPosition;

    private int iSpaceSize;
    private int iImageSize;
    private boolean firstClick;
    private boolean fillingIsOn;

    private String strClick;
    //endregion

    //region 2. Constructor
    public MainActivityListener(MainActivity activity, TableLayout tblAllField){
        this.activity=activity;
        this.tblAllField=tblAllField;

        this.firstClick=true;
        this.strClick=DEF_STEP;

    }
    //endregion

    //region 3. Getters and Setters

    public int getImageSize() {
        return iImageSize;
    }
    public int getSpaceSize() {
        return iSpaceSize;
    }
    public MenuItem getMnuFlag() {
        return mnuFlag;
    }
    public MenuItem getMnuStep() {
        return mnuStep;
    }

    public void setImageSize(int iImageSize) {
        this.iImageSize = iImageSize;
    }
    public void setSpaceSize(int iSpaceSize) {
        this.iSpaceSize = iSpaceSize;
    }
    public void setMnuFlag(MenuItem mnuFlag) {
        this.mnuFlag = mnuFlag;
    }
    public void setMnuStep(MenuItem mnuStep) {
        this.mnuStep = mnuStep;
    }
    //endregion

    //region 4. Functions and Methods
    public void populateButtons(){
        //Setting the tables Margin
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, this.iSpaceSize,0 , this.iSpaceSize);
        tblAllField.setLayoutParams(params);

        //Adding the size for the
        this.allFlagPosition = new boolean[NUMBER_ROWS+5][NUMBER_COLUMNS+5];
        this.allStepPosition = new boolean[NUMBER_ROWS+5][NUMBER_COLUMNS+5];

        //Adding the size for the Button matrix
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

                //Saving the position
                final int finalRow=row;
                final int finalColumn=column;

                //Init the boolean matrix
                allFlagPosition[row][column]=false;
                allStepPosition[row][column]=false;

                //Init the Button
                allButton[row][column]=new Button(activity);

                //Setting the Button
                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));

                //Set Background
                allButton[row][column].setBackgroundResource(R.drawable.unknown);

                //Set size

                allButton[row][column].setLayoutParams(new TableRow.LayoutParams(this.iImageSize, this.iImageSize));


                //Add Listener
                allButton[row][column].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        buttonClicked(finalRow, finalColumn);
                    }
                });


                //Add the Button to the TableRow
                tableRow.addView(allButton[row][column]);
            }
        }
    }
    private void buttonClicked(int row, int column){
        //Toast
        Toast.makeText(activity,
                "Row: " + String.valueOf(row)+ "\nColumn: " + String.valueOf(column),
                Toast.LENGTH_SHORT).show();

        if(firstClick){
            firstClick=false;
            uploadingNumberTable(row, column);
        }
        else {

            //Setting the Flag
            if (strClick.equals(DEF_FLAG) && allStepPosition[row][column] == false) {
                if (!allFlagPosition[row][column]) {
                    //Placing Flag
                    allButton[row][column].setBackgroundResource(R.drawable.flag);

                    allFlagPosition[row][column] = true;
                } else {
                    //Removing Flag
                    allButton[row][column].setBackgroundResource(R.drawable.unknown);

                    allFlagPosition[row][column] = false;

                }
            }
            //Setting the numbers or Ending the game
            else if (allStepPosition[row][column] == false && allFlagPosition[row][column] == false) {
                allStepPosition[row][column] = true;

                if (numberTable.getFromPosition(row, column) == NumberTable.DEF_BOMB)
                    //Ending the game
                    endingTheGame(row, column);
                else
                    //Placing the number
                    placingTheNumber(row, column);
            }
        }
    }
    private void uploadingNumberTable(int row, int column){
        //Init the numberTable
        this.numberTable= new NumberTable(NUMBER_ROWS, NUMBER_COLUMNS, this);

        //The first click position will be empty field
        do{
            this.numberTable.uploadMatrix();
        }while (numberTable.getFromPosition(row, column)!=0);

        //Fill the zero area
        fillingIsOn=true;
        this.numberTable.fillTheZeroArea(row, column);
        fillingIsOn=false;

    }

    //TODO Method for ending the game
    private void endingTheGame(int row, int column){
        allButton[row][column].setBackgroundResource(R.drawable.activated_bomb);


    }

    public void placingTheNumber(int row, int column){
        int iNumber=numberTable.getFromPosition(row, column);
        this.allStepPosition[row][column]=true;
        switch (iNumber){
            case 0:
                allButton[row][column].setBackgroundResource(R.drawable.zero);
                if(!fillingIsOn) {
                    fillingIsOn=true;
                    numberTable.fillTheZeroArea(row, column);
                    fillingIsOn=false;
                }
                break;
            case 1:
                allButton[row][column].setBackgroundResource(R.drawable.one);
                break;
            case 2:
                allButton[row][column].setBackgroundResource(R.drawable.two);
                break;
            case 3:
                allButton[row][column].setBackgroundResource(R.drawable.three);
                break;
            case 4:
                allButton[row][column].setBackgroundResource(R.drawable.four);
                break;
            case 5:
                allButton[row][column].setBackgroundResource(R.drawable.five);
                break;
            case 6:
                allButton[row][column].setBackgroundResource(R.drawable.six);
                break;
            case 7:
                allButton[row][column].setBackgroundResource(R.drawable.seven);
                break;
            case 8:
                allButton[row][column].setBackgroundResource(R.drawable.eight);
                break;
        }
    }

    //endregion

    //region 5. Setting the Menu listener
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuFlag:
                //Setting the strClick to the flagging mode
                strClick=DEF_FLAG;

                //Setting the visible
                this.mnuFlag.setVisible(false);
                this.mnuStep.setVisible(true);

                break;

            case R.id.mnuStep:
                //Setting the strClick to the stepping mode
                strClick=DEF_STEP;

                //Setting the visible
                this.mnuStep.setVisible(false);
                this.mnuFlag.setVisible(true);

                break;
        }
        Toast.makeText(activity, "Changing to " + strClick, Toast.LENGTH_SHORT).show();
        return true;
    }
    //endregion
}
