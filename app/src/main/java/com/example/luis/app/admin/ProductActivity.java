package com.example.luis.app.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.luis.app.adapters.ProductAdapter;
import com.example.luis.app.models.Product;
import com.example.luis.app.utils.RestApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity implements Response.Listener<JSONObject>,AdapterView.OnItemLongClickListener,DialogInterface.OnClickListener
{

    private ProgressDialog procesando;
    private List<Product> listPorduct;
    private ProductAdapter adapter;
    private RestApi api;
    private ListView list;
    private RequestQueue tarea;
    private String item;
    private int opcion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_product);
        procesando = new ProgressDialog(this);
        listPorduct = new ArrayList<>();
        api = new RestApi(this);
        list = (ListView) findViewById(R.id.listProduct);
        list.setOnItemLongClickListener(this);
        tarea = Volley.newRequestQueue(this);

        procesando.setMessage("Cargando...");
        cargar();

    }
    public void cargar ()
    {
        procesando.show();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,MainActivity.url_principal+"/wc-api/v3/products",null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(json);
        tarea.start();
    }

    @Override
    public void onResponse(JSONObject response){
        if (opcion == 1 ) {
            cargarLista(response);
        }else {
            eliminado(response);
        }

    }
    private  void eliminado (JSONObject response)
    {
        try {
            String mensaje = response.getString("message");
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
            cargar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        opcion = 1;
        onRestart();

    }
    public void cargarLista (JSONObject data)
    {
        String title,price,img;
        int id,stock;
       try{
           JSONArray products = data.getJSONArray("products");
           for (int i = 0; i<products.length(); i++)
           {
               JSONObject product = products.getJSONObject(i);
               title = product.getString("title");
               price = product.getString("price");
               id = product.getInt("id");
               img = product.getString("featured_src");

               listPorduct.add(new Product(title,id,price,img));
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
        adapter = new ProductAdapter(this,listPorduct);
        list.setAdapter(adapter);
        procesando.hide();
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

    private void eliminar(){
        opcion = 0;
        JsonObjectRequest delete = new JsonObjectRequest(Request.Method.DELETE,MainActivity.url_principal+"/wc-api/v3/products/"+item,null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(delete);
        tarea.start();
    }
    private void editar(){
        Intent intent = new Intent(this,EditProductActivity.class);
        intent.putExtra("id",item);
        startActivity(intent);

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
        list.setAdapter(null);
        listPorduct.clear();
        cargar();

    }
}
