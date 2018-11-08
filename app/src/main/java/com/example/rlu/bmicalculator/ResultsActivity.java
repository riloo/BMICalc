package com.example.rlu.bmicalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setupToolBar();
        setupFAB();

        //need to make method to process incoming data
        processIncomingData();
    }

    private void processIncomingData()
    {
        //create references to each of the textviews in xml
        TextView tvHeight = findViewById(R.id.tv_height);
        TextView tvWeight = findViewById(R.id.tv_weight);
        TextView tvBMI = findViewById(R.id.tv_bmi);
        TextView tvBMIGroup = findViewById(R.id.tv_bmi_group);

        //bring in the bundle from the intent object
        Bundle incomingData = getIntent().getExtras();
        if(incomingData != null)
        {
            String JSON = incomingData.getString("BMI");
            BMICalc mCurrentBMI = BMICalc.getObjectFromJSONString(JSON);

            //store locally the values from the bundle
            double height = mCurrentBMI.getHeight();
            double weight = mCurrentBMI.getWeight();
            double bmi = mCurrentBMI.getBMI();
            String bmiGroup = mCurrentBMI.getBMIGroup();

            //append each of those values into the respective textview
            tvHeight.setText(getString(R.string.height) + ":" + height);
            tvWeight.setText("Weight: " + weight);
            tvBMI.setText("BMI: " + bmi);
            tvBMIGroup.setText("BMI Group: ".concat(bmiGroup));

        }


    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
