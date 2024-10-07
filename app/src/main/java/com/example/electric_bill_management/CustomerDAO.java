package com.example.electric_bill_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    // Constructor
    public CustomerDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open database connection
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close database connection
    public void close() {
        dbHelper.close();
    }

    // Insert a new customer record
    public long insertCustomer(String name, String yyyymm, String address, int usedNumElectric, int elecUserTypeId) {
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("YYYYMM", yyyymm);
        values.put("ADDRESS", address);
        values.put("USED_NUM_ELECTRIC", usedNumElectric);
        values.put("ELEC_USER_TYPE_ID", elecUserTypeId);

        return database.insert("customer", null, values);
    }

    // Retrieve a customer by ID
    public Customer getCustomerById(int id) {
        Cursor cursor = database.query("customer", null, "ID = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Customer customer = new Customer(
                    cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NAME")),
                    cursor.getString(cursor.getColumnIndexOrThrow("YYYYMM")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("USED_NUM_ELECTRIC")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("ELEC_USER_TYPE_ID"))
            );
            cursor.close();
            return customer;
        }
        return null;
    }

    // Update an existing customer record
    public int updateCustomer(int id, String name, String yyyymm, String address, int usedNumElectric, int elecUserTypeId) {
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("YYYYMM", yyyymm);
        values.put("ADDRESS", address);
        values.put("USED_NUM_ELECTRIC", usedNumElectric);
        values.put("ELEC_USER_TYPE_ID", elecUserTypeId);

        return database.update("customer", values, "ID = ?", new String[]{String.valueOf(id)});
    }

    // Delete a customer record
    public int deleteCustomer(int id) {
        return database.delete("customer", "ID = ?", new String[]{String.valueOf(id)});
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        Cursor cursor = database.query("customer", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Customer customer = new Customer(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NAME")),
                        cursor.getString(cursor.getColumnIndexOrThrow("YYYYMM")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("USED_NUM_ELECTRIC")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("ELEC_USER_TYPE_ID"))
                );
                customerList.add(customer);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return customerList;
    }
}
