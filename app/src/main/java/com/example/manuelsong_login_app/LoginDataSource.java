package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class LoginDataSource {
    private SQLiteDatabase database;
    private LoginDBHelper dbHelper;

    public LoginDataSource(Context context) {
        dbHelper = new LoginDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertLogin(Login c) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("website", c.getWebsiteName());
            initialValues.put("identification", c.getIdentification());
            initialValues.put("password", c.getPassword());
            initialValues.put("importance", c.getImportance());
            initialValues.put("frequency", c.getFrequency());


            didSucceed = database.insert("login", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateLogin(Login login) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) login.getLoginID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("website", login.getWebsiteName());
            updateValues.put("identification", login.getIdentification());
            updateValues.put("password", login.getPassword());
            updateValues.put("importance", login.getImportance());
            updateValues.put("frequency", login.getFrequency());


            didSucceed = database.update("login", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastLoginId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from login";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<String> getWebsiteName() {
        ArrayList<String> websiteNames = new ArrayList<>();
        try {
            String query = "Select websiteNames from login";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                websiteNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            websiteNames = new ArrayList<String>();
        }
        return websiteNames;
    }

    public ArrayList<Login> getLogins(String sortField, String sortOrder) {
        ArrayList<Login> logins = new ArrayList<Login>();
        try {
            String query = "SELECT  * FROM login ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Login newLogin;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newLogin = new Login();
                newLogin.setLoginID(cursor.getInt(0));
                newLogin.setWebsiteName(cursor.getString(1));
                newLogin.setIdentification(cursor.getString(2));
                newLogin.setPassword(cursor.getString(3));
                newLogin.setImportance(cursor.getString(4));
                newLogin.setFrequency(cursor.getString(5));

                logins.add(newLogin);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            logins = new ArrayList<Login>();
        }
        return logins;
    }

    public Login getSpecificLogin(int loginId) {
        Login login = new Login();
        String query = "SELECT  * FROM login WHERE _id =" + loginId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            login.setLoginID(cursor.getInt(0));
            login.setWebsiteName(cursor.getString(1));
            login.setIdentification(cursor.getString(2));
            login.setPassword(cursor.getString(3));
            login.setImportance(cursor.getString(4));
            login.setFrequency(cursor.getString(5));


            cursor.close();
        }
        return login;
    }

    public boolean deleteLogin(int loginId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("login", "_id=" + loginId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

}
