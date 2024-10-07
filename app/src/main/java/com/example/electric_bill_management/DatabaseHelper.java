package com.example.electric_bill_management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ElectricBillManagement.db";

    // Table names
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_ELECTRIC_USER_TYPE = "electric_user_type";

    // Columns for electric_user_type table
    private static final String COLUMN_USER_TYPE_ID = "ID";
    private static final String COLUMN_USER_TYPE_NAME = "ELEC_USER_TYPE_NAME";
    private static final String COLUMN_UNIT_PRICE = "UNIT_PRICE";

    // Columns for customer table
    private static final String COLUMN_CUSTOMER_ID = "ID";
    private static final String COLUMN_CUSTOMER_NAME = "NAME";
    private static final String COLUMN_YYYYMM = "YYYYMM";
    private static final String COLUMN_ADDRESS = "ADDRESS";
    private static final String COLUMN_USED_NUM_ELECTRIC = "USED_NUM_ELECTRIC";
    private static final String COLUMN_CUSTOMER_USER_TYPE_ID = "ELEC_USER_TYPE_ID";

    // Create table statements
    private static final String CREATE_TABLE_ELECTRIC_USER_TYPE = "CREATE TABLE "
            + TABLE_ELECTRIC_USER_TYPE + "("
            + COLUMN_USER_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_TYPE_NAME + " TEXT, "
            + COLUMN_UNIT_PRICE + " REAL" + ")";

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE "
            + TABLE_CUSTOMER + "("
            + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUSTOMER_NAME + " TEXT, "
            + COLUMN_YYYYMM + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_USED_NUM_ELECTRIC + " INTEGER, "
            + COLUMN_CUSTOMER_USER_TYPE_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_CUSTOMER_USER_TYPE_ID + ") REFERENCES "
            + TABLE_ELECTRIC_USER_TYPE + "(" + COLUMN_USER_TYPE_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_ELECTRIC_USER_TYPE);
        db.execSQL(CREATE_TABLE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELECTRIC_USER_TYPE);
        // Create new tables
        onCreate(db);
    }
}
