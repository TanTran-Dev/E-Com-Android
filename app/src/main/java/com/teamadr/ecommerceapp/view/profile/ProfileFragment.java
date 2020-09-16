package com.teamadr.ecommerceapp.view.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.event_bus.UserProfileEvent;
import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.admin.AdminDto;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.presenter.admin_profile.AdminProfilePresenter;
import com.teamadr.ecommerceapp.presenter.admin_profile.AdminProfilePresenterImpl;
import com.teamadr.ecommerceapp.presenter.customer_profile.CustomerProfilePresenter;
import com.teamadr.ecommerceapp.presenter.customer_profile.CustomerProfilePresenterImpl;
import com.teamadr.ecommerceapp.utils.UserAuth;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.teamadr.ecommerceapp.view.add_product.NewProductActivity.REQUEST_BIG_IMAGE;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {
    @BindView(R.id.root)
    View root;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.img_cover)
    ImageView imgCover;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.imgUpdateImageCover)
    ImageView imgUpdateImageCover;
    @BindView(R.id.imgUpdateAvatar)
    ImageView imgUpdateAvatar;

    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_phone)
    TextView txtPhone;

    @BindView(R.id.btn_edit_info)
    ImageButton btnEditInfo;

    private AdminProfilePresenter adminProfilePresenter;
    private CustomerProfilePresenter customerProfilePresenter;
    private AdminDto adminDto;
    private CustomerDto customerDto;

    private StorageReference mStorageRef;
    private String avatarUrl;
    private String coverUrl;
    private LoadingDialog loadingDialog;

    public static final int REQUEST_CODE_AVATAR = 0;
    public static final int REQUEST_CODE_COVER = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        initView();
        loadProfile();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfile();
    }
    

    private void loadProfile() {
        if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(getContext()))) {
            adminProfilePresenter.getProfileAdmin();
        } else {
            customerProfilePresenter.getProfileCustomer();
        }
    }

    private void initPresenter() {
        adminProfilePresenter = new AdminProfilePresenterImpl(getContext(), this);
        customerProfilePresenter = new CustomerProfilePresenterImpl(getContext(), this);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(getContext());
        btnEditInfo.setOnClickListener(this);
        imgUpdateAvatar.setOnClickListener(this);
        imgUpdateImageCover.setOnClickListener(this);
    }

    @Override
    public void refreshProfileAdmin(AdminDto adminDto) {
        if (adminDto.getAvatarUrl() != null){
            Glide.with(getContext()).load(adminDto.getAvatarUrl())
                    .into(imgAvatar);
        }else {
            imgAvatar.setImageResource(R.drawable.avatar_placeholder);
        }

        if (adminDto.getImageCoverUrl() != null){
            Glide.with(getContext()).load(adminDto.getImageCoverUrl())
                    .into(imgCover);
        }else {
            imgCover.setImageResource(R.drawable.wall_placeholder);
        }


        txtName.setText(adminDto.getFirstName() + " " + adminDto.getLastName());
        txtAddress.setText(adminDto.getAddress());
        txtEmail.setText(adminDto.getContactEmail());
        txtGender.setText(adminDto.getGender().getValue());
        txtPhone.setText(adminDto.getPhone());

        txtBirthday.setText(adminDto.getBirthDay());

        this.adminDto = adminDto;

        EventBus.getDefault().post(new UserProfileEvent(
                adminDto.getAvatarUrl(),
                adminDto.getImageCoverUrl(),
                adminDto.getFirstName() + " " + adminDto.getLastName(),
                adminDto.getAddress()));
    }

    @Override
    public void refreshProfileCustomer(CustomerDto customerDto) {
        if (customerDto.getAvatarUrl() != null){
            Glide.with(getContext()).load(customerDto.getAvatarUrl())
                    .into(imgAvatar);
        }else {
            imgAvatar.setImageResource(R.drawable.avatar_placeholder);
        }

        if (customerDto.getImageCoverUrl() != null){
            Glide.with(getContext()).load(customerDto.getImageCoverUrl())
                    .into(imgCover);
        }else {
            imgCover.setImageResource(R.drawable.wall_placeholder);
        }

        txtName.setText(customerDto.getFirstName() + " " + customerDto.getLastName());
        txtAddress.setText(customerDto.getAddress());
        txtEmail.setText(customerDto.getContactEmail());
        txtGender.setText(customerDto.getGender().getValue());
        txtPhone.setText(customerDto.getPhone());

        txtBirthday.setText(customerDto.getBirthDay());

        this.customerDto = customerDto;

        EventBus.getDefault().post(new UserProfileEvent(
                customerDto.getAvatarUrl(),
                customerDto.getImageCoverUrl(),
                customerDto.getFirstName() + " " + customerDto.getLastName(),
                customerDto.getAddress()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_info: {
                Intent intent = new Intent(getContext(), ProfileEditActivity.class);
                if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(getContext()))) {
                    intent.putExtra(StringConstant.KEY_USER_PROFILE, adminDto);
                } else {
                    intent.putExtra(StringConstant.KEY_USER_PROFILE, customerDto);
                }
                startActivity(intent);
            }
            break;

            case R.id.imgUpdateAvatar: {
                FishBun.with(this).setImageAdapter(new GlideAdapter())
                        .setRequestCode(REQUEST_CODE_AVATAR)
                        .startAlbum();
            }
            break;

            case R.id.imgUpdateImageCover: {
                FishBun.with(this).setImageAdapter(new GlideAdapter())
                        .setRequestCode(REQUEST_CODE_COVER)
                        .startAlbum();
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case REQUEST_CODE_AVATAR: {
                if (resultCode == RESULT_OK) {
                    loadingDialog.show();
                    ArrayList<Uri> path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Glide.with(this).load(path.get(0)).into(imgAvatar);

                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    long timestamp = new Date().getTime();
                    String filePath = "images/profiles/avatar" + timestamp + ".jpg";
                    StorageReference storageReference = mStorageRef.child(filePath);
                    storageReference.putFile(path.get(0)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            loadingDialog.dismiss();
                            avatarUrl = "https://storage.googleapis.com/ecommerceapp-7b1b2.appspot.com/" + filePath;
                            if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(getContext()))) {
                                NewAdminDto newAdminDto = new NewAdminDto();

                                newAdminDto.setFirstName(adminDto.getFirstName());
                                newAdminDto.setLastName(adminDto.getLastName());
                                newAdminDto.setAddress(adminDto.getAddress());
                                newAdminDto.setUserName(txtEmail.getText().toString().trim());
                                newAdminDto.setPassword(UserAuth.getUserPassword(getContext()));
                                newAdminDto.setUserType(UserType.ADMIN);
                                newAdminDto.setBirthDay(adminDto.getBirthDay());
                                newAdminDto.setGender(adminDto.getGender());
                                newAdminDto.setPhone(adminDto.getPhone());
                                newAdminDto.setImageCoverUrl(adminDto.getImageCoverUrl());
                                newAdminDto.setAvatarUrl(avatarUrl);

                                adminProfilePresenter.editProfileAdmin(newAdminDto);
                            } else {
                                NewCustomerDto newCustomerDto = new NewCustomerDto();

                                newCustomerDto.setFirstName(customerDto.getFirstName());
                                newCustomerDto.setLastName(customerDto.getLastName());
                                newCustomerDto.setAddress(customerDto.getAddress());
                                newCustomerDto.setUserName(txtEmail.getText().toString().trim());
                                newCustomerDto.setPassword(UserAuth.getUserPassword(getContext()));
                                newCustomerDto.setUserType(UserType.CUSTOMER);
                                newCustomerDto.setBirthDay(customerDto.getBirthDay());
                                newCustomerDto.setGender(customerDto.getGender());
                                newCustomerDto.setPhone(customerDto.getPhone());
                                newCustomerDto.setImageCoverUrl(customerDto.getImageCoverUrl());
                                newCustomerDto.setAvatarUrl(avatarUrl);

                                customerProfilePresenter.editProfileCustomer(newCustomerDto);
                            }
                        }
                    });
                }
            }
            break;

            case REQUEST_CODE_COVER: {
                if (resultCode == RESULT_OK) {
                    loadingDialog.show();
                    ArrayList<Uri> path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    Glide.with(this).load(path.get(0)).into(imgCover);

                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    long timestamp = new Date().getTime();
                    String filePath = "images/profiles/cover" + timestamp + ".jpg";
                    StorageReference storageReference = mStorageRef.child(filePath);
                    storageReference.putFile(path.get(0)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            loadingDialog.dismiss();
                            coverUrl = "https://storage.googleapis.com/ecommerceapp-7b1b2.appspot.com/" + filePath;
                            if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(getContext()))) {
                                NewAdminDto newAdminDto = new NewAdminDto();

                                newAdminDto.setFirstName(adminDto.getFirstName());
                                newAdminDto.setLastName(adminDto.getLastName());
                                newAdminDto.setAddress(adminDto.getAddress());
                                newAdminDto.setUserName(txtEmail.getText().toString().trim());
                                newAdminDto.setPassword(UserAuth.getUserPassword(getContext()));
                                newAdminDto.setUserType(UserType.ADMIN);
                                newAdminDto.setBirthDay(adminDto.getBirthDay());
                                newAdminDto.setGender(adminDto.getGender());
                                newAdminDto.setPhone(adminDto.getPhone());
                                newAdminDto.setImageCoverUrl(coverUrl);
                                newAdminDto.setAvatarUrl(adminDto.getAvatarUrl());


                                adminProfilePresenter.editProfileAdmin(newAdminDto);

                            } else {
                                NewCustomerDto newCustomerDto = new NewCustomerDto();

                                newCustomerDto.setFirstName(customerDto.getFirstName());
                                newCustomerDto.setLastName(customerDto.getLastName());
                                newCustomerDto.setAddress(customerDto.getAddress());
                                newCustomerDto.setUserName(txtEmail.getText().toString().trim());
                                newCustomerDto.setPassword(UserAuth.getUserPassword(getContext()));
                                newCustomerDto.setUserType(UserType.CUSTOMER);
                                newCustomerDto.setBirthDay(customerDto.getBirthDay());
                                newCustomerDto.setGender(customerDto.getGender());
                                newCustomerDto.setPhone(customerDto.getPhone());
                                newCustomerDto.setImageCoverUrl(coverUrl);
                                newCustomerDto.setAvatarUrl(customerDto.getAvatarUrl());

                                customerProfilePresenter.editProfileCustomer(newCustomerDto);
                            }
                        }
                    });
                }
            }
            break;

        }
    }
}
