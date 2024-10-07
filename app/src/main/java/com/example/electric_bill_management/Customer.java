package com.example.electric_bill_management;

public class Customer {
    private int id;
    private String name;
    private String yyyymm;
    private String address;
    private int usedNumElectric;
    private int elecUserTypeId;

    // Constructor
    public Customer(int id, String name, String yyyymm, String address, int usedNumElectric, int elecUserTypeId) {
        this.id = id;
        this.name = name;
        this.yyyymm = yyyymm;
        this.address = address;
        this.usedNumElectric = usedNumElectric;
        this.elecUserTypeId = elecUserTypeId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(String yyyymm) {
        this.yyyymm = yyyymm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUsedNumElectric() {
        return usedNumElectric;
    }

    public void setUsedNumElectric(int usedNumElectric) {
        this.usedNumElectric = usedNumElectric;
    }

    public int getElecUserTypeId() {
        return elecUserTypeId;
    }

    public void setElecUserTypeId(int elecUserTypeId) {
        this.elecUserTypeId = elecUserTypeId;
    }
}
