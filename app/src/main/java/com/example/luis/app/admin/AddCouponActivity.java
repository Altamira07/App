package com.example.luis.app.admin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luis.app.MainActivity;
import com.example.luis.app.R;
import com.example.luis.app.models.Coupon;
import com.example.luis.app.utils.RestApi;

import org.json.JSONObject;

import java.util.Map;

public class AddCouponActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>,AdapterView.OnItemSelectedListener {

    private Button btnGuardar;
    private RestApi api;
    private EditText edtCode,edtAmount,edtLimit;
    private Spinner spnType;
    private Coupon cupon;
    private ProgressDialog procesando;
    private String type = "fixed_cart";
    private RequestQueue tarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        btnGuardar = (Button) findViewById(R.id.btnGuardarCopun);
        api = new RestApi(this);
        edtCode = (EditText) findViewById(R.id.editCouponCode);
        edtAmount = (EditText) findViewById(R.id.editAmountCoupon);
        edtLimit = (EditText) findViewById(R.id.editLimitCoupon);
        spnType = (Spinner) findViewById(R.id.spnType);
        ArrayAdapter arrayType = ArrayAdapter.createFromResource(this,R.array.spnType,android.R.layout.simple_spinner_item);
        arrayType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(arrayType);
        spnType.setOnItemSelectedListener(this);
        tarea = Volley.newRequestQueue(this);
        procesando = new ProgressDialog(this);
        procesando.setMessage("Guardando");
        btnGuardar.setOnClickListener(this);

    }
    private void guardar()
    {
        String code = edtCode.getText().toString();
        String monto = edtAmount.getText().toString();
        String limit = edtLimit.getText().toString();
        cupon = new Coupon(code,type,monto,limit);
        procesando.show();
        JSONObject data = cupon.getJson();
        JsonObjectRequest create = new JsonObjectRequest(Request.Method.POST, MainActivity.url_principal+"/wc-api/v3/coupons",data,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        tarea.add(create);
        tarea.start();
    }
    @Override
    public void onClick(View view) {
        guardar();
    }

    @Override
    public void onResponse(JSONObject response) {
            Toast.makeText(this,"Cupon agregado",Toast.LENGTH_SHORT).show();
            procesando.cancel();
            procesando = null;
            finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i)
        {
            case 0:
                type = "fixed_cart";
                break;
            case 1:
                type = "percent";
                break;
            case 2:
                type = "fixed_product";
                break;
            case 3:
                type = "percent_product";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
