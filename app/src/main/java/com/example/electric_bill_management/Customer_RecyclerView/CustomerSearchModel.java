package com.example.electric_bill_management.Customer_RecyclerView;

public class CustomerSearchModel {
    String id, name, month, address, amount, userType, price;

    public CustomerSearchModel(String id, String name, String month, String address, String amount, String userType, String price) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.address = address;
        this.amount = amount;
        this.userType = userType;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMonth() {
        return month;
    }

    public String getAddress() { return address; }

    public String getAmount() { return amount; }

    public String getUserType() { return userType; }

    public String getPrice() { return price; }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}