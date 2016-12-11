package com.example.luis.app.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.MainActivity;
import com.example.luis.app.R;
import com.example.luis.app.models.Product;
import com.example.luis.app.utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class EditProductActivity extends AppCompatActivity implements Response.Listener<JSONObject>,View.OnClickListener {

    String id;
    RestApi api;
    ProgressDialog procesando;
    RequestQueue tarea;
    Button btnGuardar;
    EditText editTitle,editPrice,editDescription,editStock;
    TextView txvCreated_at,txvUpdate_at;
    Product product;
    boolean opcion;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        api = new RestApi(this);
        btnGuardar = (Button) findViewById(R.id.btnGuardarProduct);
        btnGuardar.setOnClickListener(this);
        editTitle = (EditText) findViewById(R.id.editTitleProduct);
        editPrice = (EditText) findViewById(R.id.editPriceProduct);
        editDescription = (EditText)findViewById(R.id.editDescription);
        txvCreated_at = (TextView)  findViewById(R.id.txvCreated_atProduct);
        txvUpdate_at = (TextView) findViewById(R.id.txvUpdated_atProdct);
        procesando = new ProgressDialog(this);
        procesando.setMessage("Cargando...");
        tarea = Volley.newRequestQueue(this);
        Intent i = getIntent();
        id = i.getStringExtra("id");
        opcion = true;
        cargar();
    }
    private void cargar()
    {
        procesando.show();
        JsonObjectRequest cargar = new JsonObjectRequest(Request.Method.GET, MainActivity.url_principal+"/wc-api/v3/products/"+id,null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(cargar);
        tarea.start();
    }
    private  void  data (JSONObject response)
    {
        try {
            JSONObject data = response.getJSONObject("product");
            String title,description,price,created_at,updated_at;
            title = data.getString("title");
            description = data.getString("description");
            updated_at = data.getString("updated_at");
            created_at = data.getString("created_at");
            price = data.getString("price");
            Toast.makeText(this,"Actualizado"+updated_at,Toast.LENGTH_SHORT).show();
            product = new Product(title,description,price,created_at);
            product.setUpdated_at(updated_at);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cargarVista();

    }
    @Override
    public void onResponse(JSONObject response) {
        if (opcion)
        {
            data(response);
            procesando.hide();
        }else{
            procesando.cancel();
            procesando = null;
            finish();
        }
    }
    private void cargarVista()
    {
        editTitle.setText(product.getTitle());
        editDescription.setText(product.getDescription());
        editPrice.setText(product.getPrice());
        txvUpdate_at.setText(product.getUpdated_at());
        txvCreated_at.setText(product.getCreated_at());
        procesando.hide();
    }
    public  void  actualiza ()
    {
        procesando.setMessage("Guardando...");
        procesando.show();
        JSONObject data = product.getJson();
        JsonObjectRequest update = new JsonObjectRequest(Request.Method.PUT,MainActivity.url_principal+"/wc-api/v3/products/"+id,data,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };

        tarea.add(update);
        tarea.start();
    }
    public void cargaActualizacion()
    {
        product.setTitle(editTitle.getText().toString());
        product.setDescription(editDescription.getText().toString());
        product.setPrice(editPrice.getText().toString());
        actualiza();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGuardarProduct) {
            opcion = false;
            cargaActualizacion();

        }

    }
}
