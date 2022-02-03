package ru.geekbrains.android1_calculator.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import ru.geekbrains.android1_calculator.R;
import ru.geekbrains.android1_calculator.domain.Theme;

public class ThemeSelectionActivity extends AppCompatActivity {

    private static final String ARG_THEME = "ARG_THEME";
    public static final String THEME_RESULT = "THEME_RESULT";

    public static Intent intent(Context context, Theme theme) {
        Intent intent = new Intent(context, ThemeSelectionActivity.class);
        intent.putExtra(ARG_THEME, theme);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selection);

        Theme selectedTheme = (Theme) getIntent().getSerializableExtra(ARG_THEME);

        if (selectedTheme.equals(Theme.MAIN)) {
            showSelectedTheme(findViewById(R.id.main_theme_select_title),
                    getString(R.string.main_theme_name_in_list));

            findViewById(R.id.main_theme_select).setOnClickListener(view -> finish());
            findViewById(R.id.sand_theme_select)
                    .setOnClickListener(view -> selectTheme(Theme.SAND));
        } else {
            showSelectedTheme(findViewById(R.id.sand_theme_select_title),
                    getString(R.string.sand_theme_name_in_list));

            findViewById(R.id.main_theme_select)
                    .setOnClickListener(view -> selectTheme(Theme.MAIN));
            findViewById(R.id.sand_theme_select).setOnClickListener(view -> finish());
        }
    }

    private void showSelectedTheme(TextView themeTitleTextView, String themeTitle) {
        String title = themeTitle + " " + getString(R.string.theme_selected);
        themeTitleTextView.setText(title);
        themeTitleTextView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void selectTheme(Theme theme) {
        Intent data = new Intent();
        data.putExtra(THEME_RESULT, theme);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}