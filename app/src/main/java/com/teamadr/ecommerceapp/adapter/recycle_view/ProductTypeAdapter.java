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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductTypeAdapter extends RecyclerViewAdapter {
    public ProductTypeAdapter(Context context) {
        super(context, false);
    }


    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_product_type, parent, false);
        return new ProductTypeViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ProductTypeDto productTypeDto = getItem(position, ProductTypeDto.class);
        ProductTypeViewHolder viewHolder = (ProductTypeViewHolder) holder;

        if (productTypeDto != null) {
            viewHolder.txtProductType.setText(productTypeDto.getName());
        }
    }

    class ProductTypeViewHolder extends NormalViewHolder {
        @BindView(R.id.txt_product_type)
        TextView txtProductType;

        public ProductTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
