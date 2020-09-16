package com.teamadr.ecommerceapp.presenter.admin_profile;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.admin.AdminDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface AdminProfileInteractor extends BaseInteractor {
    void getProfileAdmin(String token,
                         Consumer<ResponseBody<AdminDto>> onSuccess,
                         Consumer<Throwable> onError);
    void updateProfileAdmin(String adminId, NewAdminDto newAdminDto,
                            Consumer<ResponseBody> onSuccess,
                            Consumer<Throwable> onError);
}
