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
import com.example.luis.app.models.Customer;
import com.example.luis.app.utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by luis on 8/12/16.
 */

public class CustomerAdapter extends BaseAdapter
{
    private  List<Customer> list;
    private Context context;
    ImageView img1;
    public CustomerAdapter(Context context, List<Customer> listCustomer) {
        this.context = context;
        this.list = listCustomer;
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

        return  i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.a_list_customers,null);
        }
        TextView txvNombre = (TextView) rowView.findViewById(R.id.txvNombre);
        TextView txvEmail = (TextView) rowView.findViewById(R.id.txvEmail);
        img1 = (ImageView) rowView.findViewById(R.id.avatarCustomer);
        final Customer item = this.list.get(i);
        rowView.setTag(item.getId());
        txvEmail.setText(item.getEmail());
        txvNombre.setText(item.getFirst_name());
        String sUrl = "https://www.gravatar.com/avatar/" + Utils.md5(item.getEmail().toLowerCase());

        System.out.println("URL GRAVATAR " + sUrl);

        try {
            final Bitmap bitmap = new BackgroundTask().execute(sUrl).get();
            img1.setImageBitmap(bitmap);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    ImageView ivFoto = new ImageView(context);
                    TextView tvNombre = new TextView(context);
                    tvNombre.setText(item.getUsername());
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
