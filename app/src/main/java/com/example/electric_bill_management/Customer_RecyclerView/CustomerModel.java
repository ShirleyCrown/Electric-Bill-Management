package com.example.electric_bill_management.Customer_RecyclerView;

// CustomerModel su dung cho CustomerList
public class CustomerModel {
    String customerName, customerAddress;
    int image;

    public CustomerModel(String customerName, String customerAddress, int image) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.image = image;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public int getImage() {
        return image;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
