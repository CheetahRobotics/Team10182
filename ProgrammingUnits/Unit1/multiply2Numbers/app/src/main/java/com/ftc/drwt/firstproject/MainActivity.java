package com.ftc.drwt.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Helper getNumbers(View view) {
        EditText firstNumberTextBox = (EditText) findViewById(R.id.firstNumber);
        EditText secondNumberTextBox = (EditText) findViewById(R.id.secondNumber);
        EditText thirdNumberTextBox = (EditText) findViewById(R.id.thirdNumber);
        Helper helper = new Helper();
        helper.n1 = Double.parseDouble(firstNumberTextBox.getText().toString());
        helper.n2 = Double.parseDouble(secondNumberTextBox.getText().toString());
        helper.n3 = Double.parseDouble(thirdNumberTextBox.getText().toString());
        return helper;
    }

    public void addNumber(View view) {
        Helper helper = getNumbers(view);

        TextView myAwesomeTextView = (TextView) findViewById(R.id.someId);
        myAwesomeTextView.setText(Double.toString(helper.n1 + helper.n2 + helper.n3));

    }
    public void multiplyNumber(View view) {
//        double result = Math.sqrt(12.0);
        TextView myAwesomeTextView = (TextView) findViewById(R.id.someId);
        EditText firstNumberTextBox = (EditText) findViewById(R.id.firstNumber);
        EditText secondNumberTextBox = (EditText) findViewById(R.id.secondNumber);
        EditText thirdNumberTextBox = (EditText) findViewById(R.id.thirdNumber);

        double n1 = Double.parseDouble(firstNumberTextBox.getText().toString());
        double n2 = Double.parseDouble(secondNumberTextBox.getText().toString());
        double n3 = Double.parseDouble(thirdNumberTextBox.getText().toString());


        myAwesomeTextView.setText(Double.toString(n1 * n2 * n3));

    }


}
