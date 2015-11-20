package drwtcp.ftc.com.beacondetection;

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

public class MainActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG              = "OCVSample::Activity";

    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Scalar               mBlobColorRgbaPink;
    private Scalar               mBlobColorRgbaBlue;
//    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetectorPink;
    private ColorBlobDetector    mDetectorBlue;
    private Mat                  mSpectrumPink;
    private Mat                  mSpectrumBlue;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;
    String _toastMsg = "";

    private int _colStartPink = 4;
    private int _rowStartPink = 44;
    private int _colEndPink = 68;
    private int _rowEndPink = 108;

    private int _colStartBlue = 4;
    private int _rowStartBlue = 144;
    private int _colEndBlue = 68;
    private int _rowEndBlue = 208;

    private int _spectrumRows = 64;
    private int _spectrumCols = 200;

    private boolean _calibratingPink = false;
    private boolean _calibratingBlue = false;
    private CameraBridgeViewBase mOpenCvCameraView;

    String         _configPath = "/sdcard/FIRST/calibration.txt";
    String _configFileVersion = "Version1";     // One word! no spaces. Using Scanner to read....

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.enableFpsMeter();
                    mOpenCvCameraView.setOnTouchListener(MainActivity.this);
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
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
        SPECTRUM_SIZE = new Size(_spectrumCols, _spectrumRows);
        CONTOUR_COLOR = new Scalar(0,255,0,255);
        mDetectorPink = new ColorBlobDetector();
        mDetectorBlue = new ColorBlobDetector();
        mSpectrumPink = new Mat();
        mSpectrumBlue = new Mat();
        if (!readConfig(_configPath)) {
            setPinkColor(new Scalar(152.0, 61.0, 81.0, 255.0), true);
            setBlueColor(new Scalar(14.0, 52.0, 76.0, 255.0), true);
        }
    }
    private void setPinkColor(Scalar color, boolean persist) {
        mBlobColorRgbaPink = color;
        Scalar mBlobColorHsv = convertScalarRgba2Hsv(mBlobColorRgbaPink);
        mDetectorPink.setHsvColor(mBlobColorHsv);
        Imgproc.resize(mDetectorPink.getSpectrum(), mSpectrumPink, SPECTRUM_SIZE);
        if (persist) saveConfig(_configPath);
    }
    private void setBlueColor(Scalar color, boolean persist) {
        mBlobColorRgbaBlue = color;
        Scalar mBlobColorHsv = convertScalarRgba2Hsv(mBlobColorRgbaBlue);
        mDetectorBlue.setHsvColor(mBlobColorHsv);
        Imgproc.resize(mDetectorBlue.getSpectrum(), mSpectrumBlue, SPECTRUM_SIZE);
        if (persist) saveConfig(_configPath);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;
        List<MatOfPoint> contours;

        mRgba = inputFrame.rgba();

        mDetectorPink.process(mRgba);
        contours = mDetectorPink.getContours();
        Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);
        Point redCenter = drawRectangleAroundContours(contours,  new Scalar(255, 0, 0));
        
        Mat colorLabelPink = mRgba.submat(_rowStartPink, _rowEndPink, _colStartPink, _colEndPink);
        colorLabelPink.setTo(mBlobColorRgbaPink);
        Mat spectrumLabelPink = mRgba.submat(_rowStartPink, _rowStartPink + mSpectrumPink.rows(), _colEndPink + 4, _colEndPink + 4 + mSpectrumPink.cols());
        mSpectrumPink.copyTo(spectrumLabelPink);

        mDetectorBlue.process(mRgba);
        contours = mDetectorBlue.getContours();
        Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);
        Point blueCenter = drawRectangleAroundContours(contours,  new Scalar(0, 0, 255));

        Mat colorLabelBlue = mRgba.submat(_rowStartBlue, _rowEndBlue, _colStartBlue, _colEndBlue);
        colorLabelBlue.setTo(mBlobColorRgbaBlue);
        Mat spectrumLabelBlue = mRgba.submat(_rowStartBlue, _rowStartBlue + mSpectrumBlue.rows(), _colEndBlue + 4, _colEndBlue + 4 + mSpectrumBlue.cols());
        mSpectrumBlue.copyTo(spectrumLabelBlue);

        Point center = new Point((redCenter.x + blueCenter.x)/2.0, (redCenter.y + blueCenter.y)/2.0);
        Imgproc.circle(mRgba, center, 10, new Scalar(255,255,255), 10);
        return mRgba;
    }
    private Point drawRectangleAroundContours(List<MatOfPoint> contours, Scalar color) {
        //For each contour found
        MatOfPoint2f         approxCurve = new MatOfPoint2f();

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
            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color);
            Imgproc.rectangle(mRgba, new Point(rect.x-1, rect.y-1), new Point(rect.x+1 + rect.width, rect.y+1 + rect.height), color);
            Imgproc.rectangle(mRgba, new Point(rect.x-2, rect.y-2), new Point(rect.x+2 + rect.width, rect.y+2 + rect.height), color);
        }
        if (maxRect == null)
            return new Point(0.0,0.0);
        return new Point(maxRect.x + maxRect.width/2.0, maxRect.y + maxRect.height/2.0);
    }

    private Scalar convertScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
    private Scalar convertScalarRgba2Hsv(Scalar rgbColor) {
        Mat pointMatRgba = new Mat(1, 1, CvType.CV_8UC3, rgbColor);
        Mat pointMatHsv = new Mat();
        Imgproc.cvtColor(pointMatRgba, pointMatHsv, Imgproc.COLOR_RGB2HSV_FULL, 4);

        return new Scalar(pointMatHsv.get(0, 0));
    }
    public boolean onTouch(View v, MotionEvent event) {

        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
        int yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;

        int x = (int) event.getX() - xOffset;
        int y = (int) event.getY() - yOffset;

        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ") (" + event.getX() + ", " + event.getY() + ") (" + mOpenCvCameraView.getWidth() + ", " + mOpenCvCameraView.getHeight() + ") (" + mRgba.cols() + ", " + mRgba.rows() + ")");
        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;

        if (y >= _rowStartPink && y <= _rowEndPink &&
                x >= _colStartPink && x <= _colEndPink)
        {
            showToast("Calibrating Pink - ON");
            _calibratingBlue = false;
            _calibratingPink = true;
            return false;
        }
        if (y >= _rowStartBlue && y <= _rowEndBlue &&
                x >= _colStartBlue && x <= _colEndBlue)
        {
            showToast("Calibrating Blue - ON");
            _calibratingBlue = true;
            _calibratingPink = false;
            return false;
        }

        if (!_calibratingPink && !_calibratingBlue)
            return false;

        Rect touchedRect = new Rect();

        touchedRect.x = (x > 4) ? x - 4 : 0;
        touchedRect.y = (y > 4) ? y - 4 : 0;

        touchedRect.width = (x + 4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y + 4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        Scalar mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width * touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        Scalar mBlobColorRgba = convertScalarHsv2Rgba(mBlobColorHsv);

        String prefix = _calibratingBlue ? "Set 'blue' to be: " : "Set 'pink' to be: ";
        String msg = prefix + " rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")";

        if (_calibratingPink)
            setPinkColor(mBlobColorRgba, true);
        else
            setBlueColor(mBlobColorRgba, true);
        _calibratingBlue = false;
        _calibratingPink = false;

        Log.i(TAG, msg);
        showToast(msg);
        return false; // don't need subsequent touch events
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
    private void saveConfig(String filePath) {
        try
        {
            if (mDetectorPink == null || mDetectorBlue == null) return;
            Scalar pinkHsv = mDetectorPink.getHsvColor();
            Scalar blueHsv = mDetectorBlue.getHsvColor();
            if (pinkHsv == null || blueHsv == null) return;

            File file = new File(filePath);
            FileOutputStream fileoutput = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fileoutput);
            ps.println(_configFileVersion);
            ps.println(mBlobColorRgbaPink.val[0]);
            ps.println(mBlobColorRgbaPink.val[1]);
            ps.println(mBlobColorRgbaPink.val[2]);
            ps.println(mBlobColorRgbaBlue.val[0]);
            ps.println(mBlobColorRgbaBlue.val[1]);
            ps.println(mBlobColorRgbaBlue.val[2]);

            ps.close();
            fileoutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean readConfig(String filePath) {
        try
        {
            if (mDetectorPink == null || mDetectorBlue == null) return false;

            Scanner in = new Scanner(new FileReader(filePath));
            String version = in.next();
            if (!version.equals(_configFileVersion)) return false;
            setPinkColor(new Scalar(Double.parseDouble(in.next()), Double.parseDouble(in.next()), Double.parseDouble(in.next())), false);
            setBlueColor(new Scalar(Double.parseDouble(in.next()), Double.parseDouble(in.next()), Double.parseDouble(in.next())), false);
            in.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
