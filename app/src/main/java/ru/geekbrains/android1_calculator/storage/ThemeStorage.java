package ru.geekbrains.android1_calculator.storage;

import android.content.Context;
import android.content.SharedPreferences;

import ru.geekbrains.android1_calculator.domain.Theme;

public class ThemeStorage {

    private final SharedPreferences preferences;
    private Theme selectedTheme;

    public ThemeStorage(Context context) {
        preferences = context.getSharedPreferences("themes", Context.MODE_PRIVATE);
    }

    public Theme getTheme() {
        if (selectedTheme != null) {
            return selectedTheme;
        }

        String savedTheme = preferences.getString("theme", Theme.MAIN.getKey());

        selectedTheme = Theme.MAIN;

        for (Theme theme : Theme.values()) {
            if (theme.getKey().equals(savedTheme)) {
                selectedTheme = theme;
                break;
            }
        }

        return selectedTheme;
    }

    public void saveTheme(Theme theme) {
        preferences.edit().putString("theme", theme.getKey()).apply();
    }
}
