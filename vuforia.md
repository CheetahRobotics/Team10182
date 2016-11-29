## Vuforia

The instructions below are accurate but we won't need to go into this detail. We'll be using the Vuforia Concept which handles Vuforia much better. So this is of interest, but not necessary.

Here's how it works:

1 - You download the object scanner to a phone. Any phone. You get the scanner here: https://developer.vuforia.com/downloads/tool

1b - Unzip the file then install the apk on your phone quite simply: `adb install scanner-5-5-11.apk`

2 - You scan an object. Follow the instructions in this video: https://www.youtube.com/watch?v=8fjpgjViSAY. More details are available on Vuforia's website: http://library.vuforia.com/articles/Training/Vuforia-Object-Scanner-Users-Guide
You need the object scanner target page, which I've uploaded [here](Letter-ObjectScanningTarget[1].pdf).

2b - Get the scanned object off of your phone via ` adb pull sdcard/VuforiaObjectScanner/ObjectReco/tapeMeasure.od` 
     (or whatever you named it. You can list the files using `adb shell ls`).

3 - You upload the object to the Vuforia Target Manager: https://developer.vuforia.com/targetmanager/project/deviceTargetListing
     (instructions here: http://library.vuforia.com/articles/Solution/How-To-Edit-and-Upload-Object-Data-Files). Email me to get our username and password at rlwoodley@gmail.com.
     
4 - In this step, you download the database from the web site onto your PC and add it as a resource into Android Studio. To do this, click on the database name in Vuforia Target Manager, then click on 'Download Database'. Unzip the database and paste the files into the 'src/main/assets' directory in Android Studio. This video shows how to do all this: https://www.youtube.com/watch?v=8fjpgjViSAY

5 - You write an opmode that detects the object and acts accordingly. See details in Team Fixit 3491's video 4: https://www.youtube.com/watch?v=2z-o9Ts8XoE. Here is the license key:


Af8mJUL/////AAAAGamRrtduq0AYioQ8+Iqr61Uf6oyUkAu3UyV/z77f3FXEb3oRQHebg2Mb+1EEk/hWjAHiAAwNyvsn74IIHK6foNJh6jGB/YqZ7k/vKaQ8nYW3PGoKBLgT8/qVT8pJ6TcUcQ81IBgPTxJ9LT5scw989dT3tolt4Y3Jybgo9Yb5DW9tPT4xc/Qb/UkJ/WQt0Z4e/PP/l8BhUhX2JN8kpPHi+P/J3/qH/XHUhkf5rYTLf3ZMESKbovJ+ieUdMcXKLtyeKhjvlZ+WStZ27XE/eMmfQHWeK+qmKg9EusYQEVLiptwKHSIAsMfCH04xqDA8pIrwIVCuGN9M+763IDBnb7P4GabnYOiQuJtAar2rnhTcnFNF





