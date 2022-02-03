package ru.geekbrains.android1_calculator;

import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormatSymbols;

import ru.geekbrains.android1_calculator.сalculator.Calculator;
import ru.geekbrains.android1_calculator.сalculator.CalculatorOperation;
import ru.geekbrains.android1_calculator.сalculator.CalculatorSymbol;

public class CalculatorPresenter implements Serializable {

    private static final char DOT = '.';

    private final Calculator calculator;
    private transient TextView resultTextView;
    private transient TextView historyTextView;

    public CalculatorPresenter() {
        this.calculator = new Calculator();
    }

    public void setOutputTextViews(TextView result, TextView history) {
        resultTextView = result;
        historyTextView = history;
        showAll();
    }

    public void operationButtonClicked(CalculatorOperation operation) {
        calculator.doOperation(operation);
        showAll();
    }

    public void symbolButtonClicked(CalculatorSymbol symbol) {
        calculator.addSymbol(symbol);
        showCurrentValue();
    }

    private void showAll() {
        showCurrentValue();
        showHistory();
    }

    private void showHistory() {
        historyTextView.setText(changeDecimalSeparator(calculator.getHistory()));
    }

    private void showCurrentValue() {
        resultTextView.setText(changeDecimalSeparator(calculator.getValue()));
    }

    private String changeDecimalSeparator(String val) {
        char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        if (decimalSeparator != DOT) {
            String regex = "\\" + DOT;
            return val.replaceAll(regex, String.valueOf(decimalSeparator));
        } else {
            return val;
        }
    }
}
