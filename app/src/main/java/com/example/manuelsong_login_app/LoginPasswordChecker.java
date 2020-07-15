package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginPasswordChecker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        initListButton();
        initCheckerButton();
        initSettingsButton();
        initChecker();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginPasswordChecker.this, LoginListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initCheckerButton() {
        ImageButton ibList = findViewById(R.id.imageButtonChecker);
        ibList.setEnabled(false);
    }
    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginPasswordChecker.this, LoginSettingsActivity
                        .class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initChecker() {

        final EditText password=(EditText)findViewById(R.id.passwordSEdit);
        final TextView result= (TextView)findViewById(R.id.passwordSResult);
        Button btn= (Button) findViewById(R.id.btn_checker);

        //int enteredBill= Integer.parseInt(bill.getText().toString());
        //tip.setText("tip:"+enteredBill*0.18);


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s="";
                int score= securityTest(password.getText().toString());
                if(score>=30){
                    s="Good for security"+" (score : "+score+")";
                }else if(score>=20){
                    s="Normal for security"+" (score : "+score+")";
                }else{
                    s="Bad for security"+" (score : "+score+")";
                }
                result.setText(s);
            }
        });

    }
    public int securityTest(String pass){
        int score=0;
        int n = pass.length();
        Map mymap = new HashMap();

        mymap.size();

        boolean hasLower = false, hasUpper = false;
        boolean hasDigit = false, hasSpecial = false;

        for (int i = 0; i < n; i++) {
            if (Character.isLowerCase(pass.charAt(i))){
                hasLower = true;}
            else if (Character.isUpperCase(pass.charAt(i))){
                hasUpper = true;}
            else if (Character.isDigit(pass.charAt(i))){
                hasDigit = true;}
            else {
                hasSpecial = true;
            }

            mymap.put(pass.charAt(i),1);
        }
        if(pass.length()>=8)
            score=score+10;
        if(hasLower)
            score=score+5;
        if(hasUpper)
            score=score+5;
        if(hasDigit)
            score=score+5;
        if(hasSpecial)
            score=score+10;
        if(mymap.size()>=5)
            score=score+5;
        return score;

    }



}
