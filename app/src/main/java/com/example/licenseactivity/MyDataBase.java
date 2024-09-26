package com.example.licenseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "HotSpitalDataBase.db";
    private static final int DATABASEVERSION = 3;  // Modificat pentru a forța actualizarea bazei de date
    private static final String TABLENAME = "HotSpitalReserved";
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
        String query = "CREATE TABLE " + TABLENAME + " (" +
                COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNNAME + " TEXT, " +
                COLUMNDEPARTMENT + " TEXT, " +
                COLUMNDOCTORS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // În caz de upgrade al bazei de date, recreează tabelul
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    // Adaugă date în tabel
    void addMedical(String spital, String departament, String doctor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNNAME, spital);
        cv.put(COLUMNDEPARTMENT, departament);
        cv.put(COLUMNDOCTORS, doctor);

        long result = db.insert(TABLENAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Șterge o intrare din tabel
    void deleteMedical(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLENAME, COLUMNID + "=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Returnează toate ID-urile din baza de date
    public ArrayList<Integer> getAllIDs() {
        ArrayList<Integer> idList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMNID + " FROM " + TABLENAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                idList.add(id);
            } while (cursor.moveToNext());
        } else {
            Log.d("MyDataBase", "Cursor is null or empty in getAllIDs");
        }

        if (cursor != null) {
            cursor.close();
        }

        return idList;
    }

    // Citește toate datele din tabel
    Cursor readData() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT " + COLUMNID + " FROM " + TABLENAME, null);
            if (cursor != null && cursor.getCount() > 0) {
                Log.d("MyDataBase", "Data found in table");
                return cursor;  // Returnează cursorul valid
            } else {
                Log.d("MyDataBase", "No data found in table");
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("MyDataBase", "Database is null");
        }
        return null;  // Returnează null dacă nu s-au găsit date
    }
}

