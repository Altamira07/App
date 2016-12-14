package com.example.luis.app.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.MainActivity;
import com.example.luis.app.R;
import com.example.luis.app.adapters.CategoryAdapter;
import com.example.luis.app.models.Category;
import com.example.luis.app.utils.Base;
import com.example.luis.app.utils.RestApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity implements Response.Listener<JSONObject>,View.OnClickListener, Base, AdapterView.OnItemLongClickListener,DialogInterface.OnClickListener{

    private RequestQueue tarea;
    private ProgressDialog procesando;
    private RestApi api;
    private List<Category> list;
    private ListView listView;
    private CategoryAdapter adapter;
    private boolean opcion;
    private Button btnAddCategory;
    private String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        tarea = Volley.newRequestQueue(this);
        procesando = new ProgressDialog(this);
        api = new RestApi(this);
        listView = (ListView)findViewById(R.id.listCategory);
        list = new ArrayList<>();
        opcion = true;
        btnAddCategory = (Button) findViewById(R.id.btnAddCategory);

        btnAddCategory.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
        cargar();
    }

    @Override
    public void onResponse(JSONObject response) {
        if (opcion)
        {
            cargarVista(response);
        }else{
            eliminado(response);
        }
    }

    @Override
    public void cargar() {
        procesando.show();
        JsonObjectRequest cargar = new JsonObjectRequest(Request.Method.GET, MainActivity.url_principal+"/wc-api/v3/products/categories",null,this,api)
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
            JSONArray categories = response.getJSONArray("product_categories");
            for (int i = 0; i < categories.length();i++)
            {
                JSONObject category = categories.getJSONObject(i);
                String name,slug,id;

                name = category.getString("name");
                slug = category.getString("slug");
                id = category.getInt("id") + "";

                list.add(new Category(id,name,slug));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new CategoryAdapter(this,list);
        listView.setAdapter(adapter);

        procesando.hide();
    }

    @Override
    public void eliminar() {
        opcion = false;
        JsonObjectRequest delete = new JsonObjectRequest(Request.Method.DELETE,MainActivity.url_principal+"/wc-api/v3/products/categories/"+item,null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(delete);
        tarea.start();
    }

    @Override
    public void eliminado(JSONObject response)
    {
        try {
            String mensaje = response.getString("message");
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            cargar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        opcion = true;
        onRestart();
    }

    @Override
    public void editar() {
        Intent i = new Intent(this,EditCategoryActivity.class);
        i.putExtra("id",item);
        startActivity(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        CharSequence opciones[] = new CharSequence[] {"Editar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Que deseas hacer?");
        item = view.getTag().toString();
        builder.setItems(opciones, this);
        builder.show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == 0 ){
            editar();
        }else{
            eliminar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        procesando.dismiss();
        procesando = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listView.setAdapter(null);
        list.clear();
        cargar();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAddCategory)
        {
            Intent i = new Intent(this,AddCategoryActivity.class);
            startActivity(i);
        }
    }
}
