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
