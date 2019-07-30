package com.example.minesweeper.listener;

import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minesweeper.R;
import com.example.minesweeper.gui.GameActivity;
import com.example.minesweeper.model.NumberTable;

public class GameActivityListener implements MenuItem.OnMenuItemClickListener {
    //region 0. Constants
    private static final String DEF_STEP="step";
    private static final String DEF_FLAG="flag";

    private static final double DEF_PERCENTAGE=0.15;

    private static final int DEF_ONE_MINUTE=60000;
    private static final int DEF_ONE_SECOND=1000;
    //endregion

    //region 1. Decl
    private MenuItem mnuStep;
    private MenuItem mnuFlag;
    private MenuItem mnuBack;

    private TableLayout tblAllField;

    private NumberTable numberTable;

    private GameActivity activity;

    private TextView txtvRestFlags;
    private TextView txtvClock;

    private CountDownTimer countDownTimer;//timer

    private long time;//actually time

    private int iRestFlags;
    private int iFlags;
    private int iSpaceSize;
    private int iImageSize;
    private int iAllFields;
    private int iRow;
    private boolean firstClick;
    private boolean fillingIsOn;

    private Button[][] allButton;
    private boolean[][] allStepPosition;//false= is it not activated, true= is activated
    private boolean[][] allFlagPosition;//false= there is no flag, true= there is flag

    private String strClick;
    //endregion

    //region 2. Constructor
    public GameActivityListener(GameActivity activity){
        this.activity=activity;
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
    public MenuItem getMnuBack() {
        return mnuBack;
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
    public void setMnuBack(MenuItem mnuBack) {
        this.mnuBack = mnuBack;
    }

    public void setTblAllField(TableLayout tblAllField) {
        this.tblAllField = tblAllField;
    }
    public void setiRow(int iRow) {
        this.iRow = iRow;
    }
    public void setTxtvRestFlags(TextView txtvRestFlags) {
        this.txtvRestFlags = txtvRestFlags;
    }
    public void setTxtvClock(TextView txtvClock) {
        this.txtvClock = txtvClock;
    }
    //endregion

    //region 4. Generating methods
    public void declVariables(){
        this.firstClick=true;
        this.strClick=DEF_STEP;
        this.iAllFields=iRow*iRow;
        this.iRestFlags=(int)(iAllFields*DEF_PERCENTAGE);
        this.iFlags=iRestFlags;

        //Adding the size for all Matrix
        this.allFlagPosition = new boolean[iRow+5][iRow+5];
        this.allStepPosition = new boolean[iRow+5][iRow+5];
    }
    public void populateButtons(){
        //Setting the tables Margin
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, this.iSpaceSize,0 , this.iSpaceSize);
        tblAllField.setLayoutParams(params);

        //Adding the size for the Button matrix
        this.allButton=new Button[iRow+5][iRow+5];

        for(int row=1;row<=iRow;row++){

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

            for(int column=1;column<=iRow;column++){

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

        //Setting the txtvRestFlags
        setTextFlags();

    }
    private void uploadingNumberTable(int row, int column){
        //Init the numberTable
        this.numberTable= new NumberTable(iRow, iRow, this);

        //Setting the number of bombs

        numberTable.setNumberBombs(iRestFlags);

        //The first click position will be empty field
        do{
            this.numberTable.uploadMatrix();
        }while (numberTable.getFromPosition(row, column)!=0);

        //Fill the zero area
        fillingIsOn=true;
        this.numberTable.fillTheZeroArea(row, column);
        fillingIsOn=false;

    }
    //endregion

    //region 5. Button is clicked
    private void buttonClicked(int row, int column){
        /*Toast
        Toast.makeText(activity,
                "Row: " + String.valueOf(row)+ "\nColumn: " + String.valueOf(column),
                Toast.LENGTH_SHORT).show();
        */

        //First click
        if(firstClick){
            firstClick=false;

            //Uploading the numberTable
            uploadingNumberTable(row, column);

            //Starting the timer
            startTimer();
        }
        //Other click
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

        //Setting the txtvRestFlags
        setTextFlags();

    }
    public void placingTheNumber(int row, int column){
        int iNumber=numberTable.getFromPosition(row, column);
        this.allStepPosition[row][column]=true;
        this.allFlagPosition[row][column]=false;
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
    private int numberingFlags(){
        int flags=0;
        for(int row=1;row<=iRow;row++){
            for (int column=1;column<=iRow;column++){
                if(allFlagPosition[row][column])
                    flags++;
            }
        }
        return flags;
    }
    private boolean watchingToWinning(){
        int steps=0;
        for(int row=1;row<=iRow;row++){
            for (int column=1;column<=iRow;column++){
                if(numberTable.getFromPosition(row, column)==NumberTable.DEF_BOMB
                   && allFlagPosition[row][column]==false) return false;
                if(allStepPosition[row][column])
                    steps++;
            }
        }
        if (steps==iAllFields-iFlags)
            return true;
        else
            return false;
    }
    private void setTextFlags(){
        //Setting the txtvRestFlags
        this.iRestFlags=iFlags-numberingFlags();
        if(iRestFlags!=0)
            this.txtvRestFlags.setText("Flags: " + String.valueOf(iRestFlags));
        else if(watchingToWinning())
            winningTheGame();
    }
    //endregion

    //region 6. Methods and Functions for ending the game
    private void endingTheGame(int explodedRow, int explodedColumn){
        //Stop the Timer
        stopTimer();

        //Setting the background for the buttons
        setBackgroundButtons(explodedRow, explodedColumn);

        //Blocking the buttons
        blockButtons();

        //Set visible the mnuBack MenuItem
        mnuBack.setVisible(true);

    }
    private void winningTheGame(){
        //Stop the Timer
        stopTimer();

        //Setting the background for the buttons
        setBackgroundButtons(0, 0);

        //Blocking the buttons
        blockButtons();

        //Set visible the mnuBack MenuItem
        mnuBack.setVisible(true);

        //Setting the txtvRestFlags to the winner text
        this.txtvRestFlags.setText("You are save");
    }

    private void setBackgroundButtons(int explodedRow, int explodedColumn){
        for(int row=1;row<=iRow;row++){
            for (int column=1;column<=iRow;column++){
                if(numberTable.getFromPosition(row, column)==NumberTable.DEF_BOMB) {
                    if (!allStepPosition[row][column] && !allFlagPosition[row][column])
                        allButton[row][column].setBackgroundResource(R.drawable.bomb);

                }
                else if(allFlagPosition[row][column]){
                    allButton[row][column].setBackgroundResource(R.drawable.bomb_incorrect);
                }
            }
        }
        if(explodedRow!=0 && explodedColumn!=0)
            allButton[explodedRow][explodedColumn].setBackgroundResource(R.drawable.bomb_activated);


    }
    private void blockButtons(){
        //Setting buttons on the table
        for(int row=1;row<=iRow;row++){
            for (int column=1;column<=iRow;column++){
                allButton[row][column].setClickable(false);
            }
        }

        //Setting the menu buttons
        this.mnuStep.setVisible(false);
        this.mnuFlag.setVisible(false);

    }
    //endregion

    //region 7. Setting the Menu listener
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

            case R.id.mnuBack:
                //Close activity
                activity.closeActivity();
                break;
        }
        Toast.makeText(activity, "Changing to " + strClick, Toast.LENGTH_SHORT).show();
        return true;
    }
    //endregion

    //region 8. Setting Timer

    public void setTimer(){
        //Setting 0 the time
        time=DEF_ONE_MINUTE;

        //Setting the text to 0:00
        updateTimer();
    }

    private void startTimer(){
        countDownTimer= new CountDownTimer(time,DEF_ONE_SECOND) {
            @Override
            public void onTick(long l) {
                time += DEF_ONE_SECOND;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void stopTimer(){
        //Canceling the counting
        countDownTimer.cancel();

        //Setting the time where end the game
        String strOutText=outClock();

        Toast.makeText(activity, strOutText, Toast.LENGTH_SHORT).show();

    }

    private void updateTimer(){
        //Getting the time in String
        String strTime=outClock();

        //Setting the TextView
        txtvClock.setText(strTime);
    }

    private String outClock(){
        //Decl and Init
        int minutes= (int) (time / DEF_ONE_MINUTE)-1;
        int seconds= (int) time % DEF_ONE_MINUTE / DEF_ONE_SECOND;

        //Setting the String then a clock
        String strTime;
        strTime = "" + minutes;
        strTime += ":";
        if(seconds < 10){
            strTime+="0";
        }
        strTime += seconds;

        return strTime;
    }

    //endregion

}
