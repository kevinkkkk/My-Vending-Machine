package com.example.wkuai.myvendingmachine.panels;

public interface VendingMachineFragPanel {

    void renderItemInventory(String itemId, int count);
    void clearViews();
    void updateAvailableFundsView(int funds);
    void updateItemSelectView(String itemId);
    void updateReturnFundsView(int funds);
    void displayAlert(String msg);
    void kickOutItem(String itemId);
}
