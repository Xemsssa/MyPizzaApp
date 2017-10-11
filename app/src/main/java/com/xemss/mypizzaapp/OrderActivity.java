package com.xemss.mypizzaapp;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class OrderActivity extends Activity {
//    private final static String LOG = "log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(LOG, "in order activity");
        super.onCreate(savedInstanceState);
//        Log.d(LOG, "in savedInstance to set view");
        setContentView(R.layout.activity_order);
//        Log.d(LOG, "after order activity");

        android.app.ActionBar actionBar = getActionBar();
//        Log.d(LOG, "before action up");
        // TODO: 11.10.2017  problem - may create NPE
        actionBar.setDisplayHomeAsUpEnabled(true);
//        Log.d(LOG, "after action bar up");
    }
}
