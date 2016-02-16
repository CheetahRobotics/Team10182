package com.noblenetwork.drwcollegeprep.cvapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.gesture.OrientedBoundingBox;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity implements CvCameraViewListener2 {
    private static final String  TAG              = "MainActivity";

    private boolean              mFreezeFrameOn = false;
    private boolean              mGrayMode = false;
    private CvCameraViewFrame    freezeFrame;
    private Mat                  mRgba;
    private Mat                  mGray;
    String _toastMsg = "";

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.enableFpsMeter();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.my_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected. selected item: " + item);
        if (item.getTitle().equals("Freeze Frame")) {
            this.mFreezeFrameOn = true;
            mRgba = mRgba.clone();
            mGray = mGray.clone();
            item.setTitle("Live Feed");
        }
        else if (item.getTitle().equals("Live Feed")) {
            this.mFreezeFrameOn = false;
            item.setTitle("Freeze Frame");
        }

        if (item.getTitle().equals("Grayscale")) {
            this.mGrayMode = true;
            item.setTitle("Make Color");
        }
        else if (item.getTitle().equals("Make Color")) {
            this.mGrayMode = false;
            item.setTitle("Grayscale");
        }
        return true;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
        if (mRgba != null)
            mRgba.release();
        if (mGray != null)
            mGray.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        if (!this.mFreezeFrameOn) {
            mRgba = inputFrame.rgba();
            mGray = inputFrame.gray();
        }

        if (this.mGrayMode) {
            Mat result = new Mat();
            Imgproc.threshold(mGray, result, 128, 255, Imgproc.THRESH_BINARY);
            return result;
        }
        else
            return mRgba;
    }

    private void showToast(String msg) {
        _toastMsg = msg;
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MainActivity.this, _toastMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
