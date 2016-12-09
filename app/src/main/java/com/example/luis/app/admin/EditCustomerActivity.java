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
import com.example.luis.app.models.Customer;
import com.example.luis.app.utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EditCustomerActivity extends AppCompatActivity implements Response.Listener<JSONObject>, View.OnClickListener
{
    private Customer customer;
    private   RequestQueue tarea;
    private ProgressDialog procesando;
    private RestApi api;
    private Button btnGuardar;
    private TextView txvEmail;
    private EditText edtFirtName,edtLastName,edtUserName,edtCountry,edtPhone,edtCity,edtState;
    boolean opcion = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id",-1);
        customer = new Customer(id);
        procesando = new ProgressDialog(this);
        api = new RestApi(this);
        //Toast.makeText(this,"Vamo a ediat"+id,Toast.LENGTH_SHORT).show();
        txvEmail = (TextView) findViewById(R.id.txvEmail);
        edtFirtName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtCountry = (EditText) findViewById(R.id.edtCountry);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtState = (EditText) findViewById(R.id.edtState);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        procesando.setMessage("Cargando...");
        tarea = Volley.newRequestQueue(this);
        cargar();
    }


    //Cargo los datos
    private void cargar ()
    {
        procesando.show();
        JsonObjectRequest jsonObjectRequest
                = new JsonObjectRequest(Request.Method.GET,
                MainActivity.url_principal+"/wc-api/v3/customers/"+customer.getId(),null,
                this,api)
        {
            @Override
            public Map<String,String> getHeaders ()throws AuthFailureError
            {
                return api.headers();
            }
        };
        tarea.add(jsonObjectRequest);
        tarea.start();
    }


    public void data (JSONObject json)
    {
        try {
            JSONObject jsonCustomer = json.getJSONObject("customer");

            customer.setLast_name(jsonCustomer.getString("last_name"));
            customer.setUsername(jsonCustomer.getString("username"));
            customer.setFirst_name(jsonCustomer.getString("first_name"));
            customer.setEmail(jsonCustomer.getString("email"));

            JSONObject jsonBiladdr = jsonCustomer.getJSONObject("billing_address");
            customer.setCity(jsonBiladdr.getString("city"));
            customer.setState(jsonBiladdr.getString("state"));
            customer.setCountry(jsonBiladdr.getString("country"));
            customer.setPhone(jsonBiladdr.getString("phone"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        cargarVista();
    }
    public  void  cargarVista ()
    {
        edtFirtName.setText(customer.getFirst_name());
        edtLastName.setText(customer.getLast_name());
        edtState.setText(customer.getState());
        edtCity.setText(customer.getCity());
        edtCountry.setText(customer.getCountry());
        edtUserName.setText(customer.getUsername());
        edtPhone.setText(customer.getPhone());
        txvEmail.setText(customer.getEmail());
    }
    private void cargaActualizacion ()
    {
        customer.setFirst_name(edtFirtName.getText().toString());
        customer.setLast_name(edtLastName.getText().toString());
        customer.setState(edtState.getText().toString());
        customer.setCity(edtCity.getText().toString());
        customer.setCountry(edtCountry.getText().toString());
        customer.setUsername(edtUserName.getText().toString());
        customer.setPhone(edtPhone.getText().toString());
        actualiza();
    }
    private void actualiza ()
    {
        procesando.setMessage("Guardando...");
        procesando.show();
        final JSONObject jsonCustomer = customer.getJson();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,MainActivity.url_principal+"/wc-api/v3/customers/"+customer.getId(),jsonCustomer,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }

        };
        tarea.add(jsonObjectRequest);
        tarea.start();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnGuardar) {
            opcion = false;
            cargaActualizacion();

        }
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
}
