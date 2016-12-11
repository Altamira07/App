package com.example.luis.app.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luis.app.R;
import com.example.luis.app.models.Product;
import com.example.luis.app.utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by luis on 9/12/16.
 */

public class ProductAdapter extends BaseAdapter
{
    private Context context;
    private List<Product> list;
    ImageView img1;
    public ProductAdapter(Context context, List<Product> list) {
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
            rowView = inflater.inflate(R.layout.list_products,null);
        }
        TextView txvTitle = (TextView) rowView.findViewById(R.id.txvTitle);
        TextView txvPrice = (TextView) rowView.findViewById(R.id.txvPrice);
        img1 = (ImageView) rowView.findViewById(R.id.imgProduct);
        final Product item = this.list.get(i);
        txvPrice.setText(item.getPrice());
        txvTitle.setText(item.getTitle());
        rowView.setTag(item.getId());
        String sUrl = item.getImg();
        try {
            final Bitmap bitmap = new ProductAdapter.BackgroundTask().execute(sUrl).get();
            img1.setImageBitmap(bitmap);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    ImageView ivFoto = new ImageView(context);
                    TextView tvNombre = new TextView(context);
                    tvNombre.setText(item.getTitle());
                    ivFoto.setImageBitmap(bitmap);

                    LinearLayout layout1 = new LinearLayout(context);
                    layout1.setOrientation(LinearLayout.VERTICAL);
                    layout1.addView(ivFoto);
                    layout1.addView(tvNombre);
                    //builder.setView(ivFoto);
                    builder.setView(layout1);
                    AlertDialog dialogFoto = builder.create();
                    dialogFoto.show();

                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowView;
    }
    private class BackgroundTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... url) {
            //---download an image---
            Bitmap bitmap = Utils.DownloadImage(url[0]);
            return bitmap;
        }
    }
}
