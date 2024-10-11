package com.example.electric_bill_management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper {
    private Context context;
    private static final String DATABASE_NAME = "database.db";
    private static final String DATABASE_PATH = "/data/data/your.package.name/databases/";

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public void createDatabase() {
        try {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            if (!dbFile.exists()) {
                copyDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyDatabase() {
        try {
            InputStream input = context.getAssets().open(DATABASE_NAME);
            String outputFileName = DATABASE_PATH + DATABASE_NAME;
            File directory = new File(DATABASE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            OutputStream output = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase openDatabase() throws SQLiteException {
        return SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
