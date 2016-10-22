## Vuforia

Team Fixit3491 put together a series of videos that explain how to work with Vuforia. 
All of the info below is from those videos and needs to be validated.

Here's how it works:

1 - You download the object scanner to a phone. Any phone. You get the scanner here: https://developer.vuforia.com/downloads/tool

1b - Unzip the file then install the apk on your phone quite simply: `adb install scanner-5-5-11.apk`

2 - You scan an object. Follow the instructions in this video: https://www.youtube.com/watch?v=8fjpgjViSAY. More details are available on Vuforia's website: http://library.vuforia.com/articles/Training/Vuforia-Object-Scanner-Users-Guide
You need the object scanner target page, which I've uploaded [here](Letter-ObjectScanningTarget[1].pdf).

2b - Get the scanned object off of your phone via ` adb pull sdcard/VuforiaObjectScanner/ObjectReco/tapeMeasure.od` 
     (or whatever you named it. You can list the files using `adb shell ls`).

3 - You upload the object to the Vuforia Target Manager: https://developer.vuforia.com/targetmanager/project/deviceTargetListing
     (instructions here: http://library.vuforia.com/articles/Solution/How-To-Edit-and-Upload-Object-Data-Files)
     
4 - You download the database from the web site onto your PC and add it as a resource into Android Studio.

5 - You write an opmode that detects the object and acts accordingly. See details in Team Fixit 341's videos 3 and 4.

