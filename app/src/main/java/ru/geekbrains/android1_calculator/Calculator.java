package ru.geekbrains.android1_calculator;

import java.io.Serializable;

public class Calculator implements Serializable {
    private static char DECIMAL_SEPARATOR_SYMBOL = ',';
    private StringBuilder showingValue;
    private double previousValue = 0;

    public Calculator() {
        showingValue = new StringBuilder("0");
    }

    public String addSymbol(String symbol) {
        if (!symbol.equals("0")) {
            if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0)))
                showingValue.setLength(0);
            showingValue.append(symbol);
        } else {
            if ((showingValue.length() > 1) || ('0' != showingValue.charAt(0)))
                showingValue.append(symbol);
        }

        return showingValue.toString();
    }

    public String getValue() {
        return showingValue.toString();
    }

    public void reset() {
        showingValue.setLength(0);
        showingValue.append("0");
        previousValue = 0;
    }

    public void backspace() {
        if (1 == showingValue.length()) {
            if ('0' != showingValue.charAt(0))
                showingValue.setCharAt(0, '0');
        } else if (showingValue.length() > 1) {
            showingValue.setLength(showingValue.length() - 1);

            // Если не нужно оставлять разделитель дробной части один в конце
            //if (DECIMAL_SEPARATOR_SYMBOL == showingValue.charAt(showingValue.length() - 1))
            //    showingValue.setLength(showingValue.length() - 1);

        }
    }

    public void result() {
        //TODO
    }

    public void decimal_separator() {
        if (-1 == showingValue.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL)))
            showingValue.append(DECIMAL_SEPARATOR_SYMBOL);
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

    public void setDecimalSeparator(char newDecimalSeparatorChar) {
        if (newDecimalSeparatorChar != DECIMAL_SEPARATOR_SYMBOL) {
            int decimalSeparatorPosition = showingValue.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
            if (-1 != decimalSeparatorPosition)
                showingValue.setCharAt(decimalSeparatorPosition, newDecimalSeparatorChar);
            DECIMAL_SEPARATOR_SYMBOL = newDecimalSeparatorChar;
        }
    }
}
