package com.example.luis.app.models;

/**
 * Created by luis on 8/12/16.
 */

public class User
{
    private String rol, nombre;
    private  int id ;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
