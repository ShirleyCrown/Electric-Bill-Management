package com.example.electric_bill_management;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        db.close();
        return price;
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = openDatabase();

        String query = "INSERT INTO CUSTOMER (NAME, YYYYMM, ADDRESS, USED_NUM_ELECTRIC, ELEC_USER_TYPE_ID) " +
                "VALUES (?, ?, ?, ?, ?)";

        db.execSQL(query, new Object[]{
                customer.getName(),
                customer.getYyyymm(),
                customer.getAddress(),
                customer.getUsedNumElectric(),
                customer.getElecUserTypeId()
        });

        db.close();
    }

    public List<String> getAllCustomerNames() {
        List<String> customerNames = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT NAME FROM CUSTOMER", null);
        if (cursor.moveToFirst()) {
            do {
                customerNames.add(cursor.getString(0));  // Lấy tên của khách hàng từ cột đầu tiên
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerNames;
    }

    public List<String> getAllCustomerAddresses() {
        List<String> customerAddresses = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery("SELECT ADDRESS FROM CUSTOMER", null);
        if (cursor.moveToFirst()) {
            do {
                customerAddresses.add(cursor.getString(0));  // Lấy địa chỉ của khách hàng từ cột đầu tiên
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerAddresses;
    }


    public void increaseElectricUnitPrice(int userTypeId, int increaseAmount) {
        SQLiteDatabase db = openDatabase();

        // Update unit price in the database
        String updateQuery = "UPDATE ELECTRIC_USER_TYPE SET UNIT_PRICE = UNIT_PRICE + ? WHERE ID = ?";
        db.execSQL(updateQuery, new Object[]{increaseAmount, userTypeId});

        // Get the updated user type name for the notification
        String userTypeName = "";
        int newUnitPrice = 0;
        Cursor cursor = db.rawQuery("SELECT ELEC_USER_TYPE_NAME, UNIT_PRICE FROM ELECTRIC_USER_TYPE WHERE ID = ?", new String[]{String.valueOf(userTypeId)});
        if (cursor.moveToFirst()) {
            userTypeName = cursor.getString(0);
            newUnitPrice = cursor.getInt(1);
        }
        cursor.close();
        db.close();

        // Create the notification
        createNotification(userTypeName, increaseAmount);
    }

    private void createNotification(String userTypeName, int increaseAmount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "electric_price_update_channel";
        String channelName = "Electric Price Updates";

        // Create the notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Format the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(new Date());

        // Build the notification content
        String notificationContent = "Already increased electric unit price for user type " + userTypeName +
                " with amount " + increaseAmount + " at " + currentTime;

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Electric Unit Price Increased")
                .setContentText(notificationContent)
                .setAutoCancel(true);

        // Set the notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        // Send the notification
        notificationManager.notify(1, builder.build());
    }

}
