package com.example.luis.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.admin.AdminActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener,Response.Listener<String>,View.OnClickListener{
    private  EditText edtEmail,edtPassword;
    private String url = "https://storeitc-luisitoaltamira.rhcloud.com/auth_users.php";
    public static final String url_principal = "https://storeitc-luisitoaltamira.rhcloud.com";
    private RequestQueue tarea;
    private ProgressDialog progressDialog;
    private String session = "";
    private Button btnLogueate;
    //TextView prueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tarea = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        btnLogueate = (Button) findViewById(R.id.btnLogin);
        btnLogueate.setOnClickListener(this);
        //edtEmail = (EditText) findViewById(R.id.edtEmail);
        //edtPassword = (EditText) findViewById(R.id.edtPassword);
        progressDialog.setMessage("Cargando...");
        //prueba = (TextView) findViewById(R.id.prueba);
    }


    //Hace petecion del login
    private  void login () {
        progressDialog.show();
        StringRequest login = new StringRequest(Request.Method.POST, url, this, this) {
            //Se envian los datos necesarios para la peticion
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Parametros en la peticion
                params.put("username", "luisroberto");
                params.put("password", "9C6R(RxTqB#OxrNOnZ");
                return params;
            }
        };
        tarea.add(login);
        tarea.start();
    }
    //Identifico el tipo de usuario
    public  void auth ()
    {
        try {
            JSONObject json = new JSONObject(session);
            if (json != null) {
                boolean b = json.getBoolean("valido");
                if (b) {
                    int id = json.getInt("id");
                    String rol = json.getString("rol");
                    //Checo que tipo de usuario es, dependiendo de este se envia a su respectiva pantalla
                    if (rol.equalsIgnoreCase("administrator")) {
                        Intent i = new Intent(this, AdminActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);

                    } else {

                    }
                } else
                    Toast.makeText(this, "Usuario o contrasenia incorrectos", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Error si no se logra hace la peticion correctamente
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
        progressDialog.hide();
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    //Responde con la peticion
    @Override
    public void onResponse(String response) {
        session = response;
        progressDialog.hide();
        auth();
    }

    @Override
    public void onClick(View view) {
        login();
    }
}
