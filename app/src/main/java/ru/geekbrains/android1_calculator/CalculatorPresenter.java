package ru.geekbrains.android1_calculator;

import android.widget.TextView;

import java.io.Serializable;

import ru.geekbrains.android1_calculator.сalculator.Calculator;
import ru.geekbrains.android1_calculator.сalculator.CalculatorOperation;

public class CalculatorPresenter implements Serializable {
    private Calculator calculator;
    private TextView resultTextView;
    private TextView historyTextView;

    public CalculatorPresenter() {
        this.calculator = new Calculator();
    }

    public void setOutputTextViews(TextView result, TextView history){
        resultTextView = result;
        historyTextView = history;
    }

    public void operationButtonClicked(CalculatorOperation operation){

        resultTextView.setText(changeDecimalSeparator(calculator.getValue()));
        historyTextView.setText(changeDecimalSeparator(calculator.getHistory()));
    }

    public void symbolButtonClicked(CalculatorSymbols symbol){

    }

    private String changeDecimalSeparator(String val){
        return val;
    }
}
