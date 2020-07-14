package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {//implements PasswordShowDialog.SaveDateListener {

    private Login currentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        setForEditing(false);
        initTextChangedEvents();
        initSaveButton();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initLogin(extras.getInt("loginId"));
        }
        else {
            currentLogin = new Login();
        }
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initMapButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginPasswordChecker.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void setForEditing(boolean enabled) {
        EditText websiteEdit = findViewById(R.id.websiteEdit);
        EditText idEdit = findViewById(R.id.identificationEdit);
        EditText editPassword = findViewById(R.id.editPassword);
        EditText editImportance = findViewById(R.id.editImportance);
        Button buttonSave = findViewById(R.id.buttonSave);

        websiteEdit.setEnabled(enabled);
        idEdit.setEnabled(enabled);
        editPassword.setEnabled(enabled);
        editImportance.setEnabled(enabled);

        buttonSave.setEnabled(enabled);

        if (enabled) {
            websiteEdit.requestFocus();
        }
        else {
            ScrollView s = findViewById(R.id.scrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
        }

    }
/*
    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.buttonBirthday);
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                PasswordShowDialog datePickerDialog = new PasswordShowDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }

 */

    private void initTextChangedEvents(){
        final EditText etWebsite = findViewById(R.id.websiteEdit);
        etWebsite.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentLogin.setWebsiteName(etWebsite.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etIdentification = findViewById(R.id.identificationEdit);
        etIdentification.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentLogin.setIdentification(etIdentification.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etPassword = findViewById(R.id.editPassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                currentLogin.setPassword(etPassword.getText().toString());
            }
        });

        final EditText etImportance = findViewById(R.id.editImportance);
        etImportance.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                currentLogin.setImportance(etImportance.getText().toString());
            }
        });




    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                hideKeyboard();
                LoginDataSource ds = new LoginDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentLogin.getLoginID() == -1) {
                        wasSuccessful = ds.insertLogin(currentLogin);
                        if (wasSuccessful) {
                            int newId = ds.getLastLoginId();
                            currentLogin.setLoginID(newId);
                        }

                    }
                    else {
                        wasSuccessful = ds.updateLogin(currentLogin);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }

                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editWebsite = findViewById(R.id.websiteEdit);
        imm.hideSoftInputFromWindow(editWebsite.getWindowToken(), 0);
        EditText editIdentification = findViewById(R.id.identificationEdit);
        imm.hideSoftInputFromWindow(editIdentification.getWindowToken(), 0);
        EditText et = findViewById(R.id.editPassword);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et = findViewById(R.id.editImportance);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

    }

    private void initLogin(int id) {

        LoginDataSource ds = new LoginDataSource(MainActivity.this);
        try {
            ds.open();
            currentLogin = ds.getSpecificLogin(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Login Failed", Toast.LENGTH_LONG).show();
        }

        EditText editWebsite = findViewById(R.id.websiteEdit);
        EditText editIdentification = findViewById(R.id.identificationEdit);
        EditText editPassword = findViewById(R.id.editPassword);
        EditText editImportance = findViewById(R.id.editImportance);

        editWebsite.setText(currentLogin.getWebsiteName());
        editIdentification.setText(currentLogin.getIdentification());
        editPassword.setText(currentLogin.getPassword());
        editImportance.setText(currentLogin.getImportance());

    }

/*
    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView birthDay = findViewById(R.id.textBirthday);
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentLogin.setBirthday(selectedTime);
    }

 */
}
