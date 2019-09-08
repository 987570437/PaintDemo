package com.itzb.paintdemo.path.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new PathView(this));//Path基本API
        setContentView(new BezierView(this));//多阶贝塞尔曲线

    }
}
