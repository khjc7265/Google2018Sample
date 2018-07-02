package com.example.hyeonjung.google2018sample;

import android.util.Log;

import java.util.ArrayList;

public class Test {


    private ArrayList<Integer> mList;


    public Test() {
        mList = new ArrayList<>();
        mList.add(1);
        mList.add(2);
        mList.add(3);
    }


    public void testKotlin() {

       Log.d(Constants.TAG, DefineFunctionAndCall.joinToString(mList, ", ", "(", ")"));
       Log.d(Constants.TAG, String.valueOf(DefineFunctionAndCall.lastChar("java")));

    }

    public void lastChar(String string){
       Log.d(Constants.TAG, String.valueOf(DefineFunctionAndCall.getLastChar(string)));
    }
}
