package com.teamadr.ecommerceapp.view.add_product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.ProductTypeAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.TrademarkAdapter;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.event_bus.NewProductEvent;
import com.teamadr.ecommerceapp.model.request.product.NewProduct;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.product.NewProductPresenter;
import com.teamadr.ecommerceapp.presenter.product.NewProductPresenterImpl;
import com.teamadr.ecommerceapp.utils.UserAuth;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewProductActivity extends AppCompatActivity implements NewProductView, View.OnClickListener {
    @BindView(R.id.edtProductName)
    EditText edtProductName;
    @BindView(R.id.edtProductPrice)
    EditText edtProductPrice;
    @BindView(R.id.txtCountOptionProduct)
    TextView txtCountOptionProduct;
    @BindView(R.id.btnDesc)
    Button btnDesc;
    @BindView(R.id.btnAsc)
    Button btnAsc;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.spnTrademark)
    Spinner spnTrademark;
    @BindView(R.id.spnProductType)
    Spinner spnProductType;
    @BindView(R.id.edtInformationProduct)
    EditText edtInformationProduct;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.imgPickSmallImage)
    ImageView imgPickSmallImage;
    @BindView(R.id.imgPickBigImage)
    ImageView imgPickBigImage;

    private NewProductPresenter newProductPresenter;
    private NewProduct newProduct;

    private TrademarkAdapter trademarkAdapter;
    private ProductTypeAdapter productTypeAdapter;
    private List<TrademarkDto> listTrademark;
    private List<ProductTypeDto> listProductTypes;
    private int count = 0;

    private StorageReference mStorageRef;

    public static final int REQUEST_SMALL_IMAGE = 0;
    public static final int REQUEST_BIG_IMAGE = 1;

    private String bigImageUrl;
    private String smallImageUrl;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Thêm sản phẩm");
        }

        initPresenter();
        initData();
        initView();
    }

    private void initPresenter() {
        newProductPresenter = new NewProductPresenterImpl(this, this);
    }

    private void initData() {
        listTrademark = new ArrayList<>();
        listProductTypes = new ArrayList<>();
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);
        newProductPresenter.refreshTrademark();
        newProductPresenter.refreshProductType();
        trademarkAdapter = new TrademarkAdapter(this, listTrademark);
        spnTrademark.setAdapter(trademarkAdapter);

        productTypeAdapter = new ProductTypeAdapter(this, listProductTypes);
        spnProductType.setAdapter(productTypeAdapter);

        btnAdd.setOnClickListener(this);
        btnAsc.setOnClickListener(this);
        btnDesc.setOnClickListener(this);
        imgPickBigImage.setOnClickListener(this);
        imgPickSmallImage.setOnClickListener(this);
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
    public void refreshTrademark(List<TrademarkDto> list) {
        listTrademark.clear();
        listTrademark.addAll(list);

        spnTrademark.setSelection(0);

        trademarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshListProductType(List<ProductTypeDto> productTypeDtos) {
        listProductTypes.clear();
        listProductTypes.addAll(productTypeDtos);
        spnProductType.setSelection(0);
        productTypeAdapter.notifyDataSetChanged();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAsc: {
                count++;
                txtCountOptionProduct.setText(String.valueOf(count));
            }
            break;

            case R.id.btnDesc: {
                count--;
                if (count <= 0) {
                    count = 0;
                }
                txtCountOptionProduct.setText(String.valueOf(count));
            }
            break;

            case R.id.btnAdd: {
                this.newProduct = new NewProduct();

                this.newProduct.setName(edtProductName.getText().toString());
                this.newProduct.setPrice(Integer.parseInt(edtProductPrice.getText().toString()));
                this.newProduct.setInformation(edtInformationProduct.getText().toString());
                this.newProduct.setSmallImageUrl(smallImageUrl);
                this.newProduct.setBigImageUrl(bigImageUrl);
                this.newProduct.setCount(Integer.parseInt(txtCountOptionProduct.getText().toString()));
                this.newProduct.setAdminId(UserAuth.getUserId(this));

                int trademarkPos = spnTrademark.getSelectedItemPosition();
                this.newProduct.setTrademarkId(listTrademark.get(trademarkPos).getId());

                int productTypePos = spnProductType.getSelectedItemPosition();
                this.newProduct.setProductTypeId(listProductTypes.get(productTypePos).getId());

                newProductPresenter.addProduct(newProduct);
            }
            break;

            case R.id.imgPickBigImage: {
                FishBun.with(this).setImageAdapter(new GlideAdapter())
                        .setRequestCode(REQUEST_BIG_IMAGE)
                        .startAlbum();
            }
            break;
            case R.id.imgPickSmallImage: {
                FishBun.with(this).setImageAdapter(new GlideAdapter())
                        .setRequestCode(REQUEST_SMALL_IMAGE)
                        .startAlbum();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case REQUEST_BIG_IMAGE: {
                if (resultCode == RESULT_OK) {
                    showLoadingProgress();
                    ArrayList<Uri> path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Glide.with(this).load(path.get(0)).into(imgPickBigImage);

                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    long timestamp = new Date().getTime();
                    String filePath = "images/products/bigImage" + timestamp + ".jpg";
                    StorageReference storageReference = mStorageRef.child(filePath);

                    storageReference.putFile(path.get(0)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            hideLoadingProgress();
                            bigImageUrl = "https://storage.googleapis.com/ecommerceapp-7b1b2.appspot.com/" + filePath;
                        }
                    });
                }
            }
            break;

            case REQUEST_SMALL_IMAGE: {
                if (resultCode == RESULT_OK) {
                    showLoadingProgress();
                    ArrayList<Uri> path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Glide.with(this).load(path.get(0)).into(imgPickSmallImage);

                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    long timestamp = new Date().getTime();
                    String filePath = "images/products/smallImage" + timestamp + ".jpg";
                    StorageReference storageReference = mStorageRef.child(filePath);
                    storageReference.putFile(path.get(0)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            hideLoadingProgress();
                            smallImageUrl = "https://storage.googleapis.com/ecommerceapp-7b1b2.appspot.com/" + filePath;
                        }
                    });
                }
            }
            break;

        }
    }

}
