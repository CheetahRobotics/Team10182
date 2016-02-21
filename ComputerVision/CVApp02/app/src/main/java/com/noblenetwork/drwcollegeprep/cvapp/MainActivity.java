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
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity implements CvCameraViewListener2 {
    private static final String  TAG              = "MainActivity";

    TextView                     mThresholdTextView;
    SeekBar                      mSeekBar;
    private int                  mThreshold = Imgproc.THRESH_BINARY;
    private boolean              mFreezeFrameOn = false;
    private boolean              mGrayMode = false;
    private CvCameraViewFrame    freezeFrame;
    private Mat mResultMat;
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
        //        mSeekBar.setOnSeekBarChangeListener(this);
        Mat result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
//        if (seekBar.getId() == R.id.maxMinSeekBar)
//            _maxMin = progress;
//        else
//            _ransacThreshold = progress;
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
    }

    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mResultMat != null)
            mResultMat.release();

        mResultMat = null;
 
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

        if (this.mGrayMode) {
            //return mGray;
            Imgproc.threshold(mGray, mResultMat, mSeekBar.getProgress(), 255, mThreshold);
            return mResultMat;
            //a bit = 8 bit if all 8 bits are set to one 255 is the
            //if the pixle is >
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
