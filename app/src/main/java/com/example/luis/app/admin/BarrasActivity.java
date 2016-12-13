package com.example.luis.app.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.luis.app.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarrasActivity extends AppCompatActivity {

    private String total_sales, average_sales,total_orders,total_shipping,total_product;
    private BarChart grafica;
    ArrayList<BarEntry> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barras);

        grafica = (BarChart) findViewById(R.id.barras);
        Intent i = getIntent();
        total_sales = i.getStringExtra("total_sales");
        average_sales = i.getStringExtra("average_sales");
        total_orders = i.getStringExtra("total_orders");
        total_shipping = i.getStringExtra("total_shipping");
        total_product = i.getStringExtra("total_product");

        graficar ();
    }
    private void  graficar ()
    {
        int totalOrder,totalProduct;
        double totalSales,avergeSales,totalShip;
        try{

            totalSales = Double.parseDouble(total_sales);
            avergeSales = Double.parseDouble(average_sales);
            totalOrder = Integer.parseInt(total_orders);
            totalShip = Double.parseDouble(total_shipping);
            totalProduct = Integer.parseInt(total_product);
            datos.add(new BarEntry(0,(int)totalSales));
            datos.add(new BarEntry(1,(int)avergeSales));
            datos.add(new BarEntry(2,totalOrder));
            datos.add(new BarEntry(3,(int)totalShip));
            datos.add(new BarEntry(4,totalProduct));

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        BarDataSet dataset = new BarDataSet(datos, "ventas,promedio ventas, total ordenes, impuestos,productos");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dataset);
        grafica.setData(data);
    }
}
