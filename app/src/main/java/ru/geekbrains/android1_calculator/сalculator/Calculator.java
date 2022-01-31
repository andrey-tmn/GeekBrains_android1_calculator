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

    public void addSymbol(CalculatorSymbol symbol) {
        if (symbol.equals(CalculatorSymbol.BACKSPACE)) {
            if (waitingNewOperand) {
                reset();
                return;
            }

            if (showingValue.length() == 1) {
                if (showingValue.charAt(0) != '0') {
                    showingValue.setCharAt(0, '0');
                }
            } else if (showingValue.length() > 1) {
                showingValue.setLength(showingValue.length() - 1);
            }

            return;
        }

        if ((symbol.equals(CalculatorSymbol.DOT)) &&
                (showingValue.indexOf(String.valueOf(DOT)) != NOT_FOUND)) {
            return;
        }

        if (waitingNewOperand) {
            if (symbol.equals(CalculatorSymbol.DOT)) {
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

        if ((!symbol.equals(CalculatorSymbol.ZERO)) && (!symbol.equals(CalculatorSymbol.DOT))) {

            if ((showingValue.length() == 1) && (showingValue.charAt(0) == '0')) {
                showingValue.setLength(0);
            }

            showingValue.append(symbol);

        } else {

            if ((showingValue.length() > 1) || (showingValue.charAt(0) != '0') ||
                    (symbol.equals(CalculatorSymbol.DOT))) {
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
                mathOperationPrepare(operation);
        }
    }

    private void mathOperationPrepare(CalculatorOperation operation) {
        if ((showingValue.length() == 1) && (showingValue.charAt(0)) == '0') {
            return;
        }

        if (waitingNewOperand) {
            setFirstOperand();
            lastOperator = operation;
            if (history.length() > 3) {
                char tmp = history.charAt(history.length() - 2);
                if (((tmp == '+') || (tmp == '-') || (tmp == '*') || (tmp == '/')) &&
                        (history.charAt(history.length() - 1) == ' ')) {
                    history.setCharAt(history.length() - 2, operation.getSymbol());
                } else {
                    String appendString = " " + operation + " ";
                    history.append(appendString);
                }
            }
            return;
        }

        if (!lastOperator.equals(CalculatorOperation.RESULT)) {
            history.append(showingValue);
            history.append(" = ");
            doMathOperation();
            setFirstOperand();
        } else {
            setFirstOperand();
            setShowingValueAtZero();
        }

        history.append(previousValue);
        String tmp = " " + operation + " ";
        history.append(tmp);
        lastOperator = operation;
    }

    private void reset() {
        setShowingValueAtZero();
        previousValue = BigDecimal.ZERO;
        lastOperator = CalculatorOperation.RESULT;
        waitingNewOperand = false;
        history.setLength(0);
    }

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
        if ((showingValue.length() == 1) && (showingValue.charAt(0) == '0')) {
            return;
        }
        if (lastOperator.equals(CalculatorOperation.RESULT)) {
            return;
        }
        if (previousValue.equals(BigDecimal.ZERO)) {
            return;
        }

        appendToHistory(showingValue.toString());
        doMathOperation();
        previousValue = BigDecimal.ZERO;
        lastOperator = CalculatorOperation.RESULT;
        history.append(" = ");
        appendToHistory(showingValue.toString());
    }

    private void appendToHistory(String doubleValInString) {
        double val = Double.parseDouble(doubleValInString);
        if (val == (long) val) {
            history.append(Long.valueOf((long) val).toString());
        } else {
            history.append(Double.valueOf(val).toString());
        }
    }
}
