package com.example.luis.app.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 11/12/16.
 */

public class Category
{
    private  String name,description,slug,id;

    public Category(String name, String description, String slug, String id) {
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.id = id;
    }

    public Category(String id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
    public Category()
    {

    }
    public JSONObject getJson () {
        JSONObject data = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            data.put("name",name);
            data.put("description",description);
            data.put("slug",slug);
            json.put("product_category",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
