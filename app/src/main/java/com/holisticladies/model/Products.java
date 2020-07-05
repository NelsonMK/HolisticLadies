package com.holisticladies.model;

public class Products {

    private String item_name;
    private String item_description;
    private String item_price;
    private String item_image;
    private int item_id, item_maker_id, stock;

    public Products (int item_id, String item_name, String item_description, String item_price, String item_image, int item_maker_id, int stock){
        this.item_id = item_id;
        this.item_name =item_name;
        this.item_description = item_description;
        this.item_price = item_price;
        this.item_image = item_image;
        this.item_maker_id = item_maker_id;
        this.stock = stock;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public String getItem_price() {
        return item_price;
    }

    public int getItem_maker_id() {
        return item_maker_id;
    }

    public String getItem_image() {
        return item_image;
    }

    public int getStock() {
        return stock;
    }
}
