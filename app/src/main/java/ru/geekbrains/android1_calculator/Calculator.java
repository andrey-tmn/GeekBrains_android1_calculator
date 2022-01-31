package ru.geekbrains.android1_calculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator implements Serializable {
    private static char DECIMAL_SEPARATOR_SYMBOL = ',';
    private static final int DECIMAL_SCALE_SYMBOLS = 9;

    private final StringBuilder showingValue;
    private final StringBuilder history;
    private BigDecimal previousValue;
    private char lastOperator = '=';
    private boolean waitingNewOperand = false;

    public Calculator() {
        showingValue = new StringBuilder("0");
        history = new StringBuilder();
        previousValue = BigDecimal.ZERO;
    }

    public String getHistoryString() {
        return history.toString();
    }

    private void changeDecimalSeparator(char oldSeparator, char newSeparator) {
        int decimalSeparatorPosition = showingValue.indexOf(String.valueOf(oldSeparator));
        if (-1 != decimalSeparatorPosition)
            showingValue.setCharAt(decimalSeparatorPosition, newSeparator);
    }

    public void setDecimalSeparator(char newDecimalSeparatorChar) {
        if (newDecimalSeparatorChar != DECIMAL_SEPARATOR_SYMBOL) {
            changeDecimalSeparator(DECIMAL_SEPARATOR_SYMBOL, newDecimalSeparatorChar);

            int decimalSeparatorPosition = history.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
            while (-1 != decimalSeparatorPosition) {
                history.setCharAt(decimalSeparatorPosition, newDecimalSeparatorChar);
                decimalSeparatorPosition = history.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
            }

            DECIMAL_SEPARATOR_SYMBOL = newDecimalSeparatorChar;
        }
    }

    public void addSymbol(String symbol) {
        if (waitingNewOperand) {
            if (DECIMAL_SEPARATOR_SYMBOL == symbol.charAt(0)) return;

            if ('=' == lastOperator) reset();
            showingValue.setLength(0);
            showingValue.append(symbol);
            waitingNewOperand = false;
            return;
        }

        if (('0' != symbol.charAt(0)) && (DECIMAL_SEPARATOR_SYMBOL != symbol.charAt(0))) {
            if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0)))
                showingValue.setLength(0);
            showingValue.append(symbol);
        } else {
            if ((showingValue.length() > 1) || ('0' != showingValue.charAt(0)) ||
                    (symbol.charAt(0) == DECIMAL_SEPARATOR_SYMBOL))
                showingValue.append(symbol);
        }
    }

    public void decimal_separator() {
        if (-1 == showingValue.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL)))
            addSymbol(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
    }

    public String getValue() {
        if (('.' != DECIMAL_SEPARATOR_SYMBOL) && (-1 != showingValue.indexOf(".")))
            changeDecimalSeparator('.', DECIMAL_SEPARATOR_SYMBOL);
        return showingValue.toString();
    }

    public void reset() {
        setShowingValueAtZero();
        previousValue = BigDecimal.ZERO;
        lastOperator = '=';
        waitingNewOperand = false;
        history.setLength(0);
    }

    public void backspace() {
        if (waitingNewOperand) {
            reset();
            return;
        }

        if (1 == showingValue.length()) {
            if ('0' != showingValue.charAt(0))
                showingValue.setCharAt(0, '0');
        } else if (showingValue.length() > 1) {
            showingValue.setLength(showingValue.length() - 1);
        }
    }

    private void setShowingValueAtZero() {
        showingValue.setLength(0);
        showingValue.append("0");
    }

    private void setFirstOperand() {
        if ('.' != DECIMAL_SEPARATOR_SYMBOL)
            changeDecimalSeparator(DECIMAL_SEPARATOR_SYMBOL, '.');
        previousValue = new BigDecimal(showingValue.toString());
    }

    private String getCurrentWithCorrectDecimalSeparator() {
        if ('.' != DECIMAL_SEPARATOR_SYMBOL)
            changeDecimalSeparator(DECIMAL_SEPARATOR_SYMBOL, '.');
        return showingValue.toString();
    }

    private void doPreviousOperation() {
        BigDecimal currentValueBigDecimal = new BigDecimal(getCurrentWithCorrectDecimalSeparator());
        BigDecimal resultBigDecimal = BigDecimal.ZERO;
        switch (lastOperator) {
            case '+':
                resultBigDecimal = previousValue.add(currentValueBigDecimal);
                break;
            case '-':
                resultBigDecimal = previousValue.subtract(currentValueBigDecimal);
                break;
            case '*':
                resultBigDecimal = previousValue.multiply(currentValueBigDecimal);
                break;
            case '/':
                resultBigDecimal = previousValue.divide(currentValueBigDecimal, DECIMAL_SCALE_SYMBOLS, RoundingMode.HALF_UP);
                break;
        }

        showingValue.setLength(0);
        double val = resultBigDecimal.doubleValue();
        if (val == (long) val)
            showingValue.append(Long.valueOf((long) val).toString());
        else
            showingValue.append(Double.valueOf(val).toString());
        if ('.' != DECIMAL_SEPARATOR_SYMBOL)
            changeDecimalSeparator('.', DECIMAL_SEPARATOR_SYMBOL);
        waitingNewOperand = true;
    }

    public void result() {
        if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0))) return;
        if ('=' == lastOperator) return;
        if (0 == previousValue.compareTo(BigDecimal.ZERO)) return;

        appendStringToHistory(showingValue.toString());
        doPreviousOperation();
        previousValue = BigDecimal.ZERO;
        lastOperator = '=';
        history.append(" = ");
        appendStringToHistory(showingValue.toString());
    }

    private void appendStringToHistory(String val) {
        if ('.' != DECIMAL_SEPARATOR_SYMBOL) {
            int decimalSeparatorPosition = val.indexOf(DECIMAL_SEPARATOR_SYMBOL);
            if (-1 != decimalSeparatorPosition)
                val = val.replace(DECIMAL_SEPARATOR_SYMBOL, '.');
        }
        appendDoubleToHistory(Double.parseDouble(val));
    }

    private void appendDoubleToHistory(double val) {
        if (val == (long) val)
            history.append(Long.valueOf((long) val).toString());
        else {
            StringBuilder tmp = new StringBuilder(Double.valueOf(val).toString());
            if ('.' != DECIMAL_SEPARATOR_SYMBOL) {
                int decimalSeparatorPosition = tmp.indexOf(".");
                if (-1 != decimalSeparatorPosition)
                    tmp.setCharAt(decimalSeparatorPosition, DECIMAL_SEPARATOR_SYMBOL);
            }
            history.append(tmp);
        }
    }

    private void operationButtonClicked(char operation) {
        if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0))) return;

        if (waitingNewOperand) {
            setFirstOperand();
            lastOperator = operation;
            if (history.length() > 3) {
                char tmp = history.charAt(history.length() - 2);
                if ((('+' == tmp) || ('-' == tmp) || ('*' == tmp) || ('/' == tmp)) &&
                        (' ' == history.charAt(history.length() - 1)))
                    history.setCharAt(history.length() - 2, operation);
                else {
                    String appendString = " " + operation + " ";
                    history.append(appendString);
                }
            }
            return;
        }

        if ('=' != lastOperator) {
            appendStringToHistory(showingValue.toString());
            history.append(" = ");
            doPreviousOperation();
            setFirstOperand();
        } else {
            setFirstOperand();
            setShowingValueAtZero();
        }
        appendStringToHistory(previousValue.toString());
        String tmp = " " + operation + " ";
        history.append(tmp);
        lastOperator = operation;
    }

    public void addition() {
        operationButtonClicked('+');
    }

    public void subtraction() {
        operationButtonClicked('-');
    }

    public void division() {
        operationButtonClicked('/');
    }

    public void multiplication() {
        operationButtonClicked('*');
    }
}
