package com.example.electric_bill_management;

public class Customer {
    private int id;
    private String name;
    private int yyyymm;
    private String address;
    private double usedNumElectric;
    private int elecUserTypeId;

    public Customer() {
    }

    // Constructor
    public Customer(int id, String name, int yyyymm, String address, int usedNumElectric, int elecUserTypeId) {
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

    public int getYyyymm() {
        return yyyymm;
    }

    public void setYyyymm(int yyyymm) {
        this.yyyymm = yyyymm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getUsedNumElectric() {
        return usedNumElectric;
    }

    public void setUsedNumElectric(double usedNumElectric) {
        this.usedNumElectric = usedNumElectric;
    }

    public int getElecUserTypeId() {
        return elecUserTypeId;
    }

    public void setElecUserTypeId(int elecUserTypeId) {
        this.elecUserTypeId = elecUserTypeId;
    }
}
