package com.example.luis.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luis.app.R;
import com.example.luis.app.models.SalesTotal;

import java.util.List;

/**
 * Created by luis on 13/12/16.
 */

public class SalesTotalAdapter extends BaseAdapter {
    private Context context;
    private List<SalesTotal> list;

    public SalesTotalAdapter(Context context, List<SalesTotal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_sales_report,null);
        }
        TextView date = (TextView) rowView.findViewById(R.id.txvSalesReportDate);
        TextView clientes = (TextView) rowView.findViewById(R.id.txvCientesList);
        TextView subTotal = (TextView) rowView.findViewById(R.id.txvSubtotalList);
        TextView products = (TextView) rowView.findViewById(R.id.txvProductList);
        TextView order = (TextView) rowView.findViewById(R.id.txvTotalOrderList);
        final  SalesTotal sales = list.get(i);
        date.setText(sales.getKey().toString());
        clientes.setText(sales.getCustomers());
        subTotal.setText(sales.getSales());
        products.setText(sales.getItems());
        order.setText(sales.getOrders());
        return rowView;
    }
}
