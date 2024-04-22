package com.example.coffee.DTO;

public class AccountData {
    private String displayName;
    private String userName;
    private String password;
    private String typeAccount;
    public AccountData(String dpn, String usn,String tac){
        this.displayName = dpn;
        this.userName = usn;
//        this.password = pw;
        this.typeAccount = tac;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }
}
