Uses OpenCV's blob detection to identify the FTC beacon reliably (we hope).   

Algorithm:   
1. Search for pink blobs and blue blobs. The initial values for pink and blue are hard-coded, but you can recalibrate 
by touching the colored square on the upper-left of the screen and then tapping an object of the desired color.   
2. Find bounding boxes for each contour.   
3. Select the largest pink bounding box and blue bounding box and average the center points.   

