package com.itzb.paintdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itzb.paintdemo.colorfilter.ColorFilterActivity;
import com.itzb.paintdemo.paint.PaintActivity;
import com.itzb.paintdemo.xfermode.XfermodesActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPaint;
    private Button btnXferomde;
    private Button btnColorFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPaint = findViewById(R.id.btn_paint);
        btnXferomde = findViewById(R.id.btn_xfer_mode);
        btnColorFilter = findViewById(R.id.btn_color_filter);
        btnPaint.setOnClickListener(this);
        btnXferomde.setOnClickListener(this);
        btnColorFilter.setOnClickListener(this);
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
        }
    }
}
