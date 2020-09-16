package com.teamadr.ecommerceapp.presenter.admin;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface AdminRegistrationInteractor extends BaseInteractor {
    void registerNewAdminByEmail(NewAdminDto newAdminDto,
                                 Consumer<ResponseBody> onSuccess,
                                 Consumer<Throwable> onError);
}
