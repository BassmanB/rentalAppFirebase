package com.example.rentalapp.Model;

public class User {
    private String Name;
    private String Password;
    private String SecureCode;
    private String Phone;

    public User(){

    }

    public User(String name, String password, String secureCode, String phone){
        Name =  name;
        Password = password;
        Phone = phone;
        this.SecureCode = secureCode;



    }

    public String getSecureCode() {
        return SecureCode;
    }

    public void setSecureCode(String secureCode) {
        this.SecureCode = secureCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone(){
        return Phone;
    }

    public void setPhone(String phone){
        Phone = phone;
    }
}
