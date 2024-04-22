package com.example.coffee.DTO;
import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class TableFoodData {
    private int iD;
    private String tableName;
    private String status;
    public TableFoodData(int iD, String tableName, String status){
        this.iD = iD;
        this.tableName = tableName;
        this.status = status;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
