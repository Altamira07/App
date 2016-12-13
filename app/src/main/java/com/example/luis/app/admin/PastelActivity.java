package com.example.luis.app.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.luis.app.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PastelActivity extends AppCompatActivity {

    private String total_sales, average_sales,total_orders,total_shipping,total_product;
    private PieChart pastel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastel_activiy);
        Intent i = getIntent();
        pastel = (PieChart) findViewById(R.id.pastel);
        total_sales = i.getStringExtra("total_sales");
        average_sales = i.getStringExtra("average_sales");
        total_orders = i.getStringExtra("total_orders");
        total_shipping = i.getStringExtra("total_shipping");
        total_product = i.getStringExtra("total_product");
        graficar();
    }
    private void graficar ()
    {
        int totalOrder,totalProduct;
        double totalSales,avergeSales,totalShip;
        ArrayList<PieEntry> data = new ArrayList<>();
        try{

            totalSales = Double.parseDouble(total_sales);
            avergeSales = Double.parseDouble(average_sales);
            totalOrder = Integer.parseInt(total_orders);
            totalShip = Double.parseDouble(total_shipping);
            totalProduct = Integer.parseInt(total_product);
            data.add(new PieEntry((float) totalSales,"Total de ventas"));
            data.add(new PieEntry((float)totalShip,"Impuestos"));
            data.add(new PieEntry(totalOrder,"Total de ordenes"));
            data.add(new PieEntry((float) avergeSales,"Promedio de ventas"));
            data.add(new PieEntry(totalProduct,"Total de productos"));


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        PieDataSet dataSet = new PieDataSet(data,"Valores");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(dataSet);
        pastel.setData(pieData);

    }
}
