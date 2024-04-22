package com.example.coffee.DTO;

public class BillInfoData {
    private int iD;
    private int idBill;
    private int idFood;
    private int count;
    public BillInfoData(int iD, int idBill, int idFood, int count){
        this.iD = iD;
        this.idBill = idBill;
        this.idFood = idFood;
        this.count = count;
    }
    public int getID() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
