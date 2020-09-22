package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderProductAdapter extends EndlessLoadingRecyclerViewAdapter {
    public OrderProductAdapter(Context context) {
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
        View view = getInflater().inflate(R.layout.item_order_product, parent, false);
        return new OrderProductViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        if (holder instanceof OrderProductViewHolder) {
            OrderProductViewHolder viewHolder = (OrderProductViewHolder) holder;
            OrderProductDto orderProductDto = getItem(position, OrderProductDto.class);
            if (orderProductDto.getSalesmanDto().getAvatarUrl() != null){
                Glide.with(viewHolder.itemView.getContext()).load(orderProductDto.getSalesmanDto().getAvatarUrl())
                        .into(viewHolder.imgAvatar);
            }else {
                viewHolder.imgAvatar.setImageResource(R.drawable.avatar_placeholder);
            }

            Glide.with(viewHolder.itemView.getContext()).load(orderProductDto.getProduct().getSmallImageUrl())
                    .into(viewHolder.imgProduct);
            viewHolder.txtAdminName.setText(orderProductDto.getSalesmanDto().getFirstName() + " " +
                    orderProductDto.getSalesmanDto().getLastName());
            viewHolder.txtIdOrder.setText("ID đơn hàng: " + orderProductDto.getId());
            viewHolder.txtProductName.setText(orderProductDto.getProduct().getName());
            viewHolder.txtCount.setText("x " + orderProductDto.getCount());
            viewHolder.txtDeliveryAddress.setText("Địa chỉ nhận: " + orderProductDto.getDeliveryAddress());
            viewHolder.txtProductPrice.setText(String.valueOf(orderProductDto.getProduct().getPrice()));
            viewHolder.txtResultMoney.setText("Thành tiền: " +
                    totalMoney(orderProductDto.getProduct().getPrice(), orderProductDto.getCount()));
            viewHolder.txtOrderDate.setText("Ngày đặt: " + orderProductDto.getOrderDate());

            if (isInSelectedMode()) {
                viewHolder.chkDelete.setVisibility(View.VISIBLE);
                if (isItemSelected(position)) {
                    viewHolder.chkDelete.setChecked(true);
                } else {
                    viewHolder.chkDelete.setChecked(false);
                }
            } else {
                viewHolder.chkDelete.setVisibility(View.GONE);
            }
        }
    }

    class OrderProductViewHolder extends NormalViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.imgProduct)
        CircleImageView imgProduct;
        @BindView(R.id.txtAdminName)
        TextView txtAdminName;
        @BindView(R.id.txtIdOrder)
        TextView txtIdOrder;
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtCount)
        TextView txtCount;
        @BindView(R.id.txtResultMoney)
        TextView txtResultMoney;
        @BindView(R.id.txtOrderDate)
        TextView txtOrderDate;
        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;
        @BindView(R.id.txtDeliveryAddress)
        TextView txtDeliveryAddress;
        @BindView(R.id.chkDelete)
        CheckBox chkDelete;

        public OrderProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            chkDelete.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> setSelectedItem(getAdapterPosition(), isChecked));
        }
    }

    private int totalMoney(int productPrice, int count) {
        return productPrice * count;
    }
}
