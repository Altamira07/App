package com.example.luis.app.admin;

import android.app.ProgressDialog;
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
import com.example.luis.app.utils.RestApi;

import org.json.JSONObject;

import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject> {

    private RestApi api;
    private ProgressDialog procesando;
    private RequestQueue tarea;
    private EditText edtName,edtSlug,edtDescription;
    private Category category;
    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categori);
        edtName = (EditText) findViewById(R.id.edtNameCategory);
        edtDescription = (EditText) findViewById(R.id.edtDescriptionCategory);
        edtSlug = (EditText) findViewById(R.id.edtSlugCategory);
        api = new RestApi(this);
        guardar = (Button) findViewById(R.id.btnUpdateCategory);
        guardar.setOnClickListener(this);
        procesando = new ProgressDialog(this);
        tarea = Volley.newRequestQueue(this);
        category = new Category();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUpdateCategory)
            guardar();
    }
    private void guardar ()
    {
        procesando.show();
        category.setName(edtName.getText().toString());
        category.setSlug(edtSlug.getText().toString());
        category.setDescription(edtDescription.getText().toString());
        JSONObject data = category.getJson();
        JsonObjectRequest create = new JsonObjectRequest(Request.Method.POST, MainActivity.url_principal+"/wc-api/v3/products/categories",data,this,api)
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
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Se creo la nueva categoria",Toast.LENGTH_SHORT).show();
        procesando.hide();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        procesando.cancel();
        procesando = null;
    }
}
