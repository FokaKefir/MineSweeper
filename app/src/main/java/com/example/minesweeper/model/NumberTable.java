package com.example.minesweeper.model;

import com.example.minesweeper.listener.MainActivityListener;

public class NumberTable {
    //region 0. Constants
    public static final int DEF_NUMBER_OF_BOMBS=15;
    public static final int DEF_BOMB=-1;
    private static final int DEF_BOARD=1;
    //endregion

    //region 1. Decl and Init
    private int iRow;
    private int iColumn;

    private int front;
    private int back;

    private int[][] iMatrix;
    private boolean[][] blnMatrix;

    private MainActivityListener listener;
    //endregion

    //region 2. Constructor
    public NumberTable(int iRow, int iColumn, MainActivityListener listener){
        this.iRow=iRow;
        this.iColumn=iColumn;
        this.listener=listener;

        this.iMatrix=new int[iRow+5][iColumn+5];
    }
    //endregion

    //region 3. Getters and Setters
    public int getFromPosition(int row, int column){
        return iMatrix[row][column];
    }

    public int getRow() {
        return iRow;
    }
    public int getColumn() {
        return iColumn;
    }

    public void setRow(int iRow) {
        this.iRow = iRow;
    }
    public void setColumn(int iColumn) {
        this.iColumn = iColumn;
    }
    //endregion

    //region 4. Upload the matrix with random positioned bombs and setting the numbers

    private void setZeroMatrix(){
        for(int row=1;row<=iRow;row++){
            for(int column=1;column<=iColumn;column++){
                iMatrix[row][column]=0;
                blnMatrix[row][column]=true;
            }
        }
    }
    private void setBombs() {
        for (int ind = 0; ind < DEF_NUMBER_OF_BOMBS; ind++) {
            int iRandomRow;
            int iRandomColumn;
            do {
                iRandomRow = (int) ((Math.random() * iRow) + 1);
                iRandomColumn = (int) ((Math.random() * iColumn) + 1);
            } while (iMatrix[iRandomRow][iRandomColumn] == DEF_BOMB);

            iMatrix[iRandomRow][iRandomColumn] = DEF_BOMB;
        }
    }
    private void setNumbers(){
        for(int row=1;row<=iRow;row++){
            for(int column=1;column<=iColumn;column++){
                if(iMatrix[row][column]==DEF_BOMB)
                    continue;
                else{
                    int iNumberOfBombs=0;
                    for(int i=-1;i<=1;i++){
                        for(int j=-1;j<=1;j++){
                            if(iMatrix[row+i][column+j]==DEF_BOMB)
                                iNumberOfBombs++;
                        }
                    }
                    iMatrix[row][column]=iNumberOfBombs;
                }
            }
        }
    }
    private void setBoards(){
        for(int row=0;row<=iRow+1;row++){
            iMatrix[row][0]=DEF_BOARD;
            iMatrix[row][iColumn+1]=DEF_BOARD;
        }
        for(int column=0;column<=iColumn+1;column++){
            iMatrix[0][column]=DEF_BOARD;
            iMatrix[iRow+1][column]=DEF_BOARD;
        }
    }

    public void uploadMatrix(){
        //Init the boolean matrix
        this.blnMatrix= new boolean[iRow+5][iColumn+5];

        //Reset the number matrix to zero and the boolean matrix
        setZeroMatrix();

        //Uploading the matrix with bombs
        setBombs();

        //Counting the bombs around the field
        setNumbers();

        //Setting the Board for the Matrix
        setBoards();

    }

    //endregion

    //region 5. Filling the zero area
    public void fillTheZeroArea(int toRow, int toColumn){
        //Setting 0 the Variables
        front=0; back=0;

        //Decl and Init the Vectors
        int[] vecRow= new int[iRow*iColumn];
        int[] vecColumn= new int[iRow*iColumn];

        //Setting the first position
        vecRow[back]= toRow;
        vecColumn[back]= toColumn;
        back++;

        //Loop
        while(front<back){
            watchingNeighborField(vecRow, vecColumn);
            front++;
        }


    }

    private void watchingNeighborField(int[] vecRow, int[] vecColumn){
        //Setting the positions vectors
        int posRow[]={-1, -1, -1, 0, 0, 1, 1, 1};
        int posColumn[]={-1, 0, 1, -1, 1, -1, 0, 1};
        int positions=8;

        //Getting the actually position
        int row=vecRow[front];
        int column=vecColumn[front];

        //Setting the Buttons background
        if(row!=0 && row!=iRow+1
        && column!=0 && column!=iColumn+1){
            blnMatrix[row][column]=false;
            listener.placingTheNumber(row, column);
        }

        //Exploring the neighbor field
        if(iMatrix[row][column]==0){
            for(int ind=0;ind<positions;ind++){
                int newRow= row + posRow[ind];
                int newColumn= column + posColumn[ind];

                if(iMatrix[newRow][newColumn]!=DEF_BOMB && blnMatrix[newRow][newColumn]){
                    vecRow[back]=newRow;
                    vecColumn[back]=newColumn;
                    blnMatrix[newRow][newColumn]=false;
                    back++;
                }
            }
        }

    }
    //endregion

}
