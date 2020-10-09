package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductCommentAdapter extends EndlessLoadingRecyclerViewAdapter {
    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductCommentAdapter(Context context) {
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
        View view = getInflater().inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            CommentDto commentDto = getItem(position, CommentDto.class);

            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.txtUserName.setText(
                    commentDto.getCustomerDto().getFirstName() + " " + commentDto.getCustomerDto().getLastName());
            commentViewHolder.txtComment.setText(commentDto.getContent());
            commentViewHolder.txtCommentDate.setText(commentDto.getCommentDate());
            Glide.with(holder.itemView.getContext())
                    .load(commentDto.getCustomerDto().getAvatarUrl())
                    .error(R.drawable.avatar_placeholder)
                    .into(commentViewHolder.imgUserComment);
            commentViewHolder.btnDelete.setVisibility(View.GONE);
        }
    }

    class CommentViewHolder extends NormalViewHolder {
        @BindView(R.id.imgUserComment)
        ImageView imgUserComment;
        @BindView(R.id.txtUserNameCommnet)
        TextView txtUserName;
        @BindView(R.id.txtComment)
        TextView txtComment;
        @BindView(R.id.txtCommentDate)
        TextView txtCommentDate;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnLongClickListener(v -> {
                btnDelete.setVisibility(View.VISIBLE);
                return true;
            });

            btnDelete.setOnClickListener(v -> itemClickListener.onClick(v, getAdapterPosition(), false));
        }
    }

}
