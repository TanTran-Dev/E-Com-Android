package com.teamadr.ecommerceapp.adapter.recycle_view;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductsAdapter extends EndlessLoadingRecyclerViewAdapter {
    public static final int VIEW_TYPE_PRODUCT_TYPE = 1;
    public static final int VIEW_TYPE_TRADEMARK = 2;
    public static final int VIEW_TYPE_PRODUCTS_LABEL = 3;

    public static final int VIEW_TYPE_PRODUCT_TYPE_POSITION = 0;
    public static final int VIEW_TYPE_TRADEMARK_POSITION = 1;
    public static final int VIEW_TYPE_PRODUCTS_LABEL_POSITION = 2;

    private SparseArray<DataHolder<?>> mapDataHolder;
    private SparseArray<DataHolder<?>> mapDataHolderPosition;

    public ListProductsAdapter(Context context) {
        super(context, false);
        initDataHolder();
    }

    private void initDataHolder() {
        ListProductTypeDataHolder productTypeDataHolder = new ListProductTypeDataHolder();
        ListTrademarkDataHolder trademarkDataHolder = new ListTrademarkDataHolder();
        ListProductsLabelDataHolder productDataHolder = new ListProductsLabelDataHolder();

        mapDataHolder = new SparseArray<>();
        mapDataHolder.put(VIEW_TYPE_PRODUCT_TYPE, productTypeDataHolder);
        mapDataHolder.put(VIEW_TYPE_TRADEMARK, trademarkDataHolder);
        mapDataHolder.put(VIEW_TYPE_PRODUCTS_LABEL, productDataHolder);

        mapDataHolderPosition = new SparseArray<>();
        mapDataHolderPosition.put(VIEW_TYPE_PRODUCT_TYPE_POSITION, productTypeDataHolder);
        mapDataHolderPosition.put(VIEW_TYPE_TRADEMARK_POSITION, trademarkDataHolder);
        mapDataHolderPosition.put(VIEW_TYPE_PRODUCTS_LABEL_POSITION, productDataHolder);
    }

    @Override
    public void addModel(int index, Object model, int viewType, boolean isScroll, boolean isUpdate) {
        super.addModel(index, model, viewType, isScroll, isUpdate);
        if (model instanceof DataHolder) {
            ((DataHolder) model).setAttached(true);
        }
    }

    @Override
    public ModelWrapper removeModel(int index) {
        ModelWrapper modelWrapper = super.removeModel(index);
        if (modelWrapper.getModel() instanceof DataHolder) {
            ((DataHolder) modelWrapper.getModel()).setAttached(false);
        }
        return modelWrapper;
    }

    public boolean isDataHolderLoadingComplete() {
        for (int i = 0; i < mapDataHolder.size(); i++) {
            int key = mapDataHolder.keyAt(i);
            DataHolder<?> dataHolder = mapDataHolder.get(key);
            if (dataHolder.isLoadingProgress()) {
                return false;
            }
        }
        return true;
    }

    private <T extends DataHolder<?>> T getDataHolder(int viewType, Class<T> type) {
        return type.cast(mapDataHolder.get(viewType));
    }

    private void addDataHolder(int viewType, DataHolder<?> dataHolder) {
        int position = dataHolder.getDefaultPosition();
        int aboveDataHolderPosition = -1;
        for (int i = position - 1; i >= 0; i--) {
            DataHolder aboveDataHolder = mapDataHolderPosition.get(i);
            if (aboveDataHolder != null && aboveDataHolder.isAttached()) {
                aboveDataHolderPosition = i;
                break;
            }
        }
        if (aboveDataHolderPosition >= 0) {
            position = aboveDataHolderPosition + 1;
        } else {
            position = 0;
        }

        if (getItemCount() > position) {
            addModel(position, dataHolder, viewType, false);
        } else {
            addModel(dataHolder, viewType, false);
        }
    }

    private int getDataHolderCurrentPosition(int viewType) {
        DataHolder<?> dataHolder = getDataHolder(viewType, DataHolder.class);
        for (int i = 0; i < getItemCount(); i++) {
            Object data = getItem(i, Object.class);
            if (data == dataHolder) {
                return i;
            }
        }
        return -1;
    }

    public void showRefreshingProgress(int viewType) {
        DataHolder<?> dataHolder = getDataHolder(viewType, DataHolder.class);
        if (dataHolder != null && !dataHolder.isLoadingProgress()) {
            dataHolder.setLoadingProgress(true);
            dataHolder.setItems(null);

            if (dataHolder.isAttached()) {
                notifyItemChanged(getDataHolderCurrentPosition(viewType));
            } else {
                addDataHolder(viewType, dataHolder);
            }

            if (dataHolder instanceof ListProductsLabelDataHolder) {
                int startPos = getDataHolderCurrentPosition(viewType);
                int totalItem = getItemCount();
                List removeItems = getListWrapperModels().subList(startPos + 1, totalItem);
                int totalItemRemoved = removeItems.size();
                removeItems.clear();
                if (totalItemRemoved > 0) {
                    notifyItemRangeRemoved(startPos + 1, totalItemRemoved);
                }
            }
        }
    }

    public <T> void refreshDataHolder(int viewType, List<T> items) {
        DataHolder<T> dataHolder;
        try {
            dataHolder = getDataHolder(viewType, DataHolder.class);
        } catch (ClassCastException e) {
            dataHolder = null;
        }
        if (dataHolder != null) {
            dataHolder.setLoadingProgress(false);

            if (dataHolder instanceof ListProductsLabelDataHolder) {
                if (items != null && !items.isEmpty()) {
                    addModels(items, false);
                }
            } else {
                dataHolder.setItems(items);
            }

            if (dataHolder.isAttached()) {
                if (items == null || items.isEmpty()) {
                    removeModel(getDataHolderCurrentPosition(viewType));
                } else {
                    notifyItemChanged(getDataHolderCurrentPosition(viewType));
                }
            } else {
                addDataHolder(viewType, dataHolder);
            }
        }
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
        View view = getInflater().inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    private RecyclerView.ViewHolder initListProductType(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_list_product_type, parent, false);
        return new ListProductTypeViewHolder(parent.getContext(), view);
    }


    private RecyclerView.ViewHolder initTrademarkViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_list_trademark, parent, false);
        return new ListTrademarkViewHolder(getContext(), view);
    }

    private RecyclerView.ViewHolder initProductsLabelViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_products_label, parent, false);
        return new ProductsLabelViewHolder(view);
    }


    @Override
    protected RecyclerView.ViewHolder solvedOnCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder result;
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                result = initLoadingViewHolder(parent);
            }
            break;

            case VIEW_TYPE_PRODUCT_TYPE: {
                result = initListProductType(parent);
            }
            break;

            case VIEW_TYPE_TRADEMARK: {
                result = initTrademarkViewHolder(parent);
            }
            break;

            case VIEW_TYPE_PRODUCTS_LABEL: {
                result = initProductsLabelViewHolder(parent);
            }
            break;

            default: {
                result = initNormalViewHolder(parent);
            }
            break;
        }
        return result;
    }

    @Override
    protected void solvedOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                bindLoadingViewHolder((LoadingViewHolder) viewHolder, position);
            }
            break;

            case VIEW_TYPE_PRODUCT_TYPE: {
                bindListProductTypeViewHolder((ListProductTypeViewHolder) viewHolder, position);
            }
            break;

            case VIEW_TYPE_TRADEMARK: {
                bindListTrademarkViewHolder((ListTrademarkViewHolder) viewHolder, position);
            }
            break;

            case VIEW_TYPE_PRODUCTS_LABEL:{
                bindListProductsLabelViewHolder((ProductsLabelViewHolder) viewHolder, position);
            }
            break;

            default: {
                bindNormalViewHolder((NormalViewHolder) viewHolder, position);
            }
            break;
        }
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        ProductDto productDto = getItem(position, ProductDto.class);

        Glide.with(viewHolder.itemView.getContext()).load(productDto.getSmallImageUrl())
                .error(R.drawable.logo_placeholder)
                .into(viewHolder.imgProduct);


        viewHolder.txtProductName.setText(productDto.getName());
        viewHolder.txtProductPrice.setText(String.valueOf(productDto.getPrice()));
        viewHolder.txtInformation.setText(productDto.getInformation());
        viewHolder.txtCreatedDate.setText("Ngày tạo: " + productDto.getCreatedDate());


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


    private void bindListProductTypeViewHolder(ListProductTypeViewHolder viewHolder, int position) {
        ListProductTypeDataHolder dataHolder = getItem(position, ListProductTypeDataHolder.class);
        if (dataHolder.isLoadingProgress()) {
//            viewHolder.shimmerLoading.startShimmer();
//            viewHolder.shimmerLoading.setVisibility(View.VISIBLE);
            viewHolder.rcItems.setVisibility(View.GONE);
//            viewHolder.btnViewMore.setVisibility(View.GONE);
        } else {
//            viewHolder.shimmerLoading.stopShimmer();
//            viewHolder.shimmerLoading.setVisibility(View.GONE);
            viewHolder.rcItems.setVisibility(View.VISIBLE);

            if (dataHolder.getItems() != null && !dataHolder.getItems().isEmpty()) {
                viewHolder.refresh(dataHolder.getItems());
            }
        }
    }

    private void bindListTrademarkViewHolder(ListTrademarkViewHolder viewHolder, int position) {
        ListTrademarkDataHolder dataHolder = getItem(position, ListTrademarkDataHolder.class);
        if (dataHolder.isLoadingProgress()) {
//            viewHolder.shimmerLoading.startShimmer();
//            viewHolder.shimmerLoading.setVisibility(View.VISIBLE);
            viewHolder.rcItems.setVisibility(View.GONE);
//            viewHolder.btnViewMore.setVisibility(View.GONE);
        } else {
//            viewHolder.shimmerLoading.stopShimmer();
//            viewHolder.shimmerLoading.setVisibility(View.GONE);
            viewHolder.rcItems.setVisibility(View.VISIBLE);

            if (dataHolder.getItems() != null && !dataHolder.getItems().isEmpty()) {
                viewHolder.refresh(dataHolder.getItems());
            }
        }
    }

    private void bindListProductsLabelViewHolder(ProductsLabelViewHolder viewHolder, int position){

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

    class ProductsLabelViewHolder extends NormalViewHolder {
        @BindView(R.id.txt_title_label)
        TextView txtLabel;

        public ProductsLabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ListProductTypeViewHolder extends NormalViewHolder implements OnItemClickListener {
        @BindView(R.id.rc_items)
        RecyclerView rcItems;

        ProductTypeAdapter productTypeAdapter;

        public ListProductTypeViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rcItems.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));

            productTypeAdapter = new ProductTypeAdapter(context);
            productTypeAdapter.addOnItemClickListener(this);
            rcItems.setAdapter(productTypeAdapter);

        }

        public void refresh(List<ProductTypeDto> productTypes) {
            productTypeAdapter.refresh(productTypes);
        }

        @Override
        public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder,
                                int viewType, int position) {

        }
    }

    class ListTrademarkViewHolder extends NormalViewHolder implements OnItemClickListener {
        @BindView(R.id.rc_items)
        RecyclerView rcItems;
        TrademarkAdapter trademarkAdapter;

        public ListTrademarkViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rcItems.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));

            trademarkAdapter = new TrademarkAdapter(context);
            trademarkAdapter.addOnItemClickListener(this);
            rcItems.setAdapter(trademarkAdapter);
        }

        public void refresh(List<TrademarkDto> trademarkDtos) {
            trademarkAdapter.refresh(trademarkDtos);
        }

        @Override
        public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

        }
    }

    private static class DataHolder<T> {
        private List<T> items;
        private boolean loadingProgress;
        private int viewType;
        private boolean attached;
        private int defaultPosition;

        DataHolder(int viewType, int defaultPosition) {
            this.viewType = viewType;
            this.defaultPosition = defaultPosition;
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items;
        }

        void setLoadingProgress(boolean isLoading) {
            this.loadingProgress = isLoading;
        }

        boolean isLoadingProgress() {
            return loadingProgress;
        }

        public int getViewType() {
            return viewType;
        }

        public void setAttached(boolean attached) {
            this.attached = attached;
        }

        public boolean isAttached() {
            return attached;
        }

        public int getDefaultPosition() {
            return defaultPosition;
        }
    }

    private static class ListProductTypeDataHolder extends DataHolder<ProductTypeDto> {
        ListProductTypeDataHolder() {
            super(VIEW_TYPE_PRODUCT_TYPE, VIEW_TYPE_PRODUCT_TYPE_POSITION);
        }
    }

    private static class ListTrademarkDataHolder extends DataHolder<TrademarkDto> {
        ListTrademarkDataHolder() {
            super(VIEW_TYPE_TRADEMARK, VIEW_TYPE_TRADEMARK_POSITION);
        }
    }

    private static class ListProductsLabelDataHolder extends DataHolder<ProductDto> {

        ListProductsLabelDataHolder() {
            super(VIEW_TYPE_PRODUCTS_LABEL, VIEW_TYPE_PRODUCTS_LABEL_POSITION);
        }
    }
}
