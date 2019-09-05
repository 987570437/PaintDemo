package com.itzb.paintdemo.canvas.transform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TransformActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new TransformView(this));
        setContentView(new SaveRestoreView(this));
    }
}
