package com.example.rentalapp.Model;

public class Object {
    private String name, price, menuId, description, image;
    public Object()
    {
    }

    public void setName(String Name, String Price, String MenuId, String Description, String Image) {
        image = Image;
        name = Name;
        price = Price;
        menuId = MenuId;
        description  = Description;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String Price) {
        price = price;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String MenuId) {
        menuId = MenuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String Image) {
        image = Image;
    }
}
