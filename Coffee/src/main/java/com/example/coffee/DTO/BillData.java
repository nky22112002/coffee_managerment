package com.example.coffee.DTO;

import java.sql.Date;

public class BillData {
    private int iD;
    private Date dateCheckIn;
    private Date dateCheckOut;
    private int idTable;
    private int status;
    public BillData(int iD, Date dateCheckIn, Date dateCheckOut, int idTable, int status){
        this.iD = iD;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.idTable = idTable;
        this.status =  status;
    }
    public int getID() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
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

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
