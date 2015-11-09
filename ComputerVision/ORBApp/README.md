This app uses the ORB Feature Detection Algorithm from Open CV.

To start, point the camera at an object you'd like to track. Then hit 'Train'. The object should be on a solid background so that no extranuous feature points are detected.

Then try moving the camera around and watch the app try to locate your object on the screen.

There are 3 modes which are accessible from the '...' menu in the upper right:   
1. Match mode. This is the default. It shows the original object and draws lines to the feature points it is able to match.   
2. Box mode. This uses findHomography() routine to draw a box around the object. Not perfect if the object is too small. Designed for planar objects.   
3. Draw key points. Just shows you the detected key points.   

This app uses both 'cross check' and an arbitrary maximum distance to filter matches.


