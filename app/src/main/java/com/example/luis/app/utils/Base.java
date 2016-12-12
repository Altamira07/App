package com.example.luis.app.utils;

import org.json.JSONObject;

/**
 * Created by luis on 11/12/16.
 */

public interface Base  {
    void cargar();
    void cargarVista(JSONObject response);
    void eliminar();
    void eliminado(JSONObject response);
    void editar();
}
