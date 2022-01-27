package ru.geekbrains.android1_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private TextView resultTextView;
    private static final String CALCULATOR_IN_BUNDLE = "CALCULATOR_IN_BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState)
            calculator = new Calculator();
        else
            calculator = (Calculator) savedInstanceState.getSerializable("CALCULATOR_IN_BUNDLE");

        resultTextView = findViewById(R.id.textview_result);
        resultTextView.setText(calculator.getValue());

        findViewById(R.id.button_operation_reset).setOnClickListener(view -> doOperation("reset"));
        findViewById(R.id.button_operation_backspace).setOnClickListener(view -> doOperation("backspace"));
        findViewById(R.id.button_operation_result).setOnClickListener(view -> doOperation("result"));
        findViewById(R.id.button_operation_decimal_separator).setOnClickListener(view -> doOperation("decimal_separator"));
        findViewById(R.id.button_operation_addition).setOnClickListener(view -> doOperation("addition"));
        findViewById(R.id.button_operation_subtraction).setOnClickListener(view -> doOperation("subtraction"));
        findViewById(R.id.button_operation_division).setOnClickListener(view -> doOperation("division"));
        findViewById(R.id.button_operation_multiplication).setOnClickListener(view -> doOperation("multiplication"));

        
    }

    private void doOperation(final String operation) {
        switch (operation) {
            case "reset":
                calculator.reset();
                break;
            case "backspace":
                calculator.backspace();
                break;
            case "result":
                calculator.result();
                break;
            case "decimal_separator":
                calculator.decimal_separator();
                break;
            case "addition":
                calculator.addition();
                break;
            case "subtraction":
                calculator.subtraction();
                break;
            case "division":
                calculator.division();
                break;
            case "multiplication":
                calculator.multiplication();
                break;
        }
        resultTextView.setText(calculator.getValue());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(CALCULATOR_IN_BUNDLE, calculator);
    }
}