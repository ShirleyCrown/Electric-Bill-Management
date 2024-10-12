package com.example.electric_bill_management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private Context context;
    private static final String DATABASE_NAME = "electric_bills.db";
    @SuppressLint("SdCardPath")
    private static final String DATABASE_PATH = "/data/data/com.example.electric_bill_management/databases/";

    public DatabaseHelper(Context context) {
        this.context = context;
    }

//    public DatabaseHelper(View.OnClickListener onClickListener) {
//    }

    public void createDatabase() {
        try {
            File directory = new File(DATABASE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();  // Tạo thư mục nếu nó không tồn tại
            }

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
            // Mở tệp cơ sở dữ liệu từ thư mục assets
            InputStream input = context.getAssets().open(DATABASE_NAME);

            String outputFileName = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();

            File directory = new File(outputFileName).getParentFile();
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

    public List<Customer> getCustomersByName(String name) {
        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMER WHERE NAME LIKE ?", new String[]{"%" + name + "%"});
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setYyyymm(cursor.getInt(2));
                customer.setAddress(cursor.getString(3));
                customer.setUsedNumElectric(cursor.getDouble(4));
                customer.setElecUserTypeId(cursor.getInt(5));

                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public List<Customer> getCustomersByAddress(String address) {
        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMER WHERE ADDRESS LIKE ?", new String[]{"%" +address + "%"});
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setYyyymm(cursor.getInt(2));
                customer.setAddress(cursor.getString(3));
                customer.setUsedNumElectric(cursor.getDouble(4));
                customer.setElecUserTypeId(cursor.getInt(5));

                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMER", null);
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setYyyymm(cursor.getInt(2));
                customer.setAddress(cursor.getString(3));
                customer.setUsedNumElectric(cursor.getDouble(4));
                customer.setElecUserTypeId(cursor.getInt(5));

                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public int getPriceByType(int type){
        int price = 0;
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT UNIT_PRICE FROM ELECTRIC_USER_TYPE WHERE ID = ?", new String[]{String.valueOf(type)});
        if (cursor != null && cursor.moveToFirst()) {
            price = cursor.getInt(0);
        }
        if (cursor != null) {
            cursor.close();
        }
        return price;
    }




//    public List<String> getJoinedCustomerData() {
//        List<String> joinedDataList = new ArrayList<>();
//        SQLiteDatabase db = openDatabase();
//        String query = "SELECT CUSTOMER.ID, CUSTOMER.NAME, CUSTOMER.YYYYMM, CUSTOMER.ADDRESS, " +
//                "CUSTOMER.USED_NUM_ELECTRIC, ELECTRIC_USER_TYPE.ELEC_USER_TYPE_NAME, ELECTRIC_USER_TYPE.UNIT_PRICE " +
//                "FROM CUSTOMER " +
//                "JOIN ELECTRIC_USER_TYPE ON CUSTOMER.ELEC_USER_TYPE_ID = ELECTRIC_USER_TYPE.ID";
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                joinedDataList.add("Customer ID: " + cursor.getInt(0) + ", Name: " + cursor.getString(1) +
//                        ", YYYYMM: " + cursor.getString(2) + ", Address: " + cursor.getString(3) +
//                        ", Used Electric: " + cursor.getInt(4) + ", User Type: " + cursor.getString(5) +
//                        ", Unit Price: " + cursor.getDouble(6));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return joinedDataList;
//    }
}
