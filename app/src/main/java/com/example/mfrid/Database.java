package com.example.mfrid;

// Modal Class
public class Database {
    private String UserID;
    private String password;
    private String MACaddress;

    public Database() {
    }

    public Database(String UserID,String password,String MACaddress) {
    this.UserID=UserID;
    this.password=password;
    this.MACaddress=MACaddress;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMACaddress() {
        return MACaddress;
    }

    public void setMACaddress(String MACaddress) {
        this.MACaddress = MACaddress;
    }
}
