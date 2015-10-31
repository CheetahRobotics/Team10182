package drwtcp.ftc.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SimplestPossibleMainActivity2 extends Activity {

    // Classes contain functions. 'Method' is another word for function.
    // This class contains one function called 'onCreate'.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // The function 'onCreate' is called when the app starts.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
