package ru.geekbrains.android1_calculator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.text.DecimalFormatSymbols;

import ru.geekbrains.android1_calculator.domain.Theme;
import ru.geekbrains.android1_calculator.storage.ThemeStorage;
import ru.geekbrains.android1_calculator.сalculator.CalculatorOperation;
import ru.geekbrains.android1_calculator.сalculator.CalculatorSymbol;

public class MainActivity extends AppCompatActivity {

    private static final String CALCULATOR_PRESENTER_IN_BUNDLE = "CALCULATOR_PRESENTER_IN_BUNDLE";

    private CalculatorPresenter presenter;
    private ThemeStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = new ThemeStorage(this);
        setTheme(storage.getTheme().getStyle());

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            presenter = new CalculatorPresenter();
        } else {
            presenter = (CalculatorPresenter) savedInstanceState.getSerializable("CALCULATOR_PRESENTER_IN_BUNDLE");
        }

        char decimalSeparatorChar = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        Button decimalSeparatorButton = findViewById(R.id.button_operation_decimal_separator);
        decimalSeparatorButton.setText(String.valueOf(decimalSeparatorChar));

        presenter.setOutputTextViews(findViewById(R.id.textview_result), findViewById(R.id.textview_history));

        setOnClickListeners();
    }

    private void changeTheme() {
        if (storage.getTheme().equals(Theme.MAIN)) {
            storage.saveTheme(Theme.SAND);
        } else {
            storage.saveTheme(Theme.MAIN);
        }
        recreate();
    }

    private void setOnClickListeners() {
        findViewById(R.id.change_theme_button).setOnClickListener(view -> changeTheme());

        findViewById(R.id.button_operation_reset)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.RESET));
        findViewById(R.id.button_operation_result)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.RESULT));
        findViewById(R.id.button_operation_addition)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.ADD));
        findViewById(R.id.button_operation_subtraction)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.SUB));
        findViewById(R.id.button_operation_division)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.DIV));
        findViewById(R.id.button_operation_multiplication)
                .setOnClickListener(view ->
                        presenter.operationButtonClicked(CalculatorOperation.MUL));

        findViewById(R.id.button_operation_decimal_separator)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.DOT));
        findViewById(R.id.button_operation_backspace)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.BACKSPACE));
        findViewById(R.id.button_number_zero)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.ZERO));
        findViewById(R.id.button_number_one)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.ONE));
        findViewById(R.id.button_number_two)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.TWO));
        findViewById(R.id.button_number_three)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.THREE));
        findViewById(R.id.button_number_four)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.FOUR));
        findViewById(R.id.button_number_five)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.FIVE));
        findViewById(R.id.button_number_six)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.SIX));
        findViewById(R.id.button_number_seven)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.SEVEN));
        findViewById(R.id.button_number_eight)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.EIGHT));
        findViewById(R.id.button_number_nine)
                .setOnClickListener(view ->
                        presenter.symbolButtonClicked(CalculatorSymbol.NINE));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(CALCULATOR_PRESENTER_IN_BUNDLE, presenter);
    }
}