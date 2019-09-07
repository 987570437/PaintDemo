package com.itzb.paintdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itzb.paintdemo.canvas.splash.SplashActivity;
import com.itzb.paintdemo.canvas.split.SplitActivity;
import com.itzb.paintdemo.canvas.transform.TransformActivity;
import com.itzb.paintdemo.paint.colorfilter.ColorFilterActivity;
import com.itzb.paintdemo.paint.simple.PaintActivity;
import com.itzb.paintdemo.paint.xfermode.XfermodesActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPaint;
    private Button btnXferomde;
    private Button btnColorFilter;
    private Button btnCanvasTransform;
    private Button btnCanvasSplit;
    private Button btnCanvasSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPaint = findViewById(R.id.btn_paint);
        btnXferomde = findViewById(R.id.btn_xfer_mode);
        btnColorFilter = findViewById(R.id.btn_color_filter);
        btnCanvasTransform = findViewById(R.id.btn_canvas_transform);
        btnCanvasSplit = findViewById(R.id.btn_canvas_split);
        btnCanvasSplash = findViewById(R.id.btn_canvas_splash);
        btnPaint.setOnClickListener(this);
        btnXferomde.setOnClickListener(this);
        btnColorFilter.setOnClickListener(this);
        btnCanvasTransform.setOnClickListener(this);
        btnCanvasSplit.setOnClickListener(this);
        btnCanvasSplash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_paint:
                startActivity(new Intent(MainActivity.this, PaintActivity.class));
                break;
            case R.id.btn_xfer_mode:
                startActivity(new Intent(MainActivity.this, XfermodesActivity.class));
                break;
            case R.id.btn_color_filter:
                startActivity(new Intent(MainActivity.this, ColorFilterActivity.class));
                break;
            case R.id.btn_canvas_transform:
                startActivity(new Intent(MainActivity.this, TransformActivity.class));
                break;
            case R.id.btn_canvas_split:
                startActivity(new Intent(MainActivity.this, SplitActivity.class));
                break;
            case R.id.btn_canvas_splash:
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                break;
        }
    }
}
