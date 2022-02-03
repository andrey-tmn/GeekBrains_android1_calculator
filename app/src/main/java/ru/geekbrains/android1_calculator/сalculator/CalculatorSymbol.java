package ru.geekbrains.android1_calculator.сalculator;

import androidx.annotation.NonNull;

public enum CalculatorSymbol {
    DOT('.'),
    BACKSPACE('←'),
    ZERO('0'),
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9');

    private final char symbol;

    CalculatorSymbol(char symbol) {
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
