package com.noblenetwork.drwcollegeprep.cvapp03;

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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements CvCameraViewListener2 {
    public enum PictureMode { Grey, Color, Mask, ApplyMask, Contours, ContourMaskMode, CenterPoint };
    private static final String  TAG              = "MainActivity";

    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

    private boolean              mTakeScreenShot;
    private boolean              mFreezeFrameOn = false;
    private PictureMode mMode = PictureMode.Color;
    private CvCameraViewFrame    freezeFrame;
    private Mat mResultMat;
    private Mat mPostProcessMat;
    private Mat mIntermediateMat;
    private Mat mHierarchy ;
    
    SeekBar mLowerHueSeekBar;
    SeekBar mUpperHueSeekBar;
    TextView mLowerHueText;
    TextView mUpperHueText;


    private Mat mHSVMat;
    private Mat mDilatedMat;
    private Mat mPyrDownMat;    
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
        mLowerHueSeekBar = (SeekBar) findViewById(R.id.LowerHueSeekBar);
        mUpperHueSeekBar = (SeekBar) findViewById(R.id.UpperHueSeekBar);
        mLowerHueText = (TextView) findViewById(R.id.LowerHueValue);
        mUpperHueText = (TextView) findViewById(R.id.UpperHueValue);
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
        if (item.getItemId() == R.id.ContoursMode) {
            this.mMode = PictureMode.Contours;
        }
        if (item.getItemId() == R.id.ContoursMaskMode) {
            this.mMode = PictureMode.ContourMaskMode;
        }
        if (item.getItemId() == R.id.CenterPoint) {
            this.mMode = PictureMode.CenterPoint;
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
        mHSVMat=new Mat();
        mHierarchy = new Mat();
        mDilatedMat = new Mat();
        mPyrDownMat = new Mat();
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

        if (mHSVMat != null)
            mHSVMat.release();
        mHSVMat = null;

        if (mDilatedMat != null)
            mDilatedMat.release();
        mDilatedMat = null;

        if (mPostProcessMat != null)
            mPostProcessMat.release();
        mPostProcessMat = null;
        if (mPyrDownMat != null)
            mPyrDownMat.release();
        mPyrDownMat = null;

        if (mRgba != null)
            mRgba.release();
        if (mGray != null)
            mGray.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mLowerHueText.setText(String.valueOf(mLowerHueSeekBar.getProgress()));
                mUpperHueText.setText(String.valueOf(mUpperHueSeekBar.getProgress()));
            }
        });

        if (!this.mFreezeFrameOn) {
            mRgba = inputFrame.rgba();
            mGray = inputFrame.gray();
        }

        Scalar zero = new Scalar(0);
        Scalar lower = new Scalar(mLowerHueSeekBar.getProgress(),0, 0);
        Scalar upper = new Scalar(mUpperHueSeekBar.getProgress(),255,255);
        Scalar CONTOUR_COLOR = new Scalar(0,255,0,255);
        switch (this.mMode) {
            case Grey:
                return postProcess(mGray);
            case Color:
                return mRgba;
            case Mask:
                Imgproc.cvtColor(mRgba, mHSVMat, Imgproc.COLOR_RGB2HSV);
                Core.inRange(mHSVMat, lower, upper, mResultMat);
                return postProcess(mResultMat);

            // See http://docs.opencv.org/2.4/doc/tutorials/imgproc/erosion_dilatation/erosion_dilatation.html
//                Imgproc.dilate(mResultMat, mIntermediateMat, new Mat());
//                return postProcess(mIntermediateMat);
            case ApplyMask:
                Imgproc.cvtColor(mRgba, mHSVMat, Imgproc.COLOR_RGB2HSV);
                Core.inRange(mHSVMat, lower, upper, mResultMat);

                mIntermediateMat.setTo(zero);
                mRgba.copyTo(mIntermediateMat, mResultMat);

                return postProcess(mIntermediateMat);
            case Contours:
                getContours(mRgba, lower, upper);
                Imgproc.drawContours(mRgba, mContours, -1, CONTOUR_COLOR);

                return postProcess(mRgba);

            case ContourMaskMode: {
                getContours(mRgba, lower, upper);

                Imgproc.cvtColor(mRgba, mHSVMat, Imgproc.COLOR_RGB2HSV);
                Core.inRange(mHSVMat, lower, upper, mResultMat);

                mIntermediateMat.setTo(zero);
                mRgba.copyTo(mIntermediateMat, mResultMat);
                Imgproc.drawContours(mIntermediateMat, mContours, -1, CONTOUR_COLOR);

                Point centerPoint = drawRectangleAroundContours(mRgba, mContours, new Scalar(0, 0, 255));
                Imgproc.circle(mIntermediateMat, centerPoint, 10, new Scalar(255, 255, 100), 10);

                return postProcess(mIntermediateMat);
            }
            case CenterPoint:
                getContours(mRgba, lower, upper);
                Point centerPoint = drawRectangleAroundContours(mRgba, mContours,  new Scalar(0, 0, 255));
                Imgproc.circle(mRgba, centerPoint, 10, new Scalar(255, 255, 100), 10);

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
    public Mat  getContours(Mat rgbaImage, Scalar lower, Scalar upper) {
        Imgproc.pyrDown(rgbaImage, mPyrDownMat);
        Imgproc.pyrDown(mPyrDownMat, mPyrDownMat);

        Imgproc.cvtColor(mPyrDownMat, mHSVMat, Imgproc.COLOR_RGB2HSV_FULL);

        Core.inRange(mHSVMat, lower, upper, mResultMat);
        Imgproc.erode(mResultMat, mDilatedMat, new Mat());
        Imgproc.dilate(mDilatedMat, mResultMat, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mResultMat, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Find max contour area
        double maxArea = 0;
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea)
                maxArea = area;
        }

        // Filter contours by area and resize to fit the original image size
        mContours.clear();
        each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint contour = each.next();
            if (Imgproc.contourArea(contour) > 0.1*maxArea) {
                Core.multiply(contour, new Scalar(4,4), contour);
                mContours.add(contour);
            }
        }
        return mResultMat;  // return the dilated mask.
    }
    private Point drawRectangleAroundContours(Mat image, List<MatOfPoint> contours, Scalar color) {
        //For each contour found
        MatOfPoint2f approxCurve = new MatOfPoint2f();

        double maxArea = 0;
        Rect maxRect = null;
        for (int i=0; i<contours.size(); i++)
        {
            //Convert contours(i) from MatOfPoint to MatOfPoint2f
            MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(i).toArray() );
            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint( approxCurve.toArray() );

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);
            if (rect.area() > maxArea) {
                maxArea = rect.area();
                maxRect = rect;
            }

            // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color);
            Imgproc.rectangle(image, new Point(rect.x-1, rect.y-1), new Point(rect.x+1 + rect.width, rect.y+1 + rect.height), color);
            Imgproc.rectangle(image, new Point(rect.x-2, rect.y-2), new Point(rect.x+2 + rect.width, rect.y+2 + rect.height), color);
        }
        if (maxRect == null)
            return new Point(0.0,0.0);
        return new Point(maxRect.x + maxRect.width/2.0, maxRect.y + maxRect.height/2.0);
    }
}
