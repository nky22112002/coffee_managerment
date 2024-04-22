package com.example.coffee.DTO;

import java.util.List;

public class FoodsData {

    private int iD;
    private String foodName;
    private String price;
    private String categoryName;
    public FoodsData(int iD, String foodName, String  price, String categoryName){
        this.iD = iD;
        this.foodName = foodName;
        this.price = price;
        this.categoryName = categoryName;
    }

    public int getID() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
