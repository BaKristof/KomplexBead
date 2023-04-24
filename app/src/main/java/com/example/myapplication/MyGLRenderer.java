package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MyGLRenderer implements GLSurfaceView.Renderer {

    //private  Triangle mtirg;
    private  Square msquer;
    //public volatile float mAngle;
    private static Context mContext;

    public MyGLRenderer(Context context) {

        mContext = context;

    }

    private float[] rotationMatrix = new float[16];
    @Override
    public void onDrawFrame(GL10 gl) {
      /*  float[] scratch = new float[16];
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);

        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

            private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
*/
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // mtirg.draw(scratch);
        msquer.draw();
    }
    public void Change_Playesr ( float x, float y ){
        msquer.updateVertices(x,y);

    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
      // Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        //mtirg = new Triangle();
        msquer = new Square();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }


    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public static int loadTexture(int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   // No pre-scaling

        // Read in the resource
        final Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(), resourceId, options);

        // Bind to the texture in OpenGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        // Load the bitmap into the bound texture
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // Clean up
        bitmap.recycle();

        return textureId[0];
    }
}
