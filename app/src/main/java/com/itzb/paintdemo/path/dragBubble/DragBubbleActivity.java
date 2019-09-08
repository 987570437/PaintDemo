package com.itzb.paintdemo.path.dragBubble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DragBubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DragBubbleView(this));
    }
}
