# Team10182
## DRW Trading College Prep - FTC Robotics Team

We meet at the school:
- Thursdays from 3:30 to 5:30pm.
- Saturdays from 10am to noon. (Starting 9/29).

#### The 2018-19 game is called Rover Ruckus.

- [Video explanation](https://www.youtube.com/watch?v=rR4gR4l2XA8) - Skip to the 2:06 minute mark.
- [One page overview](https://firstinspiresst01.blob.core.windows.net/ftc/2019/gonemlpg.pdf)

Resources:
- [Resource Library](https://www.firstinspires.org/resource-library/ftc/game-and-season-info)
- [Rules Manual, Part 1](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/2018-2019/game-manual-part-1.pdf) <- General rules about sportsmanship, core values etc.
- [Rules Manual, Part 2](https://firstinspiresst01.blob.core.windows.net/ftc/2019/gemf2.pdf) <- Start here. Rules specific to the Rover Ruckus game.
- [Engineering Notebook](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/engineering-notebook-guidelines.pdf). Teams are judged on their Engineering Notebook. We've struggled to produce this in previous years. It requires a diligent student to track the team over the course of the season.
- [Tech Forum](https://ftcforum.usfirst.org/). For Q&A with other teams and with FTC head-quarters.
- [Field Assembly Manual](https://firstinspiresst01.blob.core.windows.net/ftc/2019/fieldsetjhupguide1.1.pdf)

For coaches:
- [Team Management Resources](https://www.firstinspires.org/resource-library/ftc/team-management-resources) - Tons of links re team-building, coaching, fund raising etc.
- [First Illinois Robotics](https://www.firstillinoisrobotics.org/ftc/). They organize our meets here in Illinois.

#### Things we need to order:
- [We need to register](https://www.firstinspires.org/)
- [Kit Of Parts Options](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/kit-of-parts.pdf) We will buy these when registering.
- [Full Game Set](http://www.andymark.com/Rover-Ruckus-p/am-3890.htm)

#### Programming the Bots ####

As in previous years, we will divide the team into a Build Team and a Programming Team. The Build Team doesn't need to learn Java/Android Studio, etc. They will have plenty to do without getting into this and vice versa.

So for the Programming Team:   
As is other years, we will use Android Studio. Download the latest version of Android Studio, then attempt to open the [source code](https://github.com/ftctechnh/ftc_app). Follow the prompts to upgrade Gradle etc. It hould all build right out of the box without any modifications required.

I've created a [separate page for programming](Programming.md).

#### Building the Bots ####
Each bot consists of 1) a chassis including motors, 2) a set of Controllers, Sensors and cables, and 3) phones and game consoles.

1) For the Chassis we have 2 options.
- Option 1: Use our existing [Tetrix Competition Set](https://www.pitsco.com/TETRIX-FTC-Competition-Set/&TXredir=1).
- Option 2: Use the new [REV Starter Kit](http://www.revrobotics.com/rev-45-1270/) ([parts list](http://www.revrobotics.com/content/docs/REV-45-1270-PL.pdf) ). Note: the kit has cables, batteries, but no sensors or controllers.

2) For the Electronics and cables we also have 2 options:
- Option 1: Use our existing Modern Robotics [Controllers](https://modernroboticsinc.com/robot-electronics-bundle-2) and [Sensors](https://modernroboticsinc.com/sensors). 
- Option 2: Use the new REV [electronics](http://www.revrobotics.com/rev-for-ftc/electronics/control-system/) and [sensors](http://www.revrobotics.com/ftc/electronics/sensors/) and [cables](http://www.revrobotics.com/ftc/electronics/cables/). This [video](https://www.youtube.com/watch?v=DKwpEyKbTqM&index=2&list=PLEuGrYl8iBm6SyJ41h1qQdl6en5YNCydi) shows how to wire up the REV electronics.

3) We have plenty of phones and game consoles from previous seasons.

Building Guides:
- Here is a [guide](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/basic-bot-guide-tetrix.pdf) to build a simple Tetrix robot chassis using the Tetrix set with REV Electronics.
- Here is a [guide](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/basic-bot-guide-rev.pdf) to build a simple robot chassis using the REV set and REV Electronics.
- Here is a [guide](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/basic-bot-guide-tetrix-part2.pdf) to a more advanced Tetrix bot that is suited for Rover Ruckus.
None of those guides use the Modern Robotics electronics, but they are allowed this season. REV seems simpler, but we may not have all the parts.

Other important information for builders:
[Robotics Wiring Guide](https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/robot-wiring-guide.pdf)

#### More on REV ####

You can ignore this section until you actually start to build one of the bots listed above.   

The new Rev Robotics Expansion Hub:
- [Explanatory Video](https://www.youtube.com/watch?v=7FIayseEtrk)
- [Expansion Hub Guide](http://www.revrobotics.com/content/docs/REV-31-1153-GS.pdf). Many great details including how to connect to Modern Robotics sensors, etc.
- [Step-by-Step Guide to Configuration using the new Rev Kit](https://github.com/ftctechnh/ftc_app/wiki/Configuring-Your-Hardware)
- [A longer step-by-step guide which shows how to wire up Rev components]( https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/android-studio-tutorial.pdf)
- [All Rev Resources](http://www.revrobotics.com/resources). Includes pretty advanced drive trains.
- [Rev Electronics Tutorials](https://www.youtube.com/playlist?list=PLEuGrYl8iBm6SyJ41h1qQdl6en5YNCydi) - Videos.

Notes:
 For teams using existing Modern Robotics I2C sensors, because the Expansion Hub uses a 3.3V logic-level while the Modern Robotics sensors use a 5V logic-level. A team will need to use a special adapter cable with a logic-level converter included that will allow their 5V Modern Robotics I2C sensors to be used with the new Expansion Hub. Teams will be able to purchase the cables and adapters through the FIRST storefront. 

For the other Modern Robotics sensors (touch sensor and optical distance sensor) the REV Robotics Expansion Hub kit will include 3.3V alternative sensors that work directly with the Expansion Hub.

Teams also have the option of using their existing Modern Robotics Core Device Interface modules in parallel to an Expansion Hub to connect their existing 5V sensors to their Robot Controller.

