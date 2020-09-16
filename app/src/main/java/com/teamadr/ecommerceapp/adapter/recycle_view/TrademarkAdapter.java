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
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrademarkAdapter extends ArrayAdapter <TrademarkDto>{
    private Context context;
    private int resource;
    private List<TrademarkDto> list;
    private String defaultItem;

    public TrademarkAdapter(@NonNull Context context, @NonNull List<TrademarkDto> objects) {
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

    private View initView(int position, View convertView, ViewGroup parent){
        TrademarkViewHolder trademarkViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_trademark, parent, false);
            trademarkViewHolder = new TrademarkViewHolder();
            trademarkViewHolder.imgTrademark = convertView.findViewById(R.id.img_trademark);
            trademarkViewHolder.txtTrademark = convertView.findViewById(R.id.txt_trademark_name);


            convertView.setTag(trademarkViewHolder);
        }else {
            trademarkViewHolder = (TrademarkViewHolder) convertView.getTag();
        }

        TrademarkDto trademarkDto = getItem(position);
        if (trademarkDto != null) {
            Glide.with(context).load(trademarkDto.getImageUrl())
                    .into(trademarkViewHolder.imgTrademark);
        }

        if (trademarkDto != null) {
            trademarkViewHolder.txtTrademark.setText(trademarkDto.getName());
        }

        return convertView;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    static class TrademarkViewHolder {
        ImageView imgTrademark;
        TextView txtTrademark;
    }
}
