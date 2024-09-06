package com.example.licenseactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "HotSpitalDataBase.db";
    private static final int DATABASEVERSION = 1;
    private static final String TABLENAME = "HotSpitalReseved";
    private static final String COLUMNID = "ID";
    private static final String COLUMNNAME = "NameHotSpital";
    private static final String COLUMNDEPARTMENT = "Department";
    private static final String COLUMNDOCTORS = "NameDoctors";
    public MyDataBase(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE_TABLE" + TABLENAME + "(" + COLUMNID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +  COLUMNNAME + "TEXT," + COLUMNDEPARTMENT + "TEXT," + COLUMNDOCTORS + "TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }
}
