package com.example.luis.app.admin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.MainActivity;
import com.example.luis.app.R;
import com.example.luis.app.adapters.CustomerAdapter;
import com.example.luis.app.models.Customer;
import com.example.luis.app.utils.RestApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerActivity extends AppCompatActivity implements Response.Listener<String>
{
    private List<Customer> listCustomer;
    private CustomerAdapter adapter;
    private RestApi api;
    private RequestQueue tarea;
    private ProgressDialog procesando;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        procesando = new ProgressDialog(this);
        list = (ListView) findViewById(R.id.listCustomer);
        listCustomer = new ArrayList<>();

        tarea =  Volley.newRequestQueue(this);
        api = new RestApi(this);
        load();
    }
    private void load()
    {
        procesando.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  MainActivity.url_principal+"/wc-api/v3/customers",this,api)
        {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError
            {
                return api.headers();
            }
        };
        tarea.add(stringRequest);
        tarea.start();
    }
    @Override
    public void onResponse(String response) {
        cargarLista(response);
    }
    private void cargarLista (String data)
    {
        int id,i = 0;
        String created_at,email,first_name,username,avatar_url;
        try {
            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("customers");
            for ( i = 0 ; i<jsonArray.length(); i++)
            {
                JSONObject customer = jsonArray.getJSONObject(i);
                created_at = customer.getString("created_at");
                email = customer.getString("email");
                first_name = customer.getString("first_name");
                username = customer.getString("username");
                avatar_url = customer.getString("avatar_url");
                id = customer.getInt("id");
                listCustomer.add(new Customer(id,created_at,email,first_name,username,avatar_url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new CustomerAdapter(this,listCustomer);
        list.setAdapter(adapter);
        procesando.hide();
    }
}
