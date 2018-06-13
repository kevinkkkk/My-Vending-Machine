package com.example.wkuai.myvendingmachine.models.imodels;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.ItemInventory;
import com.example.wkuai.myvendingmachine.models.PurchaseHistory;
import com.example.wkuai.myvendingmachine.models.VendingMachine;

import java.util.List;

public interface IVendingMachine {
    void setInventoryListener(VendingMachine.InventorListener listener);
    void resetMachine();
    int insertCoin(AcceptedCoins coin);
    boolean keyInId(String itemId);
    int confirmSelection() throws NoSufficientFundsException, NoEnoughItemsException;
    int abortPurchasing();
    List<ItemInventory> getInventory();
    boolean isItemSelected();
    List<PurchaseHistory> getPurchaseHistory();
}
