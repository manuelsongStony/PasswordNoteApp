package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoginListActivity extends AppCompatActivity {
    RecyclerView loginList;
    LoginAdapter loginAdapter;

    ArrayList<Login> logins;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
//            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
//            int position = viewHolder.getAdapterPosition();
//            int loginId = logins.get(position).getLoginID();
//            Intent intent = new Intent(LoginListActivity.this, MainActivity.class);
//            intent.putExtra("loginId", loginId);
//            startActivity(intent);

            PasswordShowDialog dialog=new PasswordShowDialog(LoginListActivity.this,view,logins);
            dialog.show(getSupportFragmentManager(),"PasswordShowDialog");


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_list);

        initChekcerButton();
        initSettingsButton();

        initAddLoginButton();
        initDeleteSwitch();
    }

    private void initChekcerButton() {
        ImageButton ibList = findViewById(R.id.imageButtonChecker);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginListActivity.this, LoginPasswordChecker.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginListActivity.this, LoginSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        String sortBy = getSharedPreferences("MyLoginListPreferences", Context.MODE_PRIVATE).getString("sortfield", "website");
        String sortOrder = getSharedPreferences("MyLoginListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        LoginDataSource ds = new LoginDataSource(this);
        try {
            ds.open();
            logins = ds.getLogins(sortBy, sortOrder);
            ds.close();
            if (logins.size() > 0) {
                loginList = findViewById(R.id.rvLogins);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                loginList.setLayoutManager(layoutManager);

                loginAdapter = new LoginAdapter(logins, this);

                loginAdapter.setOnItemClickListener(onItemClickListener);
                loginList.setAdapter(loginAdapter);
            }
            else {
                Intent intent = new Intent(LoginListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving logins", Toast.LENGTH_LONG).show();
        }

    }
    private void initAddLoginButton() {
        Button newLogin = findViewById(R.id.buttonAddLogin);
        newLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                loginAdapter.setDelete(status);
                loginAdapter.notifyDataSetChanged();
            }
        });
    }
}
