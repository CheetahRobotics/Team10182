// This example class comes from: http://www.stonekick.com/techblog/using_Android_SeekBars.html
package com.stonekick.seekbar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


// Note the words 'implements OnSeekBarChangeListener'. This tells java that we can handle
// callbacks from the SeekBar.
public class SeekBarMainActivity extends Activity implements OnSeekBarChangeListener {

    // A class can contain functions and variables.
    // The 3 variables we declare here are 'seekbar1', 'value' and 'result'.
    // Sice they are declared outside a function they can be used by all function in the class.
    
    SeekBar seekbar1;       // This declares a variable called 'seekbar1'. It is of type 'SeekBar'.
    int value;              // This declares a variable called 'value'. It is of type 'int'.
    TextView result;        // This declares a variable called 'result'. It is of type 'TextView'.

    //set constructor for when activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        
        // These 2 lines are generated by Android Studio. We can ignore them for now.
        super.onCreate(savedInstanceState);           
        setContentView(R.layout.activity_seek_bar);
        
        // Our layout has 2 widgets: a SeekBar and a TextView.
        // Here we store a reference to each one.
        seekbar1 = (SeekBar)findViewById(R.id.sbBar);
        result = (TextView)findViewById(R.id.tvResult);

        // This next lines mean that this class will handle all callbacks from the SeekBar.
        // The SeekBar calls 3 functions: onProgressChanged, onStartTrackingTouch, onStopTrackingTouch.
        // Note at the top of the class where we say: 'implements OnSeekBarChangeListener'. This is
        // required too for Java to call our class with SeekBar updates.
        seekbar1.setOnSeekBarChangeListener(this);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {

        value = progress;                   // Where is the variable 'value' declared?
        result.setText ("Value:"+value);    // This line puts text on the screen, in our TextView.
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // There is nothing in this function, except this comment, so the function does nothing.
        // Maybe later we'll want to put some code here.
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // There is nothing in this function, except this comment, so the function does nothing.
        // Maybe later we'll want to put some code here.
    }
}