package com.example.minesweeper.model;

public class NumberTable {
    //region zero. Constants
    private static final int DEF_NUMBER_OF_BOMBS=20;
    public static final int DEF_BOMB=-1;
    //endregion

    //region one. Decl and Init
    private int iRow;
    private int iColumn;

    private int[][] iMatrix;
    //endregion

    //region two. Constructor
    public NumberTable(int iRow, int iColumn){
        this.iRow=iRow;
        this.iColumn=iColumn;

        this.iMatrix=new int[iRow+5][iColumn+5];
    }
    //endregion

    //region three. Getters and Setters
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

    //region four. Upload the matrix with random positioned bombs and setting the numbers
    public void uploadMatrix(){
        //uploading the matrix with bombs
        for(int ind=0;ind<DEF_NUMBER_OF_BOMBS;ind++){
            int iRandomRow;
            int iRandomColumn;
            do {
                iRandomRow = (int) ((Math.random() * iRow)+1);
                iRandomColumn = (int) ((Math.random() * iColumn)+1);
            }while(iMatrix[iRandomRow][iRandomColumn]==DEF_BOMB);

            iMatrix[iRandomRow][iRandomColumn]=DEF_BOMB;
        }

        //Counting the bombs around the field
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

    //endregion

}
