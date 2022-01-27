package ru.geekbrains.android1_calculator;

import java.io.Serializable;

public class Calculator implements Serializable {
    private String showingValue = "";
    private double previousValue = 0;

    public String addSymbol() {
        return showingValue;
    }

    public String getValue() {
        return showingValue;
    }

    public void reset() {
        showingValue = "";
        previousValue = 0;
    }

    public void backspace() {
        //TODO
    }

    public void result() {
        //TODO
    }

    public void decimal_separator() {
        //TODO
    }

    public void addition() {
        //TODO
    }

    public void subtraction() {
        //TODO
    }

    public void division() {
        //TODO
    }

    public void multiplication() {
        //TODO
    }
}
