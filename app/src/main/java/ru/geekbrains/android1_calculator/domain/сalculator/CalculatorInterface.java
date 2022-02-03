package ru.geekbrains.android1_calculator.domain.сalculator;

public interface CalculatorInterface {
    String getHistory();
    String getValue();
    void addSymbol(CalculatorSymbol symbol);
    void doOperation(CalculatorOperation operation);
}
