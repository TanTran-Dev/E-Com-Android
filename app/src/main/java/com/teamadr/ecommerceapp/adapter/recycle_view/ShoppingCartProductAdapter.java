package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;
import com.teamadr.ecommerceapp.view.product_detail.ProductDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartProductAdapter extends EndlessLoadingRecyclerViewAdapter {
    public ShoppingCartProductAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_loading, parent, false);
        return new LoadingViewHolder(view);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_shopping_cart_product, parent, false);
        return new ShoppingCartProductViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        if (holder instanceof ShoppingCartProductViewHolder) {
            ShoppingCartProductViewHolder shoppingCartProductViewHolder = (ShoppingCartProductViewHolder) holder;
            ShoppingCartProductDto shoppingCartProductDto = getItem(position, ShoppingCartProductDto.class);

            Glide.with(shoppingCartProductViewHolder.itemView.getContext())
                    .load(shoppingCartProductDto.getProductDto().getSmallImageUrl())
                    .into(shoppingCartProductViewHolder.imgProduct);


            shoppingCartProductViewHolder.txtProductName.setText(shoppingCartProductDto.getProductDto().getName());
            shoppingCartProductViewHolder.txtProductPrice.setText(String.valueOf(shoppingCartProductDto.getProductDto().getPrice()));
            shoppingCartProductViewHolder.txtInformation.setText(shoppingCartProductDto.getProductDto().getInformation());
            shoppingCartProductViewHolder.txtCountLimit.setText("Hiện có: " + shoppingCartProductDto.getProductDto().getCount());
            shoppingCartProductViewHolder.txtProductCount.setText(String.valueOf(shoppingCartProductDto.getCount()));

            if (isInSelectedMode()) {
                shoppingCartProductViewHolder.chkDelete.setVisibility(View.VISIBLE);
                if (isItemSelected(position)) {
                    shoppingCartProductViewHolder.chkDelete.setChecked(true);
                } else {
                    shoppingCartProductViewHolder.chkDelete.setChecked(false);
                }
            } else {
                shoppingCartProductViewHolder.chkDelete.setVisibility(View.GONE);
            }
        }
    }

    class ShoppingCartProductViewHolder extends NormalViewHolder implements View.OnClickListener {
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;
        @BindView(R.id.txtInformation)
        TextView txtInformation;
        @BindView(R.id.txtProductCount)
        TextView txtProductCount;
        @BindView(R.id.txtCountLimit)
        TextView txtCountLimit;
        @BindView(R.id.btnDesc)
        Button btnDesc;
        @BindView(R.id.btnAsc)
        Button btnAsc;
        @BindView(R.id.txtBuyNow)
        TextView txtBuyNow;
        @BindView(R.id.chkDelete)
        CheckBox chkDelete;

        ShoppingCartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            chkDelete.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> setSelectedItem(getAdapterPosition(), isChecked));

            btnAsc.setOnClickListener(this);
            btnDesc.setOnClickListener(this);
            txtBuyNow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPos = getAdapterPosition();
            ShoppingCartProductDto shoppingCartProductDto = getItem(itemPos, ShoppingCartProductDto.class);
            int count = shoppingCartProductDto.getCount();

            switch (v.getId()){
                case R.id.btnAsc:{
                    if (count < shoppingCartProductDto.getProductDto().getCount()) {
                        count++;
                        txtProductCount.setText(String.valueOf(count));
                        shoppingCartProductDto.setCount(count);
                    }
                }
                break;

                case R.id.btnDesc:{
                    if (count > 1){
                        count --;
                        txtProductCount.setText(String.valueOf(count));
                        shoppingCartProductDto.setCount(count);
                    }
                }
                break;

                case R.id.txtBuyNow:{
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra(StringConstant.KEY_PRODUCT_ID, shoppingCartProductDto.getProductDto().getId());
                    intent.putExtra(StringConstant.KEY_COUNT_OPTION, count);
                    getContext().startActivity(intent);
                }
                break;
            }
        }
    }
}
