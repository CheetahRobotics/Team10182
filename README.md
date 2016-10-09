# Team10182
## DRW Trading College Prep - FTC Robotics Team

A new season begins!

#### The 2016-17 game is called Velocity Vortex.

- [Video explanation] (https://www.youtube.com/watch?v=iQLrcQbm8cg)
- [One page overview] (https://firstinspiresst01.blob.core.windows.net/ftc/velocity-vortex-one-page.pdf)
- [Training Document] (https://github.com/ftctechnh/ftc_app/files/464313/FTC_SDK_UserMan_v2_2_rel01.pdf). Read this first, it has so much information about how to set up phones and laptops.

Rules Manuals:
- [Part 1](https://firstinspiresst01.blob.core.windows.net/ftc/game-manual-part-1.pdf) - These are general guidelines that don't change much from year-to-year.
- [Part 2](https://firstinspiresst01.blob.core.windows.net/ftc/game-manual-part-2.pdf) - These are specific to Velocity Vortex.
- [Engineering Notebook](http://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/engineering-notebook-guidelines.pdf)

What is new this year?
- There is a new version of the FTC code, version 2.20. A lot has changed. Please see this [VERY IMPORTANT VIDEO](https://www.youtube.com/watch?v=TKPscPqsz8s&index=1&list=PLKxIFjlI_T1zKVjYZ6S1FeqeqW1_pnzE2) to understand the new way we are going to program robots this year: 
- There is a new computer vision library called Vuforia. Looks much easier to use then OpenCV and there is support for it in the ftc app. See the link below.
- Here is a good set of slides that discuss some of the important changes to the ftc_app: http://ftc.flfirst.org/images/Home_Files/Documents/2016/2016_SDK_Changes.pdf

Other resources:
- Details on the playing field kit can be found on the [Hardware Page](Hardware.md)
- [Pushbot Guide](http://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/2016-2017-season/pushbot-build-guide.pdf) - Build instructions start on page 102.
- [Pushbot Guide - Sensor Supplement](http://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/pushbot-build-guide-tetrix-sensors-supplement.pdf)
- [Electronics Notes](https://firstinmaryland.org/images/docs/2016Docs/FTC_Kickoff/FTC_Wiring_and_ESD_2016_v1.pdf). Good discussion of the robotics electronics subsystem.
- [Vuforia Resources](vuforia.md). Vuforia is the computer vision software that FTC is encouraging teams to use for this season.
- [VERY IMPORTANT VIDEO](https://www.youtube.com/watch?v=TKPscPqsz8s&index=1&list=PLKxIFjlI_T1zKVjYZ6S1FeqeqW1_pnzE2) about the ftc_app 2.2.

FAQ:
- I want to start looking at the Robot Controller code in Android Studio on my home PC. How do I do this?
     1. You need Java 8 and Android studio installed. The document linked to above shows you how to do this.
     2. You need 'git' installed on your PC. You can install it here: https://git-scm.com/
     3. You need the source code for the Robot Controller on your PC. Please ignore the instructions in the above document and instead do the instructions which are [here](GitQuickStart.md).
     4. Start Android Studio and click on 'Open an existing Android Studio project' and open the directory you just created.
- How do do wireless debugging of the Robot Controller?   
     You usually want to debug the robot controller while it is connected to the robot. But you can't plug the phone into the laptop because it is already plugged into the robot and there is only one USB plug on the phone. But there is a solution: you can connect the laptop to the phone WIRELESSLY, over WIFI. Here is how.
     1. The phone needs to be on a local wifi network like 'NobleStudentCloud'. The open the Settings menu on the phone and click 'About phone'. Then click on 'Status'. Then scroll down to where the IP address is.
     2. Connect the phone to the laptop.
     3. Open a CMD window and type `adb tcpip 5555`.
     4. The type `adb connect ipaddress`. ipaddress is the ipaddress you wrote down in step one.
     5. Now unplug the phone from the laptop and plug it into the robot. You can still start, stop and debug your phone from Android Studio.
     6. You can always type `adb devices` to confirm your laptop is still connected to the phone. If you lose a connection you can alwyas reconnect using the `adb connect` command in step 4 above.
     
     

