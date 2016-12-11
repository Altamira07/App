package com.example.luis.app.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by luis on 9/12/16.
 */

public class Product
{
    private String title;
    private String type;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String price;
    private int stock_quantity;
    private String created_at;
    private String updated_at;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
    private int id;

    public Product(String title, String type, String price, int stock_quantity, String created_at, String updated_at, int id) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
    }

    public Product(String title, int id, String price,String img) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.img = img;
    }

    public Product(String title, String description, String price, String created_at) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.created_at = created_at;
    }
    public  Product()
    {

    }
    public JSONObject getJson ()
    {
        JSONObject json = new JSONObject();
        JSONObject product = new JSONObject();
        try {
            product.put("title",title);
            product.put("description",description);
            product.put("regular_price",price);

            json.put("product",product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  json;
    }
    public Product(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
