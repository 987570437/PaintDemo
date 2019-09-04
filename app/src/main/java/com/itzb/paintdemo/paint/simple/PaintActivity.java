package com.itzb.paintdemo.paint.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GradientLayout(this));
    }
}
