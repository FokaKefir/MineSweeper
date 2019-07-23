package com.example.minesweeper.listener;

import android.content.Context;
import android.widget.Button;

import com.example.minesweeper.gui.MainActivity;

public class MainActivityListener {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
    private Context context;

    private MainActivity activity;

    private Button[][] allButton;
    //endregion

    //region 2. Constructor

    public MainActivityListener(Context context){
        this.context=context;

    }

    //endregion


}
