package ru.geekbrains.android1_calculator;

import java.io.Serializable;

public class Calculator implements Serializable {
    private int showingValue = 0;
    private int previousValue = 0;

    public String addSymbol(){
        return Integer.toString(showingValue);
    }
}
