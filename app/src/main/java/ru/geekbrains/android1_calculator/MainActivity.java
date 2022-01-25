package ru.geekbrains.android1_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    static private final String CALCULATOR_IN_BUNDLE = "CALCULATOR_IN_BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState)
            calculator = new Calculator();
        else
            calculator = (Calculator) savedInstanceState.getSerializable("CALCULATOR_IN_BUNDLE");

    }
}