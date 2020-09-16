package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

public class ProductTypeAdapter extends ArrayAdapter<ProductTypeDto> {
    private Context context;
    private int resource;
    private List<ProductTypeDto> list;
    private String defaultItem;

    public ProductTypeAdapter(@NonNull Context context, @NonNull List<ProductTypeDto> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        ProductTypeViewHolder productTypeViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_product_type, parent, false);
            productTypeViewHolder = new ProductTypeViewHolder();
            productTypeViewHolder.txtProductType = convertView.findViewById(R.id.txt_product_type);


            convertView.setTag(productTypeViewHolder);
        } else {
            productTypeViewHolder = (ProductTypeViewHolder) convertView.getTag();
        }

        ProductTypeDto productTypeDto = getItem(position);

        if (productTypeDto != null) {
            productTypeViewHolder.txtProductType.setText(productTypeDto.getName());
        }

        return convertView;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    static class ProductTypeViewHolder {

        TextView txtProductType;
    }
}
