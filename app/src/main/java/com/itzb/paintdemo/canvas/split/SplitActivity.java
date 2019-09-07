package com.itzb.paintdemo.canvas.split;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SplitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SplitView(this));
    }
}
