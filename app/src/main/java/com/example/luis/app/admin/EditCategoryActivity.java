package com.example.luis.app.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.MainActivity;
import com.example.luis.app.R;
import com.example.luis.app.models.Category;
import com.example.luis.app.utils.Base;
import com.example.luis.app.utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class EditCategoryActivity extends AppCompatActivity implements Base,Response.Listener<JSONObject>,View.OnClickListener {

    private Button btnSave;
    private EditText edtName,edtSlug,edtDescription;
    private String id;
    private RequestQueue tarea;
    private RestApi api;
    private ProgressDialog procesando;
    boolean opcion;
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categori);
        btnSave = (Button) findViewById(R.id.btnUpdateCategory);
        btnSave.setOnClickListener(this);
        edtName = (EditText) findViewById(R.id.edtNameCategory);
        edtDescription = (EditText) findViewById(R.id.edtDescriptionCategory);
        edtSlug = (EditText) findViewById(R.id.edtSlugCategory);
        Intent i = getIntent();
        id = i.getStringExtra("id");
        procesando = new ProgressDialog(this);
        procesando.setMessage("Cargando...");
        tarea = Volley.newRequestQueue(this);
        api = new RestApi(this);
        opcion = true;
        category = new Category();
        cargar();

    }

    @Override
    public void cargar() {
        procesando.show();
        JsonObjectRequest cargar = new JsonObjectRequest(Request.Method.GET, MainActivity.url_principal+"/wc-api/v3/products/categories/"+id,null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(cargar);
        tarea.start();
    }

    @Override
    public void cargarVista(JSONObject response) {
        try {
            JSONObject product_category = response.getJSONObject("product_category");
            category.setDescription(product_category.getString("description"));
            category.setName(product_category.getString("name"));
            category.setSlug(product_category.getString("slug"));
            category.setId(product_category.get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        edtDescription.setText(category.getDescription());
        edtName.setText(category.getName());
        edtSlug.setText(category.getSlug());
        procesando.hide();
    }

    @Override
    public void eliminar() {

    }

    @Override
    public void eliminado(JSONObject response) {

    }

    @Override
    public void editar() {
        procesando.setMessage("Guardando");
        procesando.show();
        category.setDescription(edtDescription.getText().toString());
        category.setSlug(edtSlug.getText().toString());
        category.setName(edtName.getText().toString());
        JSONObject data = category.getJson();
        JsonObjectRequest update =  new JsonObjectRequest(Request.Method.PUT,MainActivity.url_principal+"/wc-api/v3/products/categories/"+id,data,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        opcion = false;
        tarea.add(update);
        tarea.start();
    }

    @Override
    public void onResponse(JSONObject response) {
        if (opcion)
        {
            cargarVista(response);
        }else{
            actualizado(response);
        }
    }
    public  void actualizado(JSONObject response)
    {
        Toast.makeText(this,"Categoria actualizada",Toast.LENGTH_SHORT).show();
        procesando.hide();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        procesando.cancel();
        procesando = null;
    }

    @Override
    public void onClick(View view) {
        editar();
    }
}
