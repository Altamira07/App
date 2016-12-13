package com.example.luis.app.models;

/**
 * Created by luis on 13/12/16.
 */

public class SalesTotal
{
    private String sales,orders,customers,items,key;

    public SalesTotal(String sales, String orders, String customers, String items, String key) {
        this.sales = sales;
        this.orders = orders;
        this.customers = customers;
        this.items = items;
        this.key = key;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
