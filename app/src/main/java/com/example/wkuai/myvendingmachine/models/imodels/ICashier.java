package com.example.wkuai.myvendingmachine.models.imodels;

import com.example.wkuai.myvendingmachine.models.AcceptedCoins;

import java.util.Map;

public interface ICashier {
    int addCoinInToSlot(AcceptedCoins coin);
    int confirmPurchasingAndReturnChanges(int itemPrice);
    int returnCoinsInSlot();
    void resetCashier();
    Map<AcceptedCoins, Integer> getCoinsInSlot();
}
