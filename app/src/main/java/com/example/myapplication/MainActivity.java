package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private GLSurfaceView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mGLView = new GLSurfaceView(this);
        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(new MyGLRenderer(this));
        setContentView(mGLView);
    }
}