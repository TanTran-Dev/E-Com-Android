package com.teamadr.ecommerceapp.presenter.profile_edit;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.profile.ProfileEditView;

public class ProfileEditPresenterImpl implements ProfileEditPresenter {
    private Context context;
    private ProfileEditView view;
    private ProfileEditInteractor interactor;

    public ProfileEditPresenterImpl(Context context, ProfileEditView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ProfileEditInteractorImpl(context);
    }

    @Override
    public void editProfileAdmin(NewSalesmanDto newSalesmanDto) {
        view.showLoadingProgress();
        interactor.updateProfileAdmin(UserAuth.getUserId(context), newSalesmanDto,
                responseBody -> {
                    view.hideLoadingProgress();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    view.navigateToProfile();
                },
                Throwable::printStackTrace);
    }

    @Override
    public void editProfileCustomer(NewCustomerDto newCustomerDto) {
        view.showLoadingProgress();
        interactor.updateProfileCustomer(UserAuth.getUserId(context), newCustomerDto,
                responseBody -> {
                    view.hideLoadingProgress();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    view.navigateToProfile();
                },
                Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }
}
