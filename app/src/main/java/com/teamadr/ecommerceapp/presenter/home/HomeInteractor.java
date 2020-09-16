package com.teamadr.ecommerceapp.presenter.home;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface HomeInteractor extends BaseInteractor {
    void refreshTrademarks (List<String> sortBy, List<String> sortType, int pageIndex, int pageSize,
                            Consumer<ResponseBody<Page<TrademarkDto>>> onSuccess,
                            Consumer<Throwable> onError);
}
