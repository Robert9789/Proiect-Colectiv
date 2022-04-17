package com.example.shop.Model;

import java.util.*;

public class Products
{
    public String pname, description,category,productState,image;
    public int price ;
    public Date date;
    public double time;
    public int pid;
    public Products()
    {

    }

    public Products(String pname, String description,String category, String productState, String image,int price,Date date, double time,int pid)
    {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.productState = productState;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
     public String getPname() {
        return pname;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public  int getPrice() {
        return price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return pid ;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getProductState() {
        return productState;
    }
}