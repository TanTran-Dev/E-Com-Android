package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerViewAdapter {

    public ProductAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        ProductDto productDto = getItem(position, ProductDto.class);


        Glide.with(productViewHolder.itemView.getContext()).load(productDto.getSmallImageUrl())
                .error(R.drawable.logo_placeholder)
                .into(productViewHolder.imgProduct);


        productViewHolder.txtProductName.setText(productDto.getName());
        productViewHolder.txtProductPrice.setText(String.valueOf(productDto.getPrice()));
        productViewHolder.txtInformation.setText(productDto.getInformation());
        productViewHolder.txtCreatedDate.setText(getContext().getString(R.string.created_date) + productDto.getCreatedDate());


        if (isInSelectedMode()) {
            productViewHolder.chkDelete.setVisibility(View.VISIBLE);
            if (isItemSelected(position)) {
                productViewHolder.chkDelete.setChecked(true);
            } else {
                productViewHolder.chkDelete.setChecked(false);
            }
        } else {
            productViewHolder.chkDelete.setVisibility(View.GONE);
        }

    }

    class ProductViewHolder extends NormalViewHolder {
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;
        @BindView(R.id.txtInformation)
        TextView txtInformation;
        @BindView(R.id.txtCreatedDate)
        TextView txtCreatedDate;
        @BindView(R.id.chkDelete)
        CheckBox chkDelete;
//        @BindView(R.id.shimmer_loading)
//        ShimmerFrameLayout shimmerLoading;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            chkDelete.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> setSelectedItem(getAdapterPosition(), isChecked));
        }
    }


    private void initProductData(boolean loadingProgress) {
        DataPlaceHolder placeHolder = new DataPlaceHolder();
        placeHolder.setLoadingProgress(loadingProgress);
    }

    private static class DataPlaceHolder {
        private List<ProductDto> listProducts;
        private boolean loadingProgress;

        public List<ProductDto> getListProducts() {
            return listProducts;
        }

        public void setListProducts(List<ProductDto> listProducts) {
            this.listProducts = listProducts;
        }

        public boolean isLoadingProgress() {
            return loadingProgress;
        }

        public void setLoadingProgress(boolean loadingProgress) {
            this.loadingProgress = loadingProgress;
        }
    }
}
