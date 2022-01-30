package ru.geekbrains.android1_calculator.сalculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator implements Serializable {

    private static final char DOT = '.';
    private static final int DECIMAL_SCALE_SYMBOLS = 6;
    private static final int NOT_FOUND = -1;

    private final StringBuilder showingValue;
    private final StringBuilder history;
    private BigDecimal previousValue;
    private CalculatorOperation lastOperator = CalculatorOperation.RESULT;
    private boolean waitingNewOperand = false;

    public Calculator() {
        showingValue = new StringBuilder("0");
        history = new StringBuilder();
        previousValue = BigDecimal.ZERO;
    }

    public String getHistory() {
        return history.toString();
    }

    public String getValue() {
        return showingValue.toString();
    }

    public void addSymbol(char symbol) {
        if ((symbol == DOT) &&
                (showingValue.indexOf(String.valueOf(DOT)) != NOT_FOUND)) {
            return;
        }

        if (waitingNewOperand) {
            if (DOT == symbol) {
                return;
            }

            if (lastOperator.equals(CalculatorOperation.RESULT)) {
                reset();
            }

            showingValue.setLength(0);
            showingValue.append(symbol);
            waitingNewOperand = false;
            return;
        }

        if (('0' != symbol) && (DOT != symbol)) {

            if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0))) {
                showingValue.setLength(0);
            }

            showingValue.append(symbol);

        } else {

            if ((showingValue.length() > 1) || ('0' != showingValue.charAt(0)) ||
                    (symbol == DOT)) {
                showingValue.append(symbol);
            }

        }
    }

    public void doOperation(CalculatorOperation operation) {
        switch (operation) {
            case RESET:
                reset();
                break;
            case RESULT:
                result();
                break;
            default:
                doMathOperation();
        }
    }

/*
    private void changeDecimalSeparator(char oldSeparator, char newSeparator) {
        int decimalSeparatorPosition = showingValue.indexOf(String.valueOf(oldSeparator));
        if (decimalSeparatorPosition != NOT_FOUND)
            showingValue.setCharAt(decimalSeparatorPosition, newSeparator);
    }

    public void setDecimalSeparator(char newDecimalSeparatorChar) {
        if (newDecimalSeparatorChar != DECIMAL_SEPARATOR_SYMBOL) {
            changeDecimalSeparator(DECIMAL_SEPARATOR_SYMBOL, newDecimalSeparatorChar);

            int decimalSeparatorPosition = history.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
            while (decimalSeparatorPosition != NOT_FOUND) {
                history.setCharAt(decimalSeparatorPosition, newDecimalSeparatorChar);
                decimalSeparatorPosition = history.indexOf(String.valueOf(DECIMAL_SEPARATOR_SYMBOL));
            }

            DECIMAL_SEPARATOR_SYMBOL = newDecimalSeparatorChar;
        }
    }
*/

    private void reset() {
        setShowingValueAtZero();
        previousValue = BigDecimal.ZERO;
        lastOperator = CalculatorOperation.RESULT;
        waitingNewOperand = false;
        history.setLength(0);
    }

    /*
        private void backspace() {
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
    */
    private void setShowingValueAtZero() {
        showingValue.setLength(0);
        showingValue.append('0');
    }

    private void setFirstOperand() {
        previousValue = new BigDecimal(showingValue.toString());
    }

    private void doMathOperation() {
        BigDecimal currentValueBigDecimal = new BigDecimal(showingValue.toString());
        BigDecimal resultBigDecimal = BigDecimal.ZERO;
        switch (lastOperator) {
            case ADD:
                resultBigDecimal = previousValue.add(currentValueBigDecimal);
                break;
            case SUB:
                resultBigDecimal = previousValue.subtract(currentValueBigDecimal);
                break;
            case MUL:
                resultBigDecimal = previousValue.multiply(currentValueBigDecimal);
                break;
            case DIV:
                resultBigDecimal =
                        previousValue.divide(currentValueBigDecimal,
                                DECIMAL_SCALE_SYMBOLS, RoundingMode.HALF_UP);
                break;
        }

        showingValue.setLength(0);
        double val = resultBigDecimal.doubleValue();
        if (val == (long) val)
            showingValue.append(Long.valueOf((long) val).toString());
        else
            showingValue.append(Double.valueOf(val).toString());
        waitingNewOperand = true;
    }

    private void result() {
        if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0))) {
            return;
        }
        if (lastOperator.equals(CalculatorOperation.RESULT)) {
            return;
        }
        if (previousValue.equals(BigDecimal.ZERO)) {
            return;
        }

        appendDoubleToHistory(Double.parseDouble(showingValue.toString()));
        doMathOperation();
        previousValue = BigDecimal.ZERO;
        lastOperator = CalculatorOperation.RESULT;
        history.append(" = ");
        appendDoubleToHistory(Double.parseDouble(showingValue.toString()));
    }

    private void appendDoubleToHistory(double val) {
        if (val == (long) val) {
            history.append(Long.valueOf((long) val).toString());
        } else {
            history.append(Double.valueOf(val).toString());
        }
    }

    private void operationButtonClicked(CalculatorOperation operation) {
        if ((1 == showingValue.length()) && ('0' == showingValue.charAt(0))) {
            return;
        }

        if (waitingNewOperand) {
            setFirstOperand();
            lastOperator = operation;
            if (history.length() > 3) {
                char tmp = history.charAt(history.length() - 2);
                if ((('+' == tmp) || ('-' == tmp) || ('*' == tmp) || ('/' == tmp)) &&
                        (' ' == history.charAt(history.length() - 1)))
                    history.setCharAt(history.length() - 2, operation.getSymbol());
                else {
                    String appendString = " " + operation + " ";
                    history.append(appendString);
                }
            }
            return;
        }

        if (lastOperator.equals(CalculatorOperation.RESULT)) {
            history.append(showingValue.toString());
            history.append(" = ");
            doMathOperation();
            setFirstOperand();
        } else {
            setFirstOperand();
            setShowingValueAtZero();
        }
        history.append(previousValue.toString());
        String tmp = " " + operation + " ";
        history.append(tmp);
        lastOperator = operation;
    }
}
