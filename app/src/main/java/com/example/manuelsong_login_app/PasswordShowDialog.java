package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class PasswordShowDialog extends DialogFragment {
    RecyclerView loginList;
    LoginAdapter loginAdapter;
    Login currentLogin;
    ArrayList<Login> logins;
    LoginListActivity activity;
    View activityView;
    private static final String TAG = "PasswordShowDialog";
    //private EditText mInput;
    private TextView mActionChange, mActionCancel;
    private TextView websiteText, idText, passwordText;


    public PasswordShowDialog(LoginListActivity act,View view,ArrayList<Login> loginsFrom){
        super();
        activity=act;
        activityView=view;
        logins=loginsFrom;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_password_show_up, container, false);
        mActionChange = view.findViewById(R.id.action_change);
        mActionCancel=view.findViewById(R.id.action_cancel);

        websiteText = view.findViewById(R.id.websiteText2);
        idText = view.findViewById(R.id.idText2);
        passwordText = view.findViewById(R.id.passwordText2);



        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) activityView.getTag();
        int position = viewHolder.getAdapterPosition();

        websiteText.setText(logins.get(position).getWebsiteName());
        idText.setText(logins.get(position).getIdentification());
        passwordText.setText(logins.get(position).getPassword());
        //logins.get(position).setFrequency(Integer.toString((parseInt(logins.get(position).getFrequency())+1)));
        //Frequency++
        currentLogin=logins.get(position);
        currentLogin.setFrequency(Integer.toString((parseInt(currentLogin.getFrequency())+1)));

        LoginDataSource ds = new LoginDataSource(activity);
        try {
            ds.open();
            ds.updateLogin(currentLogin);
            ds.close();
        }
        catch (Exception e) {

        }


        mActionCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });


        mActionChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");

                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) activityView.getTag();
                int position = viewHolder.getAdapterPosition();
                int loginId = logins.get(position).getLoginID();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("loginId", loginId);
                startActivity(intent);
                getDialog().dismiss();
            }
        });
        return view;
    }

}
