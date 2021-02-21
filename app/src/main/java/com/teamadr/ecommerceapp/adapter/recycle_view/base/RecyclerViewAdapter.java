package com.teamadr.ecommerceapp.adapter.recycle_view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static String TAG = "RecyclerViewAdapter";
    public static final int VIEW_TYPE_NORMAL = 0;

    public static AtomicInteger idGenerator = new AtomicInteger();

    private List<ModelWrapper> listWrapperModels;
    private List<ModelWrapper> listWrapperModelsBackup;
    private Map<String, List<ModelWrapper>> filterCache;

    private LayoutInflater inflater;
    private List<OnItemClickListener> onItemClickListeners;
    private OnItemTouchChangedListener onItemTouchChangeListener;
    private OnItemSelectionChangedListener onItemSelectionChangeListener;
    private boolean selectedMode;
    private boolean filteringMode;
    private RecyclerView recyclerView;
    private Context context;

    public RecyclerViewAdapter(Context context, boolean enableSelectedMode) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listWrapperModels = new ArrayList<>();
        this.filterCache = new HashMap<>();
        this.onItemClickListeners = new ArrayList<>(1);
        setSelectedMode(enableSelectedMode);
    }

    protected DiffUtilCallBack initDiffUtilCallback(List<ModelWrapper> oldItems, List<ModelWrapper> newItems) {
        return new DiffUtilCallBack(oldItems, newItems);
    }

    public int getItemPosition(Object item) {
        for (int i = 0; i < listWrapperModels.size(); i++) {
            ModelWrapper modelWrapper = listWrapperModels.get(i);
            if (modelWrapper != null && checkItemEquals(modelWrapper.getModel(), item)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkItemEquals(Object item1, Object item2) {
        return Objects.equals(item1, item2);
    }

    public void enableFilteringMode(boolean enable) {
        if (enable && !filteringMode) {
            filterCache.put("", listWrapperModels);
            filteringMode = true;
        } else if (!enable && filteringMode) {
//            Log.i("FILTER", "clear filter cache: " + filterCache.size());
            this.listWrapperModels = filterCache.get("");
            filterCache.clear();
            filteringMode = false;
        }
    }

    public boolean isFilteringMode() {
        return filteringMode;
    }

    public void performFiltering(String queryString) {
        if (!filteringMode) {
            enableFilteringMode(true);
        }

        List<ModelWrapper> results;
        if (queryString == null || queryString.isEmpty()) {
            results = filterCache.get("");
//            Log.i("FILTER", "found: '" + queryString + "' (" + results.size() + ")");
        } else {
            results = filterCache.get(queryString);
            if (results == null) {
//                Log.i("FILTER", "not found: '" + queryString + "'");
                String previousQueryString = queryString.substring(0, queryString.length() - 1);
                List<ModelWrapper> items = findNearestFilterResults(previousQueryString);
                results = new ArrayList<>();
                for (ModelWrapper model : items) {
                    if (model != null && checkFilterCondition(model, queryString)) {
                        results.add(model);
                    }
                }
                filterCache.put(queryString, results);
//                Log.i("FILTER", "do filter: '" + queryString + "' (" + results.size() + ")");
            } else {
//                Log.i("FILTER", "found: '" + queryString + "' (" + results.size() + ")");
            }
        }

        setListWrapperModels(results);
        notifyDataSetChanged();
    }

    public int getRealModelPosition(ModelWrapper model) {
        List<ModelWrapper> modelWrappers;
        if (filteringMode) {
            modelWrappers = filterCache.get("");
        } else {
            modelWrappers = listWrapperModels;
        }
        if (modelWrappers == null) {
            return -1;
        } else {
            return modelWrappers.indexOf(model);
        }
    }

    public <T> T getRealItem(int realPosition, Class<T> type) {
        if (filteringMode) {
            List<ModelWrapper> modelWrappers = filterCache.get("");
            if (modelWrappers == null) {
                return null;
            }
            return type.cast(modelWrappers.get(realPosition).model);
        } else {
            return getItem(realPosition, type);
        }
    }

    private List<ModelWrapper> findNearestFilterResults(String queryString) {
        List<ModelWrapper> results;
        if (queryString == null || queryString.isEmpty()) {
            results = filterCache.get("");
//            Log.i("FILTER", "found: '" + queryString + "' (" + results.size() + ")");
        } else {
            results = filterCache.get(queryString);
            if (results == null) {
//                Log.i("FILTER", "not found: '" + queryString + "'");
                String previousQueryString = queryString.substring(0, queryString.length() - 1);
                results = findNearestFilterResults(previousQueryString);
            } else {
//                Log.i("FILTER", "found: '" + queryString + "' (" + results.size() + ")");
            }
        }
        return results;
    }

    public boolean checkFilterCondition(ModelWrapper model, String queryString) {
        return model.model.equals(queryString);
    }

    public void backup(boolean clone) {
        listWrapperModelsBackup = new ArrayList<>(listWrapperModels.size());
        try {
            for (ModelWrapper modelWrapper : listWrapperModels) {
                listWrapperModelsBackup.add(clone ? modelWrapper.clone() : modelWrapper);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void recoverBackup() {
        this.listWrapperModels = listWrapperModelsBackup;
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = null;
        this.context = null;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListeners.add(onItemClickListener);
    }

    private void notifyItemClickListener(RecyclerView.Adapter adapter,
                                         RecyclerView.ViewHolder viewHolder,
                                         int viewType,
                                         int position) {
        for (OnItemClickListener onItemClickListener : onItemClickListeners) {
            onItemClickListener.onItemClick(adapter, viewHolder, viewType, position);
        }
    }

    public void removeOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListeners.remove(onItemClickListener);
    }

    public void setOnItemTouchChangeListener(OnItemTouchChangedListener onItemTouchChangeListener) {
        this.onItemTouchChangeListener = onItemTouchChangeListener;
    }

    public void setOnItemSelectionChangeListener(OnItemSelectionChangedListener onItemSelectionChangeListener) {
        this.onItemSelectionChangeListener = onItemSelectionChangeListener;
    }

    public void clear() {
        int itemCount = getItemCount();
        listWrapperModels.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public <T> void refresh(List<T> models) {
        int itemCount = getItemCount();
        listWrapperModels.clear();
        notifyItemRangeRemoved(0, itemCount);
        addModels(models, false);
    }

    public <T> void addModels(List<T> listModels, boolean isScroll) {
        addModels(listModels, VIEW_TYPE_NORMAL, isScroll);
    }

    public <T> void addModels(List<T> listModels, int viewType, boolean isScroll) {
        addModels(listModels, 0, listModels.size() - 1, viewType, isScroll, true);
    }

    public <T> void addModels(List<T> listModels, int viewType, boolean isScroll, boolean isUpdate) {
        addModels(listModels, 0, listModels.size() - 1, viewType, isScroll, isUpdate);
    }

    public <T> void addModels(List<T> listModels, int fromIndex, int toIndex, int viewType, boolean isScroll, boolean isUpdate) {
        int startInsertedPosition = getItemCount();
        int endInsertedPosition = startInsertedPosition + listModels.size();
        for (int i = fromIndex; i <= toIndex; i++) {
            addModel(listModels.get(i), viewType, false, false);
        }
        if (isUpdate) {
            notifyItemRangeInserted(startInsertedPosition, endInsertedPosition);
        }
        if (isScroll) {
            getRecyclerView().scrollToPosition(listWrapperModels.size() - 1);
        }
    }

    public void addModel(Object model, boolean isScroll) {
        addModel(listWrapperModels.size(), model, isScroll);
    }

    public void addModel(Object model, boolean isScroll, boolean isUpdate) {
        addModel(listWrapperModels.size(), model, isScroll, isUpdate);
    }

    public void addModel(Object model, int viewType, boolean isScroll) {
        addModel(listWrapperModels.size(), model, viewType, isScroll);
    }

    public void addModel(Object model, int viewType, boolean isScroll, boolean isUpdate) {
        addModel(listWrapperModels.size(), model, viewType, isScroll, isUpdate);
    }

    public void addModel(int index, Object model, boolean isScroll) {
        addModel(index, model, VIEW_TYPE_NORMAL, isScroll, true);
    }

    public void addModel(int index, Object model, boolean isScroll, boolean isUpdate) {
        addModel(index, model, VIEW_TYPE_NORMAL, isScroll, isUpdate);
    }

    public void addModel(int index, Object model, int viewType, boolean isScroll) {
        addModel(index, model, viewType, isScroll, true);
    }

    public void updateModel(int position, Object model, boolean isScroll) {
        getListWrapperModels().get(position).model = model;
        notifyItemChanged(position);
        if (isScroll) {
            getRecyclerView().scrollToPosition(position);
        }
    }

    public void addModel(int index, Object model, int viewType, boolean isScroll, boolean isUpdate) {
        ModelWrapper modelWrapper = new ModelWrapper(model, viewType);
        this.listWrapperModels.add(index, modelWrapper);
        if (isUpdate) {
            notifyItemInserted(index);
        }
        if (isScroll) {
            getRecyclerView().scrollToPosition(index);
        }
    }

    public ModelWrapper removeModel(int index) {
        return removeModel(index, true);
    }

    public ModelWrapper removeModel(int index, boolean isUpdate) {
        ModelWrapper result = this.listWrapperModels.remove(index);
        if (isUpdate) {
            notifyItemRemoved(index);
        }
        return result;
    }

    public void removeModels(List<Integer> ascPositions) {
        for (int i = ascPositions.size() - 1; i >= 0; i--) {
            int position = ascPositions.get(i);
            try {
                listWrapperModels.remove(position);
                notifyItemRemoved(position);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

    public void setSelectedMode(boolean isSelected) {
        if (this.selectedMode != isSelected) {
            if (this.selectedMode) {
                deSelectAllItems(null);
            }
            notifyItemRangeChanged(0, getItemCount());
        }
        selectedMode = isSelected;
    }

    public boolean isInSelectedMode() {
        return selectedMode;
    }

    public void setSelectedItem(int position, boolean isSelected) {
        if (selectedMode && position >= 0 && position < listWrapperModels.size()) {
            ModelWrapper modelWrapper = listWrapperModels.get(position);
            if (modelWrapper.isSelected != isSelected) {
                listWrapperModels.get(position).isSelected = isSelected;
                notifyItemChanged(position);
            }
        }
    }

    public void deSelectAllItems(OnEachUnSelectedItem onEachUnSelectedItem) {
        int size = listWrapperModels.size();
        for (int i = 0; i < size; i++) {
            ModelWrapper modelWrapper = listWrapperModels.get(i);
            if (onEachUnSelectedItem != null && !modelWrapper.isSelected) {
                onEachUnSelectedItem.onEachUnselectedItem(modelWrapper);
            }
            modelWrapper.isSelected = false;
        }
    }

    public interface OnEachUnSelectedItem {
        void onEachUnselectedItem(ModelWrapper modelWrapper);
    }

    public void removeAllSelectedItems() {
        if (selectedMode) {
            List<ModelWrapper> listItemLeft = new ArrayList<>();
            deSelectAllItems(listItemLeft::add);

            DiffUtil.DiffResult diffResult = DiffUtil
                    .calculateDiff(initDiffUtilCallback(listWrapperModels, listItemLeft));

            listWrapperModels = listItemLeft;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public boolean isItemSelected(int position) {
        return selectedMode && position >= 0 &&
                position < listWrapperModels.size() &&
                listWrapperModels.get(position).isSelected;
    }

    public <T> List<T> getSelectedItemModels(Class<T> type) {
        List<T> result = new ArrayList<>();
        forEachSelectedItemModels(type, (index, model) -> result.add(model));
        return result;
    }

    public <T> void forEachSelectedItemModels(Class<T> type, OnEachSelectedModel<T> onEachSelectedModel) {
        for (int i = 0; i < listWrapperModels.size(); i++) {
            ModelWrapper modelWrapper = listWrapperModels.get(i);
            Object model = modelWrapper.model;
            if (modelWrapper.isSelected && model != null) {
                if (model.getClass().equals(type)) {
                    onEachSelectedModel.onEachSelectedModel(i, type.cast(modelWrapper.model));
                }
            }
        }
    }

    public <T> void forEachModels(Class<T> type, OnEachModel<T> onEachModel) {
        for (ModelWrapper modelWrapper : listWrapperModels) {
            Object model = modelWrapper.model;
            if (model != null && model.getClass().equals(type)) {
                onEachModel.onEachModel(type.cast(model));
            }
        }
    }

    public void forEachItem(OnEachItem onEachItem) {
        for (ModelWrapper modelWrapper : listWrapperModels) {
            onEachItem.onEachItem(modelWrapper);
        }
    }

    public interface OnEachItem {
        void onEachItem(ModelWrapper modelWrapper);
    }

    public interface OnEachModel<T> {
        void onEachModel(T model);
    }

    public interface OnEachSelectedModel<T> {
        void onEachSelectedModel(int position, T model);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public <T> T getItem(int position, Class<T> classType) {
        return classType.cast(listWrapperModels.get(position).model);
    }

    public ModelWrapper getModel(int position) {
        return listWrapperModels.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return listWrapperModels.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final RecyclerView.ViewHolder viewHolder = solvedOnCreateViewHolder(parent, viewType);
        setClickStateBackground(viewHolder.itemView, viewType, false);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (onItemTouchChangeListener != null) {
                            onItemTouchChangeListener.onItemPress(viewHolder, viewType);
                        }
                    }
                    break;

                    case MotionEvent.ACTION_CANCEL: {
                        if (onItemTouchChangeListener != null) {
                            onItemTouchChangeListener.onItemRelease(viewHolder, viewType);
                        }
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        int itemPosition = getItemPosition(view);
                        if (itemPosition >= 0 && itemPosition < getItemCount()) {
                            setClickStateBackground(view, viewType, false);
                            notifyItemClickListener(RecyclerViewAdapter.this, viewHolder, viewType, itemPosition);
                        }
                    }
                    break;

                    default: {
                        break;
                    }
                }
                return false;
            }
        });
        return viewHolder;
    }

    private int getItemPosition(View view) {
        return recyclerView.getChildLayoutPosition(view);
    }

    protected RecyclerView.ViewHolder solvedOnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            return initNormalViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper modelWrapper = listWrapperModels.get(position);
        int viewType = modelWrapper.viewType;
        if (onItemSelectionChangeListener != null) {
            onItemSelectionChangeListener.onItemSelectionChanged(holder, viewType, modelWrapper.isSelected);
        }
        solvedOnBindViewHolder(holder, viewType, position);
    }

    protected void solvedOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        if (viewType == VIEW_TYPE_NORMAL) {
            bindNormalViewHolder((NormalViewHolder) viewHolder, position);
        }
    }

    protected abstract RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent);

    protected abstract void bindNormalViewHolder(NormalViewHolder holder, int position);

    protected void setClickStateBackground(View view, int viewType, boolean isPress) {

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ModelWrapper> getListWrapperModels() {
        return listWrapperModels;
    }

    public void setListWrapperModels(List<ModelWrapper> listWrapperModels) {
        this.listWrapperModels = listWrapperModels;
    }

    public void clearListSelectedItems() {
        for (ModelWrapper modelWrapper : listWrapperModels) {
            modelWrapper.isSelected = false;
        }
    }

    @Override
    public int getItemCount() {
        return listWrapperModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position);
    }

    public interface OnItemSelectionChangedListener {
        void onItemSelectionChanged(RecyclerView.ViewHolder viewHolder, int viewType, boolean isSelected);
    }

    public interface OnItemTouchChangedListener {
        void onItemPress(RecyclerView.ViewHolder viewHolder, int viewType);

        void onItemRelease(RecyclerView.ViewHolder viewHolder, int viewType);
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ModelWrapper implements Cloneable {
        int id = idGenerator.getAndIncrement();
        Object model;
        int viewType;
        boolean isSelected = false;

        public ModelWrapper(Object model, int viewType) {
            this.model = model;
            this.viewType = viewType;
        }

        public Object getModel() {
            return model;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public int getViewType() {
            return viewType;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ModelWrapper)) {
                return false;
            }
            ModelWrapper modelWrapper = (ModelWrapper) obj;
            if (modelWrapper.viewType != this.viewType) {
                return false;
            }

            if (modelWrapper.isSelected != this.isSelected) {
                return false;
            }

            if (modelWrapper.model == null) {
                return this.model == null;
            } else {
                return modelWrapper.model.equals(this.model);
            }
        }

        @Override
        protected ModelWrapper clone() throws CloneNotSupportedException {
            return (ModelWrapper) super.clone();
        }
    }
}