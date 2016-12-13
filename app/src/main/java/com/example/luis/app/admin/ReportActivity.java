package com.example.luis.app.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.luis.app.adapters.SalesTotalAdapter;
import com.example.luis.app.models.SalesTotal;
import com.example.luis.app.utils.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReportActivity extends AppCompatActivity implements Response.Listener<JSONObject>, View.OnClickListener
{

    private boolean periodOrDate = true;
    private String dateMax,dateMin,period,total_sales, average_sales,total_orders,total_shipping,total_product;
    private RequestQueue tarea;
    private ProgressDialog procesando;
    private RestApi api;
    private TextView txvTotalVentas,txvPromedioVentas,txvTotalProducts,txvTotalIva,txvTotalOrdenes;
    private List<SalesTotal> list = new ArrayList<>();
    private ListView listView;
    private SalesTotalAdapter adapter;
    private Button btnBarras,btnPastel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent i = getIntent();
        periodOrDate = i.getBooleanExtra("periodOrDate",false);
        dateMax = i.getStringExtra("max");
        dateMin = i.getStringExtra("min");
        System.out.println(dateMin);
        period = i.getStringExtra("period");

        btnPastel = (Button) findViewById(R.id.btnPastel);
        btnBarras = (Button) findViewById(R.id.btnBarras);

        btnPastel.setOnClickListener(this);
        btnBarras.setOnClickListener(this);

        txvTotalVentas = (TextView) findViewById(R.id.txvTotalVentas);
        txvPromedioVentas = (TextView) findViewById(R.id.txvPromedioVentas);
        txvTotalProducts = (TextView) findViewById(R.id.txvTotalProductos);
        txvTotalIva = (TextView) findViewById(R.id.txvTotalImpuesto);
        txvTotalOrdenes = (TextView) findViewById(R.id.txvTotalOrdenes);
        api = new RestApi(this);
        procesando = new ProgressDialog(this);
        procesando.setMessage("Cargando...");
        tarea = Volley.newRequestQueue(this);
        listView = (ListView) findViewById(R.id.listSalesReport);
        load();
    }
    private void load()
    {
        procesando.show();
        String params = (periodOrDate) ?  loadPeriod() :loadDate();
        JsonObjectRequest load = new JsonObjectRequest(Request.Method.GET, MainActivity.url_principal+"/wc-api/v3/reports/sales?"+params,null,this,api)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return api.headers();
            }
        };
        System.out.println(load.getUrl());
        tarea.add(load);
        tarea.start();

    }
    private String loadPeriod ()
    {
        return "filter[period]="+period;
    }
    private String loadDate ()
    {
        return "filter[date_min]="+dateMin+"&filter[date_max]="+dateMax;
    }
    @Override
    public void onResponse(JSONObject response)
    {
            cargarData(response);
    }
    private void cargarData(JSONObject response)
    {
        JSONObject totals = null;
        try {
            JSONObject sales = response.getJSONObject("sales");
            total_sales = sales.getString("total_sales");
            average_sales = sales.getString("average_sales");
            total_orders = sales.getString("total_orders");
            total_shipping = sales.getString("total_shipping");
            total_product = sales.getString("total_items");
            txvTotalVentas.setText(total_sales);
            txvPromedioVentas.setText(average_sales);
            txvTotalIva.setText(total_shipping);
            txvTotalOrdenes.setText(total_orders);
            txvTotalProducts.setText(total_product);
            totals = sales.getJSONObject("totals");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cargarList(totals);
    }
    private void cargarList(JSONObject totals)
    {
        Iterator key = totals.keys();
        String keyJson = "";
        while(key.hasNext())
        {
            keyJson =(String) key.next();
            try {
                JSONObject total = totals.getJSONObject(keyJson);
                String sales = total.getString("sales");
                String orders = total.getString("orders");
                String customers = total.getString("customers");
                String items = total.getString("items");
                list.add(new SalesTotal(sales,orders,customers,items,keyJson));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        procesando.hide();
        adapter = new SalesTotalAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        procesando.cancel();
        procesando = null;
    }

    @Override
    public void onClick(View view)
    {
        Intent i;
        switch (view.getId())
        {
            case R.id.btnBarras:
                i = new Intent(this,BarrasActivity.class);
                data(i);
                startActivity(i);
                break;
            case R.id.btnPastel:
                i = new Intent(this,PastelActivity.class);
                data(i);
                startActivity(i);
                break;
        }
    }
    private  void data (Intent i)
    {
        i.putExtra("total_sales",total_sales);
        i.putExtra("average_sales",average_sales);
        i.putExtra("total_orders",total_orders);
        i.putExtra("total_shipping",total_shipping);
        i.putExtra("total_product",total_product);

    }
}
