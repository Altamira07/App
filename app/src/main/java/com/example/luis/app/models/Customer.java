package com.example.luis.app.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 8/12/16.
 */

public class Customer
{
    private int id;
    private String created_at;
    private String email;
    private String first_name;
    private String username;
    private String avatar_url;
    private String last_name;
    private String country;
    private String phone;
    private String city;
    private String address;
    public Customer(int id, String created_at, String email, String first_name, String username, String avatar_url) {
        this.id = id;
        this.created_at = created_at;
        this.email = email;
        this.first_name = first_name;
        this.username = username;
        this.avatar_url = avatar_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JSONObject getJson()
    {
        JSONObject object = new JSONObject();
        JSONObject customer = new JSONObject();
        JSONObject bilding = new JSONObject();
        try {
            customer.put("first_name",first_name);
            customer.put("last_name",last_name);
            customer.put("username",username);
            bilding.put("country",country);
            bilding.put("phone",phone);
            bilding.put("state",state);
            bilding.put("city",city);
            customer.put("billing_address",bilding);
            object.put("customer",customer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    private String state;

    public Customer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
