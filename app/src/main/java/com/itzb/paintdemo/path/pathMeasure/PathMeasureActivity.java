package com.itzb.paintdemo.path.pathMeasure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PathMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathMeasureView(this));
    }
}
