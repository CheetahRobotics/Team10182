### OpenCV-AndroidStudioBuild

First I downloaded version 3.0 of OpenCV from http://opencv.org/downloads.html. 

Using the code in the sdk/java directory of that download, I *roughly* followed the instructions here: http://stackoverflow.com/a/27421494, (up thru step 3), and then built the .aar file. 

You can load this project directly into Android Studio and build the .aar.

### How you use Open CV with your Android app:

The .aar file is very small. The main OpenCV code is a separate download from Google Play. You will be prompted to down-load this file the first time you run your app on the phone.   

1. Create project with an activity.

2. Put  the opencv .aar in libs directory.

3. Add compile line to dependencies section to build.gradle.

       compile(name: ‘openCVLibrary249’, ext: 'aar’)

4. Add repositories section to build.gradle.

    repositories {
        mavenCentral()
        flatDir {
            dirs 'libs’
        }
    }

5. Add camera permissions to AndroidManifest.xml.

### Using non-free modules.

Some algorithms in OpenCV are patented, i.e. are not free. (Examples: SIFT, SURF, FREAK). They are not available by default in the Android package.  The simplest way to use them is as follows:   

1. This page shows how to rebuild the code using JNI and it is a painful mess: https://sites.google.com/site/wghsite/technical-notes/sift_surf_opencv_android. However, you can simply download the already built .so files from that site. At this link specifically: https://sites.google.com/site/wghsite/technical-notes/sift_surf_opencv_android/sift_surf_opencv_android_package.zip?attredirects=0.  The 2 files you need to pull out of that zip file are: *libnonfree.so* and *libopencv_java.so*.   

2. Create these directories under app/arc/main: jniLibs/armeabi and jniLibs/armeabi-v7a. Put the 2 .so files in each directory.   

3. Add the following line to your MainActivity.java in onManagerConnected in the SUCCESS case:   

    System.loadLibrary("nonfree");
    
    
