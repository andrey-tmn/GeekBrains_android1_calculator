package ru.geekbrains.android1_calculator.domain;

import androidx.annotation.StyleRes;

import ru.geekbrains.android1_calculator.R;

public enum Theme {
    MAIN(R.style.Theme_CalculatorStandard, "main"),
    SAND(R.style.Theme_SandDorBeetle, "sand");

    @StyleRes
    private final int style;

    private final String key;

    Theme(int style, String key) {
        this.style = style;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public int getStyle() {
        return style;
    }
}
