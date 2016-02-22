package com.noblenetwork.drwcollegeprep.cvapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity implements CvCameraViewListener2 {
    public enum PictureMode { Grey, Color, Mask, ApplyMask, ApplyMaskRealTime };
    private static final String  TAG              = "MainActivity";

    TextView                     mThresholdTextView;
    SeekBar                      mSeekBar;
    private boolean              mTakeScreenShot;
    private int                  mThreshold = Imgproc.THRESH_BINARY;
    private boolean              mFreezeFrameOn = false;
    private PictureMode mMode = PictureMode.Color;
    private CvCameraViewFrame    freezeFrame;
    private Mat mResultMat;
    private Mat mIntermediateMat;
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
        mSeekBar = (SeekBar) findViewById(R.id.thresholdSeekBar);
        mThresholdTextView = (TextView) findViewById(R.id.thresholdTextView);
        Mat result;
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

        if (item.getItemId() == R.id.GreyscaleMode) {
            this.mMode = PictureMode.Grey;
        }
        if (item.getItemId() == R.id.ColorMode) {
            this.mMode = PictureMode.Color;
        }
        if (item.getItemId() == R.id.MaskMode) {
            this.mMode = PictureMode.Mask;
        }
        if (item.getItemId() == R.id.ApplyMaskMode) {
            this.mMode = PictureMode.ApplyMask;
        }
        if (item.getItemId() == R.id.ApplyRealTimeMaskMode) {
            this.mMode = PictureMode.ApplyMaskRealTime;
        }

        if (item.getTitle().equals("NO_THRESHOLD"))
            this.mThreshold = -1;
        if (item.getTitle().equals("THRESH_BINARY"))
            this.mThreshold = Imgproc.THRESH_BINARY;
        if (item.getTitle().equals("THRESH_BINARY_INV"))
            this.mThreshold = Imgproc.THRESH_BINARY_INV;

        if (item.getTitle().equals("THRESH_TRUNC"))
            this.mThreshold = Imgproc.THRESH_TRUNC;

        if (item.getTitle().equals("THRESH_TOZERO"))
            this.mThreshold = Imgproc.THRESH_TOZERO;

        if (item.getTitle().equals("THRESH_TOZERO_INV"))
            this.mThreshold = Imgproc.THRESH_TOZERO_INV;

        if (item.getItemId() == R.id.action_screen_shot)
            this.mTakeScreenShot = true;
        item.setChecked(true);

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
        mResultMat = new Mat();
        mIntermediateMat = new Mat();
    }

    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mResultMat != null)
            mResultMat.release();

        mResultMat = null;

        if (mIntermediateMat != null)
            mIntermediateMat.release();

        mIntermediateMat = null;
 
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

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mThresholdTextView.setText(String.valueOf(mSeekBar.getProgress()));
            }
        });

        switch (this.mMode) {
            case Grey:
                if (mThreshold < 0) {
                    takeScreenShot(mGray);
                    return mGray;
                }
                else {
                    Imgproc.threshold(mGray, mResultMat, mSeekBar.getProgress(), 255, mThreshold);
                    takeScreenShot(mResultMat);
                    return mResultMat;
                }
            case Color:
                takeScreenShot(mRgba);
                return mRgba;
            case Mask:
                takeScreenShot(mRgba);
                return mResultMat;
            case ApplyMaskRealTime:
                Imgproc.threshold(mGray, mResultMat, mSeekBar.getProgress(), 255, mThreshold);
                // fall thru to next step....
            case ApplyMask:
                Scalar zero = new Scalar(0);
                mIntermediateMat.setTo(zero);
                mRgba.copyTo(mIntermediateMat, mResultMat);
                takeScreenShot(mIntermediateMat);
                return mIntermediateMat;
            default:
                return mRgba;
        }
    }
    private void takeScreenShot(Mat image) {
        if (this.mTakeScreenShot) {
            this.mTakeScreenShot = false;
            Utilities.saveImg(image, this);
            showToast("Screen Shot saved to device.");
        }
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
