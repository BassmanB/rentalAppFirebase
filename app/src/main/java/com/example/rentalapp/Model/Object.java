package com.example.rentalapp.Model;

public class Object {
    private String Name, Price, MenuId, Description, Image;
    public Object()
    {
    }

    public void setName(String name, String price, String menuId, String description, String image) {
        Image = image;
        Name = name;
        Price = price;
        MenuId = menuId;
        Description  = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
