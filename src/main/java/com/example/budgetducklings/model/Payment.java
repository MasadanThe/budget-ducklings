package com.example.budgetducklings.model;

public class Payment {
    private String username;
    private String title;
    private String date;
    private String description;
    private String category;
    private double price;

    private int id;

    public Payment(){
        id = 0;
    }

    public Payment(String username, String title, String date, String description, String category, double price){
        this.username = username;
        this.title = title;
        this.date = date;
        this.description = description;
        this.category = category;
        this.price = price;
        id = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
