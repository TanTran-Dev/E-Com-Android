package com.teamadr.ecommerceapp.presenter.customer_profile;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.profile.ProfileView;

public class CustomerProfilePresenterImpl implements CustomerProfilePresenter {
    private Context context;
    private ProfileView view;
    private CustomerProfileInteractor interactor;

    public CustomerProfilePresenterImpl(Context context, ProfileView view) {
        this.context = context;
        this.view = view;
        this.interactor = new CustomerProfileInteractorImpl(context);
    }


    @Override
    public void editProfileCustomer(NewCustomerDto newCustomerDto) {
        interactor.updateProfileCustomer(UserAuth.getUserId(context), newCustomerDto,
                responseBody -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    getProfileCustomer();
                },
                Throwable::printStackTrace);
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }

    @Override
    public void getProfileCustomer() {
        interactor.getProfileAdmin(UserAuth.getBearerToken(context),
                customerDtoResponseBody
                        -> view.refreshProfileCustomer(customerDtoResponseBody.getData()),
                Throwable::printStackTrace);
    }
}
