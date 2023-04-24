package com.example.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final  Context mContext;
    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;
                renderer.Change_Playesr(dx,dy);
                requestRender();
        }
        previousX = x;
        previousY = y;
        return true;
    }
    public MyGLSurfaceView(Context context){
        super(context);

        mContext = context;
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer(context);

        setRenderer(renderer);

    }
}
