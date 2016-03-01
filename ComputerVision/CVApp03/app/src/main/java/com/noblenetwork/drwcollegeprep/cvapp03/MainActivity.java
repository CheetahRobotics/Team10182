package com.noblenetwork.drwcollegeprep.cvapp03;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private boolean              mTakeScreenShot;
    private boolean              mFreezeFrameOn = false;
    private PictureMode mMode = PictureMode.Color;
    private CvCameraViewFrame    freezeFrame;
    private Mat mResultMat;
    private Mat mPostProcessMat;
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

        if (item.getItemId() == R.id.action_screen_shot)
            this.mTakeScreenShot = true;

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
        mPostProcessMat = new Mat();
    }

    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mResultMat != null)
            mResultMat.release();

        mResultMat = null;

        if (mIntermediateMat != null)
            mIntermediateMat.release();
        mIntermediateMat = null;

        if (mPostProcessMat != null)
            mPostProcessMat.release();
        mPostProcessMat = null;

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

        switch (this.mMode) {
            case Grey:
                return postProcess(mGray);
            case Color:
                return postProcess(mRgba);
            default:
                return mRgba;
        }
    }
    private Mat postProcess(Mat inputMat) {

        Mat outputMat = inputMat;

        // blur function.
//        Imgproc.medianBlur(inputMat, outputMat, 11);
//        Imgproc.GaussianBlur(inputMat, outputMat, new Size(7,7), 0, 0);

        // these only work on grayscale
//        Imgproc.Canny(inputMat, outputMat, 50, 50);
//        Imgproc.equalizeHist(inputMat, outputMat);

        return takeScreenShot(outputMat);
    }
    private Mat takeScreenShot(Mat image) {
        if (this.mTakeScreenShot) {
            this.mTakeScreenShot = false;
            Utilities.saveImg(image, this);
            showToast("Screen Shot saved to device.");
        }
        return image;
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
