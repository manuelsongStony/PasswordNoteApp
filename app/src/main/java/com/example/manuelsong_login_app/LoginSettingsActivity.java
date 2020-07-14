package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_settings);
    
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginSettingsActivity.this, LoginListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initMapButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginSettingsActivity.this, LoginPasswordChecker.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setEnabled(false);
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MyLoginListPreferences",
                Context.MODE_PRIVATE).getString("sortfield","website");
        String sortOrder = getSharedPreferences("MyLoginListPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbWebsite = findViewById(R.id.website);
        RadioButton rbID = findViewById(R.id.radioIdendification);
        RadioButton rbFrequency = findViewById(R.id.radioFrequency);
        if (sortBy.equalsIgnoreCase("website")) {
            rbWebsite.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("identification")) {
            rbID.setChecked(true);
        }
        else {
            rbFrequency.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = findViewById(R.id.website);
                RadioButton rbCity = findViewById(R.id.radioIdendification);
                if (rbName.isChecked()) {
                    getSharedPreferences("MyLoginListPreferences",
                             Context.MODE_PRIVATE).edit().putString("sortfield", "website").apply();
                }
                else if (rbCity.isChecked()) {
                    getSharedPreferences("MyLoginListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "identification").apply();
                }
                else {
                    getSharedPreferences("MyLoginListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "frequency").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("MyLoginListPreferences",
                             Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    getSharedPreferences("MyLoginListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }

}
