package com.example.manuelsong_login_app;
//Manuel Song 110116811
//I used all the template, frame , and methods from textbook
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mylogins.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_TABLE_LOGIN =
         "create table login (_id integer primary key autoincrement, "
                 + "website text not null, identification text, "
                 + "password text, importance text, frequency text);";

    public LoginDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LoginDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }

}
