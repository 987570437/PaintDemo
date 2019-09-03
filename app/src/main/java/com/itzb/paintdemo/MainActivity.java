package com.itzb.paintdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itzb.paintdemo.paint.PaintActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPaint;
    private Button btnXferomde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPaint = findViewById(R.id.btn_paint);
        btnXferomde = findViewById(R.id.btn_xfer_mode);
        btnPaint.setOnClickListener(this);
        btnXferomde.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_paint:
                startActivity(new Intent(MainActivity.this, PaintActivity.class));
                break;
            case R.id.btn_xfer_mode:
//                startActivity(new Intent(MainActivity.this, PaintActivity.class));
                break;
        }
    }
}
