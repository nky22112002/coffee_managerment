package com.example.coffee.DTO;

public class MenuData {
    private String foodName;
    private int count;
    private double price;
    private double totalPrice;
    public MenuData(String foodName, int count, double price, double totalPrice){
        this.foodName = foodName;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
