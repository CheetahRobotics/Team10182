You usually want to debug the robot controller while it is connected to the robot. But you can't plug the phone into the laptop because it is already plugged into the robot and there is only one USB plug on the phone. But there is a solution: you can connect the laptop to the phone WIRELESSLY, over WIFI. Here is how.
 1. The phone needs to be on a local wifi network like 'NobleStudentCloud'. The open the Settings menu on the phone and click 'About phone'. Then click on 'Status'. Then scroll down to where the IP address is.
 2. Connect the phone to the laptop.
 3. Open a CMD window and type `adb tcpip 5555`.
 4. The type `adb connect ipaddress`. ipaddress is the ipaddress you wrote down in step one.
 5. Now unplug the phone from the laptop and plug it into the robot. You can still start, stop and debug your phone from Android Studio.
 6. You can always type `adb devices` to confirm your laptop is still connected to the phone. If you lose a connection you can alwyas reconnect using the `adb connect` command in step 4 above.

Attention! OSX users, you need to add the path to `adb` to your `PATH`. So add a line like the following to your `\.bash_profile`.   

`export PATH="/Users/robertwoodley/Library/Android/sdk/platform-tools/:$PATH"`   

(Obviously replace 'robertwoodley' with your username).

