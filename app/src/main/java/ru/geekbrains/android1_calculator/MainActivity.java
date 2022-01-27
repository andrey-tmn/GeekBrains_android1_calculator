package ru.geekbrains.android1_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        findViewById(R.id.button_number_zero).setOnClickListener(view -> numberButtonClick("0"));
        findViewById(R.id.button_number_one).setOnClickListener(view -> numberButtonClick("1"));
        findViewById(R.id.button_number_two).setOnClickListener(view -> numberButtonClick("2"));
        findViewById(R.id.button_number_three).setOnClickListener(view -> numberButtonClick("3"));
        findViewById(R.id.button_number_four).setOnClickListener(view -> numberButtonClick("4"));
        findViewById(R.id.button_number_five).setOnClickListener(view -> numberButtonClick("5"));
        findViewById(R.id.button_number_six).setOnClickListener(view -> numberButtonClick("6"));
        findViewById(R.id.button_number_seven).setOnClickListener(view -> numberButtonClick("7"));
        findViewById(R.id.button_number_eight).setOnClickListener(view -> numberButtonClick("8"));
        findViewById(R.id.button_number_nine).setOnClickListener(view -> numberButtonClick("9"));
    }

    private void numberButtonClick(String currentSymbol) {
        calculator.addSymbol(currentSymbol);
        resultTextView.setText(calculator.getValue());
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