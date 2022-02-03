package ru.geekbrains.android1_calculator.presenters;

import java.io.Serializable;
import java.text.DecimalFormatSymbols;

import ru.geekbrains.android1_calculator.domain.сalculator.Calculator;
import ru.geekbrains.android1_calculator.domain.сalculator.CalculatorInterface;
import ru.geekbrains.android1_calculator.domain.сalculator.CalculatorOperation;
import ru.geekbrains.android1_calculator.domain.сalculator.CalculatorSymbol;
import ru.geekbrains.android1_calculator.ui.CalculatorView;

public class CalculatorPresenter implements Serializable {

    private static final char DOT = '.';

    private final CalculatorInterface calculator;
    private transient CalculatorView view;

    public CalculatorPresenter(CalculatorView view) {
        this.calculator = new Calculator();
        this.view = view;
        showAll();
    }

    public void setView(CalculatorView view) {
        this.view = view;
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
        view.showHistory(changeDecimalSeparator(calculator.getHistory()));
    }

    private void showCurrentValue() {
        view.showValue(changeDecimalSeparator(calculator.getValue()));
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
