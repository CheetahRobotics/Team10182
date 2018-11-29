We programming our app using the Java programming language. We work in an editor called Android Studio to create these Java programs.

#### Intro to Java. Learning Resources ###

[CodeHS](https://codehs.com/info/curriculum/apjava). Many students at DRW College Prep have experimented with Code HS and enjoyed it. These seems the easiest way to begin learning java. Contact one of your mentors to get started.

[Java Course](http://mooc.fi/courses/2013/programming-part-1/material.html)

Facts about Java:
- 97% of Enterprise Desktops Run Java
- 89% of Desktops (or Computers) in the U.S. Run Java
- 9 Million Java Developers Worldwide
- 3 Billion Mobile Phones Run Java
- 100% of Du-ray Disc Players Ship with Java
- 5 Billion Java Cards in Use
- 125 million TV devices run Java

[Programming FTC - Training Manual](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/android-studio-tutorial.pdf). If you know Java but are trying to figure out just how the heck we use the phones and Android Studio in FTC, this has it all.

#### Programming notes related to FTC. ####

These are advanced topics in no particular order. I'll organize them later.

[FTC Gamepad Map](ftc_gamepad_map.jpg) - How the buttons map to the variables.

[Remote Debugging](RemoteDebugging.md)

If the build in Android Studio is slow, you might try what is suggested in this note: http://stackoverflow.com/a/35813097/2195930, though this is a few years old and the build has gotten better.

#### Computer Vision ####
Computer Vision (CV) can be very useful, especially in the autonomous phase. There are 3 commonly used CV frameworks in FTC:
* Vuforia
* [Tensor Flow](https://github.com/google/ftc-object-detection)
* Open CV
In previous years we have experimented with Vuforia and with Open CV, though we never used either in competition.

##### Vuforia

FTC recommends using Vuforia with the [navigation targets](https://firstinspiresst01.blob.core.windows.net/ftc/2019/navigation-target-us.pdf) and has [code examples](https://github.com/ftctechnh/ftc_app/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptVuforiaNavRoverRuckus.java) that demonstrate how to do this. This code allows you to determine your X/Y position on the playing field.

##### OpenCV/Grip

Open CV could be very useful this year in identifying the mineral configuration in the autonomous phase.
There is something called GRIP which allows us to experiment with Open CV configuration before you actually have to code it up. 
* Download [here](https://github.com/WPIRoboticsProjects/GRIP/releases). 
* Here is a [good introductory video](https://www.youtube.com/watch?v=wkW8puXMoSk).
* [Documentation](https://wpilib.screenstepslive.com/s/currentCS/m/vision/l/463566-introduction-to-grip)
* [Github](https://github.com/WPIRoboticsProjects/GRIP)

It is a pain to configure OpenCV on Android Studio. I'll post some links as and when.
