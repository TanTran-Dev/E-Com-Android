package com.teamadr.ecommerceapp.presenter.admin_profile;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.profile.ProfileView;

public class AdminProfilePresenterImpl implements AdminProfilePresenter {
    private Context context;
    private ProfileView view;
    private AdminProfileInteractor interactor;

    public AdminProfilePresenterImpl(Context context, ProfileView view) {
        this.context = context;
        this.view = view;
        this.interactor = new AdminProfileInteractorImpl(context);
    }

    @Override
    public void editProfileSalesman(NewSalesmanDto newAdminProfileDto) {
        interactor.updateProfileSalesman(UserAuth.getUserId(context), newAdminProfileDto,
                responseBody -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    getProfileSalesman();
                },
                Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }

    @Override
    public void getProfileSalesman() {
        interactor.getProfileSalesman(UserAuth.getBearerToken(context),
                adminDtoResponseBody -> {
                    Log.i("Profile", "accept: " + adminDtoResponseBody.getData());
                    view.refreshProfileAdmin(adminDtoResponseBody.getData());
                },
                Throwable::printStackTrace);
    }

}
