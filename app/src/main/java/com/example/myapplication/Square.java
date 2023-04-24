package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private final int mProgram;
    private int mTexCoordHandle;
    private int mTextureHandle;
    private  FloatBuffer vertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private int positionHandle;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, fTexCoord);" +
                    "}";
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f, 0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f, 0.5f, 0.0f}; // top right
    static float[] texCoords = {
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.5f, 0.5f,
            0.0f, 0.5f
    };
    private int texturID;
    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

    public Square() {
        texturID = MyGLRenderer.loadTexture(1);

        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);


        GLES20.glLinkProgram(mProgram);
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mTexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTexCoordBuffer.put(texCoords).position(0);
    }


    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int TEX_COORD_COUNT = 2;
    private static final int TEX_COORD_STRIDE = TEX_COORD_COUNT * FLOAT_SIZE_BYTES;

    public void draw() {

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoord");
        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");

        GLES20.glUseProgram(mProgram);

        GLES20.glVertexAttribPointer(positionHandle,COORDS_PER_VERTEX,GLES20.GL_FLOAT
        ,false,vertexStride,vertexBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 4, GLES20.GL_FLOAT,  false, TEX_COORD_STRIDE,mTexCoordBuffer );

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
    public void updateVertices(float mX,float mY) {

        float mWidth= Math.abs( squareCoords[1]-squareCoords[4]);
        float mHeight = Math.abs(squareCoords[0]-squareCoords[6]);

        float left = mX - mWidth / 2.0f;
        float right = mX + mWidth / 2.0f;
        float top = mY + mHeight / 2.0f;
        float bottom = mY - mHeight / 2.0f;

        squareCoords[0] =left;
        squareCoords[1] =top;
        squareCoords[3] =right;
        squareCoords[4] =top;
        squareCoords[6] =left;
        squareCoords[7] =bottom;
        squareCoords[9] =right;
        squareCoords[10] =bottom;

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
    }

}


