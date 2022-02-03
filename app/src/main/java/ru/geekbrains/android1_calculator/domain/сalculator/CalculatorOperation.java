package ru.geekbrains.android1_calculator.domain.сalculator;

import androidx.annotation.NonNull;

public enum CalculatorOperation {
    RESET('C'),
    RESULT('='),
    ADD('+'),
    SUB('-'),
    MUL('*'),
    DIV('/');

    private final char symbol;

    CalculatorOperation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}
