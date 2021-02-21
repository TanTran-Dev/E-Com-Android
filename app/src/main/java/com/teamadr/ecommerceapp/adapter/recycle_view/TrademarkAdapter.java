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
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrademarkAdapter extends RecyclerViewAdapter {

    public TrademarkAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_trademark, parent, false);
        return new TrademarkViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        Context context = getContext();
        TrademarkViewHolder trademarkViewHolder = (TrademarkViewHolder) holder;

        TrademarkDto trademarkDto = getItem(position, TrademarkDto.class);
        if (trademarkDto != null) {
            Glide.with(context).load(trademarkDto.getImageUrl())
                    .into(trademarkViewHolder.imgTrademark);
        }

        if (trademarkDto != null) {
            trademarkViewHolder.txtTrademark.setText(trademarkDto.getName());
        }

    }

    class TrademarkViewHolder extends NormalViewHolder {
        @BindView(R.id.img_trademark)
        ImageView imgTrademark;
        @BindView(R.id.txt_trademark_name)
        TextView txtTrademark;

        public TrademarkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
