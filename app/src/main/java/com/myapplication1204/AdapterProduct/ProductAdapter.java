package com.myapplication1204.AdapterProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapplication1204.Model.Product;
import com.myapplication1204.R;


import java.util.List;

public class ProductAdapter  extends BaseAdapter {

    Context context;
    int item_layout;
    List<Product> products;

    public ProductAdapter(Context context, int item_layout, List<Product> products) {
        this.context = context;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            // linking ciew
            viewHolder.txtInfo = convertView.findViewById(R.id.txtInfo);
            viewHolder.imvEdit = convertView.findViewById(R.id.imvEdit);
            viewHolder.imvDelete = convertView.findViewById(R.id.imvDelete);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Binding data
        Product p = products.get(position);
        viewHolder.txtInfo.setText(p.getProductCode()+ " - " + p.getProductName()+" - "+ String.format("%.0f d",p.getProductPrice()));

        return convertView;
    }

    public static class ViewHolder {
        TextView txtInfo;
        ImageView imvEdit,imvDelete;
    }
}