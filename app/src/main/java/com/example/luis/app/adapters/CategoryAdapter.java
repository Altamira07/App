package com.example.luis.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luis.app.R;
import com.example.luis.app.models.Category;
import com.example.luis.app.models.Customer;

import java.util.List;

/**
 * Created by luis on 11/12/16.
 */

public class CategoryAdapter extends BaseAdapter
{
    private Context context;
    List<Category> list;

    public CategoryAdapter(Context context, List<Category> list) {
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
            rowView = inflater.inflate(R.layout.list_category,null);
        }
        TextView txvName = (TextView) rowView.findViewById(R.id.txvName_category);
        TextView txvSlug = (TextView) rowView.findViewById(R.id.txvSlug_categoy);
        final  Category category = list.get(i);
        txvName.setText(category.getName());
        txvSlug.setText(category.getSlug());
        rowView.setTag(category.getId());
        return rowView;
    }
}
