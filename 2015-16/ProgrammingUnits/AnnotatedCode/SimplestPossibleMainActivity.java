// A line that begins with double-slash (//) is a comment.
// The computer ignores comments. You can put anything you want in a comment.
// Comments are a way for you to add notes to your code.

package drwtcp.ftc.com.myapplication;       // Separate apps go in separate packages.

// 'import' statements tell java which packages you are using.
// 'import' statements are often added when you hit 'alt-enter'.
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// Each java file contains one class. The name of the file and the name of the class are the same.
// So in this case we have the class SimplestPossibleMainActivity inside the file SimplestPossibleMainActivity.java.


public class SimplestPossibleMainActivity extends Activity {        // <--- Start of class

    // Classes contain functions. 'Method' is another word for function.
    // This class contains one function called 'onCreate'.

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // <-- Start of function onCreate

        // The function 'onCreate' is called when the app starts.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }                                                       // <-- End of function onCreate

}                                                                     // <---- End of class
