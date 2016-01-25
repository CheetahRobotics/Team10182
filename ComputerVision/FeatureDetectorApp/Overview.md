package drwtcp.ftc.com.cv;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Sample {
    DescriptorExtractor descriptorExtractor;
    DescriptorMatcher _matcher;
    FeatureDetector _detector;

    Mat _descriptors;
    MatOfKeyPoint _keypoints;

    Mat _descriptors2;
    MatOfKeyPoint _keypoints2;

    private Mat trainFeatureDetector(CvCameraViewFrame inputFrame) {
        Mat gray1 = inputFrame.gray();

        _descriptors = new Mat();
        _keypoints = new MatOfKeyPoint();

        _detector = FeatureDetector.create(FeatureDetector.ORB);
        _matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);
        _detector.detect(gray1, _keypoints, _descriptors);

        descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
        descriptorExtractor.compute(gray1, _keypoints, _descriptors);
    }
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat gray2 = inputFrame.gray();
        _descriptors2 = new Mat();
        _keypoints2 = new MatOfKeyPoint();

        _detector.detect(gray2, _keypoints2);
        descriptorExtractor.compute(gray2, _keypoints2, _descriptors2);

        MatOfDMatch matches12 = new MatOfDMatch();
        _matcher.match(_descriptors, _descriptors2, matches12);
        List<DMatch> matches12_list = matches12.toList();

        // .... filter based on distance... 
        //-- Quick calculation of min distances between keypoints
        double min_dist = 0;
        for (int i = 0; i < matches12_list.size(); i++) {
            double dist = matches12_list.get(i).distance;
            if (dist < min_dist) min_dist = dist;
        }

        //-- Draw only "good" matches (i.e. whose distance is less than 3*min_dist )
        List<DMatch> good_matches_list = new ArrayList<>();
        for (int i = 0; i < matches12_list.size(); i++) {
            if (matches12_list.get(i).distance < 3 * min_dist) {
                good_matches_list.add(matches12_list.get(i));
            }
        }

        drawMatches(gray2, _keypoints2, good_matches_list, (double) gray2.height(), (double) gray2.width());
    }
}
