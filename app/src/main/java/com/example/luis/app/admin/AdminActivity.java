package com.example.luis.app.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luis.app.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btnClientes,btnProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnClientes = (Button) findViewById(R.id.btnClientes);
        btnClientes.setOnClickListener(this);
        btnProducts = (Button) findViewById(R.id.btnProducts);
        btnProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId())
        {
            case R.id.btnClientes:
                i = new Intent(this,CustomerActivity.class);
                startActivity(i);
                break;
            case R.id.btnProducts:
                i = new Intent(this,ProductActivity.class);
                startActivity(i);
                break;
        }
    }
}
