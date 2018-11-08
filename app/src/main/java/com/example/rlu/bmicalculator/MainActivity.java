package com.example.rlu.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Model (m = member - start fields w an 'm')
    private BMICalc mBmiCalc;

    //View components
    private EditText mEditTextHeight, mEditTextWeight;

    //debug TAG
    private final String sTAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupFAB();
        mBmiCalc = new BMICalc();
        mEditTextHeight = findViewById(R.id.input_height);
        mEditTextWeight = findViewById(R.id.input_weight);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //new anonymous class that implements the View.OnClickListener interface
            @Override
            public void onClick(View view) {
                //this method overrides the method stub/header in the OCL interface
                //variables
                String height, weight;
                double bmi;

                //get data from the Views
                height = mEditTextHeight.getText().toString();
                weight = mEditTextWeight.getText().toString();
                Log.d(sTAG, "From EditTexts: ");
                Log.d(sTAG, "Height: " + height);
                Log.d(sTAG, "Weight: " + weight);

                try {
                    //copy into the model wat the user typed in the EditTexts
                    mBmiCalc.setHeight(Double.parseDouble(height));
                    mBmiCalc.setWeight(Double.parseDouble(weight));

                    Log.d(sTAG, "From BMI Model: ");
                    Log.d(sTAG, "Height: " + mBmiCalc.getHeight());
                    Log.d(sTAG, "Weight: " + mBmiCalc.getWeight());

                    //get from the model the BMI for those numbers(height, weight)
                    bmi = mBmiCalc.getBMI();

                    //Output that BMI from the model to the user
                    Snackbar.make(view, "BMI: " + mBmiCalc.getBMI(), Snackbar.LENGTH_LONG)
                            .setAction("Details...", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent
                                                    (getApplicationContext(),
                                                    ResultsActivity.class);

                                    //convert the BMI object into a string
                                    String currentBMI_JSON = BMICalc.getJSONStringFromObject(mBmiCalc);
                                    intent.putExtra("BMI", currentBMI_JSON);

                                    startActivity(intent);
                                }
                            }).show();
                }
                catch(IllegalArgumentException e)
                {
                        //toast = to show the user a quick message
                        Toast.makeText(MainActivity.this,
                                "Error: Height and weight must both be greater than 0",
                                Toast.LENGTH_SHORT).show();
                }

            }
        });
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
}
