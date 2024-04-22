package com.example.coffee.DTO;

import java.util.Date;

public class TurnoverData {
    private int id;
    private String tableName;
    private double totalPrice;
    private Date dateCheckIn;
    private Date dateCheckOut;
    private int discount;

    public TurnoverData(int id ,String tableName, Double totalPrice, Date dateCheckIn, Date dateCheckOut, int discount){
        this.id = id;
        this.tableName = tableName;
        this.totalPrice = totalPrice;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.discount = discount;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
