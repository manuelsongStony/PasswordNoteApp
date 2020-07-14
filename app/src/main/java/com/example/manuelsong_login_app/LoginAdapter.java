package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoginAdapter extends RecyclerView.Adapter{
    private ArrayList<Login> loginData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public class LoginViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewWebsite;
        public TextView textIdentification;
        public Button deleteButton;
        public LoginViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWebsite = itemView.findViewById(R.id.textWebsiteName);
            textIdentification = itemView.findViewById(R.id.textIdentification);
            deleteButton = itemView.findViewById(R.id.buttonDeleteLogin);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getWebsiteTextView() {
            return textViewWebsite;
        }
        public TextView getIdentificationTextView() {
            return textIdentification;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public LoginAdapter(ArrayList<Login> arrayList, Context context) {
        loginData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new LoginViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        LoginViewHolder lvh = (LoginViewHolder) holder;
        lvh.getWebsiteTextView().setText(loginData.get(position).getWebsiteName());
        lvh.getIdentificationTextView().setText(loginData.get(position).getIdentification());
        if (isDeleting) {
            lvh.getDeleteButton().setVisibility(View.VISIBLE);
            lvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else {
            lvh.getDeleteButton().setVisibility(View.INVISIBLE);        }

    }

    @Override
    public int getItemCount() {

        return loginData.size();
    }

    private void deleteItem(int position) {
        Login login = loginData.get(position);
        LoginDataSource dataSource = new LoginDataSource(parentContext);
        try {
            dataSource.open();
            boolean didDelete = dataSource.deleteLogin(login.getLoginID());
            dataSource.close();
            if (didDelete) {
                loginData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {

        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }
}
