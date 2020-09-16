package com.teamadr.ecommerceapp.view.product_detail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.ProductCommentAdapter;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.presenter.product_detail.ProductDetailPresenter;
import com.teamadr.ecommerceapp.presenter.product_detail.ProductDetailPresenterImpl;
import com.teamadr.ecommerceapp.utils.UserAuth;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView,
        View.OnClickListener, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    @BindView(R.id.imgBigImageProduct)
    ImageView imgImageProduct;
    @BindView(R.id.imgSmallImageProduct)
    CircleImageView imgSmallImageProduct;
    @BindView(R.id.txtProductName)
    TextView txtProductName;
    @BindView(R.id.txtProductPrice)
    TextView txtProductPrice;
    @BindView(R.id.imgAvatarAdmin)
    CircleImageView imgAvatarAdmin;
    @BindView(R.id.txtAdminName)
    TextView txtAdminName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtProductInformation)
    TextView txtProductInformation;
    @BindView(R.id.txtProductCount)
    TextView txtProductCount;
    @BindView(R.id.txtProductCountOption)
    TextView txtProductCountOption;
    @BindView(R.id.btnAsc)
    Button btnAsc;
    @BindView(R.id.btnDesc)
    Button btnDesc;
    @BindView(R.id.fabComment)
    FloatingActionButton fabComment;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerLoading;

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.btnAddToCart)
    ImageButton btnAddToCart;
    @BindView(R.id.btnBuyNow)
    Button btnBuyNow;
    @BindView(R.id.ln_option)
    LinearLayout lnOption;
    @BindView(R.id.rl_select_count)
    RelativeLayout rlSelectCount;

    @BindView(R.id.rclComment)
    RecyclerView rclComment;
    @BindView(R.id.scroll_view_detail)
    NestedScrollView scrollViewDetail;

    private CircleImageView imgProductDialog;
    private TextView txtProductNameDialog;
    private TextView txtProductPriceDialog;
    private TextView txtProductCountDialog;
    private TextView txtCountOptionDialog;
    private Button btnAscDialog;
    private Button btnDescDialog;
    private EditText edtDeliveryAddressDialog;
    private TextView txtTotalMoney;
    private Button btnConfirmDialog;

    private ProductDetailPresenter productDetailPresenter;
    private ProductCommentAdapter commentAdapter;
    private int productId;
    private int countOption;
    private int countLimit;
    private LoadingDialog loadingDialog;

    private String shoppingCartId;
    private boolean isHideButton;
    private ProductDto productDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Chi tiết sản phẩm");
        }

        initPresenter();
        initData();
        initView();

        productDetailPresenter.refreshProductDetail(productId);
        productDetailPresenter.refreshComment(productId);
        productDetailPresenter.getShoppingCart(UserAuth.getUserId(this));
    }

    private void initPresenter() {
        productDetailPresenter = new ProductDetailPresenterImpl(this, this);
    }

    private void initData() {
        productId = getIntent().getIntExtra(StringConstant.KEY_PRODUCT_ID, 0);
        isHideButton = getIntent().getBooleanExtra(StringConstant.KEY_HIDE_BUTTON, false);
        countOption = getIntent().getIntExtra(StringConstant.KEY_COUNT_OPTION, 1);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);
        btnAsc.setOnClickListener(this);
        btnDesc.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        fabComment.setOnClickListener(this);
        txtPhone.setOnClickListener(this);


        if (isHideButton) {
            lnOption.setVisibility(View.GONE);
            rlSelectCount.setVisibility(View.GONE);
        } else {
            lnOption.setVisibility(View.VISIBLE);
            rlSelectCount.setVisibility(View.VISIBLE);
        }

        //init comment
        commentAdapter = new ProductCommentAdapter(this);
        commentAdapter.setLoadingMoreListener(this);

        rclComment.setLayoutManager(new LinearLayoutManager(this));
        rclComment.setAdapter(commentAdapter);


        commentAdapter.setItemClickListener((view, position, isLongClick) -> {
            CommentDto commentDto = commentAdapter.getItem(position, CommentDto.class);
            productDetailPresenter.deleteComment(commentDto.getId(), productId);
        });

        if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(this))) {
            fabComment.setVisibility(View.GONE);
            lnOption.setVisibility(View.GONE);
        } else {
            fabComment.setVisibility(View.VISIBLE);
            lnOption.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshProductDetail(ProductDto productDto) {
        this.productDto = productDto;
        Glide.with(this).load(this.productDto.getBigImageUrl())
                .into(imgImageProduct);
        Glide.with(this).load(this.productDto.getSmallImageUrl())
                .into(imgSmallImageProduct);
        Glide.with(this).load(this.productDto.getAdminDto().getAvatarUrl())
                .into(imgAvatarAdmin);
        txtProductCount.setText("Hiện có: " + this.productDto.getCount() + " sản phẩm");
        txtProductName.setText(this.productDto.getName());
        txtProductPrice.setText(this.productDto.getPrice() + " VNĐ");
        txtAdminName.setText(this.productDto.getAdminDto().getFirstName() + " " + this.productDto.getAdminDto().getLastName());

        //set underline for txtPhone
        SpannableString phone = new SpannableString(this.productDto.getAdminDto().getPhone());
        phone.setSpan(new UnderlineSpan(), 0, phone.length(), 0);
        String sub = String.valueOf(phone);
        txtPhone.setText(sub.substring(0, phone.length() - 4) + "****");

        txtProductInformation.setText(this.productDto.getInformation());
        txtProductCountOption.setText(countOption + "");
        countLimit = this.productDto.getCount();
    }

    @Override
    public void showLoadingProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        loadingDialog.dismiss();
    }

    @Override
    public void getIdShoppingCart(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    public void refreshComment(List<CommentDto> list) {
        commentAdapter.refresh(list);
    }

    @Override
    public void addMoreComment(List<CommentDto> list) {
        commentAdapter.addModels(list, false);
    }

    @Override
    public void showLoadingMore(boolean isShow) {
        if (isShow) {
            commentAdapter.showLoadingItem(true);
        } else {
            commentAdapter.hideLoadingItem();
        }
    }

    @Override
    public void disableLoadingMore(boolean disable) {
        commentAdapter.enableLoadingMore(!disable);
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        commentAdapter.enableLoadingMore(enable);
    }

    @Override
    public void showShimmerLoading() {
        fabComment.setVisibility(View.GONE);
        scrollViewDetail.setVisibility(View.GONE);
        shimmerLoading.setVisibility(View.VISIBLE);
        shimmerLoading.startShimmer();
    }

    @Override
    public void hideShimmerLoading() {
        shimmerLoading.stopShimmer();
        shimmerLoading.setVisibility(View.GONE);
        scrollViewDetail.setVisibility(View.VISIBLE);
        if (UserType.CUSTOMER.getLabel().equals(UserAuth.getUserType(this))) {
            fabComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart: {
                productDetailPresenter.setOrder(productId, countOption);
                productDetailPresenter.setShoppingCartId(shoppingCartId);

                productDetailPresenter.addShoppingCartProduct();
            }
            break;

            case R.id.btnBuyNow: {
                showDialogBuyNow();
            }
            break;

            case R.id.btnAsc: {
                countOption++;
                if (countOption <= countLimit) {
                    txtProductCountOption.setText(countOption + "");
                } else {
                    countOption = countLimit;
                }
            }
            break;

            case R.id.btnDesc: {
                countOption--;
                if (countOption >= 1) {
                    txtProductCountOption.setText(countOption + "");
                } else {
                    countOption = 1;
                }
            }
            break;

            case R.id.fabComment: {
                showDialogComment();
            }
            break;

            case R.id.txtPhone: {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + productDto.getAdminDto().getPhone()));
                startActivity(intent);
            }
            break;
        }
    }

    private void showDialogBuyNow() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_buy_now);
        dialog.show();

        imgProductDialog = dialog.findViewById(R.id.imgProduct);
        txtProductNameDialog = dialog.findViewById(R.id.txtProductName);
        txtProductPriceDialog = dialog.findViewById(R.id.txtProductPrice);
        txtProductCountDialog = dialog.findViewById(R.id.txtCountLimit);
        txtCountOptionDialog = dialog.findViewById(R.id.txtProductCountOption);
        btnAscDialog = dialog.findViewById(R.id.btnAsc);
        btnDescDialog = dialog.findViewById(R.id.btnDesc);
        edtDeliveryAddressDialog = dialog.findViewById(R.id.edtDeliveryAddress);
        btnConfirmDialog = dialog.findViewById(R.id.btnConfirm);
        txtTotalMoney = dialog.findViewById(R.id.txtTotalMoney);

        Glide.with(this).load(productDto.getSmallImageUrl())
                .into(imgProductDialog);
        txtProductNameDialog.setText(productDto.getName());
        txtProductPriceDialog.setText("Giá: " + productDto.getPrice());
        txtProductCountDialog.setText("Kho: " + countLimit);
        txtCountOptionDialog.setText(String.valueOf(countOption));
        txtTotalMoney.setText(String.valueOf(totalMoney(productDto.getPrice(), countOption)));

        btnAscDialog.setOnClickListener(v -> {
            countOption++;
            if (countOption <= countLimit) {
                txtCountOptionDialog.setText(countOption + "");
                txtTotalMoney.setText(String.valueOf(totalMoney(productDto.getPrice(), countOption)));
            } else {
                countOption = countLimit;
            }
        });

        btnDescDialog.setOnClickListener(v -> {
            countOption--;
            if (countOption >= 1) {
                txtCountOptionDialog.setText(countOption + "");
                txtTotalMoney.setText(String.valueOf(totalMoney(productDto.getPrice(), countOption)));
            } else {
                countOption = 1;
            }
        });

        productDetailPresenter.getShoppingCart(UserAuth.getUserId(this));

        btnConfirmDialog.setOnClickListener(v -> {
            NewOrderProduct newOrderProduct = new NewOrderProduct();
            newOrderProduct.setDeliveryAddress(edtDeliveryAddressDialog.getText().toString());
            newOrderProduct.setAdminId(productDto.getAdminDto().getId());
            newOrderProduct.setShoppingCartId(shoppingCartId);
            newOrderProduct.setProductId(productId);
            newOrderProduct.setCount(Integer.parseInt(txtCountOptionDialog.getText().toString()));
            newOrderProduct.setCustomerId(UserAuth.getUserId(this));

            productDetailPresenter.createNewOrderProduct(newOrderProduct);
            dialog.dismiss();
        });
    }

    private int totalMoney(int productPrice, int countOption) {
        return productPrice * countOption;
    }

    @Override
    public void onLoadMore() {
        productDetailPresenter.loadMoreComment(productId);
    }

    private void showDialogComment() {
        final FlatDialog flatDialog = new FlatDialog(this);
        flatDialog.setTitle("Bình luận")
                .setLargeTextFieldHint("Nhập bình luận...")
                .setFirstButtonText("Bình luận")
                .setSecondButtonText("Huỷ")
                .setBackgroundColor(Color.parseColor("#F3F9A7"))
                .setLargeTextFieldHintColor(Color.parseColor("#000000"))
                .setLargeTextFieldBorderColor(Color.parseColor("#000000"))
                .setLargeTextFieldTextColor(Color.parseColor("#000000"))
                .setTitleColor(Color.parseColor("#000000"))
                .setFirstButtonColor(Color.parseColor("#74ebd5"))
                .setSecondButtonColor(Color.parseColor("#f7797d"))
                .setFirstButtonTextColor(Color.parseColor("#000000"))
                .setSecondButtonTextColor(Color.parseColor("#000000"))
                .withFirstButtonListner(view -> {
                    String comment = flatDialog.getFirstTextField();
                    if (comment.isEmpty()) {
                        Toast.makeText(ProductDetailActivity.this, "Nhập bình luận", Toast.LENGTH_SHORT).show();
                    } else {
                        NewComment newComment = new NewComment();
                        newComment.setContent(comment);
                        newComment.setCustomerId(UserAuth.getUserId(ProductDetailActivity.this));
                        newComment.setProductId(productId);

                        productDetailPresenter.createComment(newComment, productId);
                        commentAdapter.notifyDataSetChanged();
                        flatDialog.dismiss();
                    }
                })
                .withSecondButtonListner(view -> flatDialog.dismiss())
                .show();
    }
}
