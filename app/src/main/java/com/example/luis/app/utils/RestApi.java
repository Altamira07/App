package com.example.luis.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.Request;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luis on 8/12/16.
 * 466 103 63 78
 */

public class RestApi implements Response.ErrorListener
{
    private final String ck = "ck_a64ead43b826563f0e0fad044920da0f166ae7fe",
            cs = "cs_0b6239dded2cbf1bc56bbbae5a49e913842123b9";
    private Context context;

    public RestApi(Context context)
    {
        this.context = context;
    }
    //Credenciales de autenticacion
    public Map<String,String> headers()
    {
        Map<String, String> headers = new HashMap<>();
        String credentials = ck+":"+cs;
        String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
        headers.put("Authorization",auth);
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");
        return headers;
    }
     //Error si no se pueden obtener los datos
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        String message ="";
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        message+=volleyError.getMessage();

        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
