package com.example.coffee.DTO;

import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MenuManagerData {
    private static MenuManagerData instance;
    public ObservableList<BillData> dtBill;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public static MenuManagerData getInstance() {
        return instance;
    }

    public static void setInstance(MenuManagerData instance) {
        MenuManagerData.instance = instance;
    }



}
