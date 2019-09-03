package com.itzb.paintdemo.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.itzb.paintdemo.R;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GradientLayout(this));
    }
}
