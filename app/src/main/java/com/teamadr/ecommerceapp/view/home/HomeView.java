package com.teamadr.ecommerceapp.view.home;


import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

public interface HomeView {
    void refreshTrademark(List<TrademarkDto> trademarkDtos);
    void logout();
    void navigateLogin();
}
