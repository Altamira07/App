package com.example.luis.app.models;

/**
 * Created by luis on 8/12/16.
 */

public class Customer
{
    private int id;
    private String created_at,email,first_name,username,avatar_url;

    public Customer(int id, String created_at, String email, String first_name, String username, String avatar_url) {
        this.id = id;
        this.created_at = created_at;
        this.email = email;
        this.first_name = first_name;
        this.username = username;
        this.avatar_url = avatar_url;
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
