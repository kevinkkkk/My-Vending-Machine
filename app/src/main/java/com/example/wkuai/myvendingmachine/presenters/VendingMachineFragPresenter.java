package com.example.wkuai.myvendingmachine.presenters;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.ItemInventory;
import com.example.wkuai.myvendingmachine.models.VendingMachine;
import com.example.wkuai.myvendingmachine.models.imodels.PurchaseHistory;
import com.example.wkuai.myvendingmachine.panels.VendingMachineFragPanel;

import java.util.List;

public class VendingMachineFragPresenter implements VendingMachine.InventorListener{
    VendingMachineFragPanel mPanel;
    VendingMachine mVendingMachine;

    public VendingMachineFragPresenter() {
        mVendingMachine = new VendingMachine();
        mVendingMachine.setInventoryListener(this);
    }

    public void setPanel(VendingMachineFragPanel panel) {
        mPanel = panel;
    }

    public void getInventory(){
        List<ItemInventory> inventories = mVendingMachine.getInventory();
        for (int i = 0; i < inventories.size(); i++) {
            mPanel.renderItemInventory(inventories.get(i).getItem().getId(), inventories.get(i).getQuantity());
        }
        mPanel.clearViews();
    }

    public void insertCoin(AcceptedCoins coin){
        mPanel.updateAvailableFundsView(mVendingMachine.insertCoin(coin));
    }

    public void selectItemId(String itemId){
        if (mVendingMachine.keyInId(itemId)){
            mPanel.updateItemSelectView(itemId);
        }
    }

    public void abortPurchasing(){
        mPanel.updateReturnFundsView(mVendingMachine.abortPurchasing());
    }

    public void confirmPurchasing(){
        //if no item selected show an alert info
        if (!mVendingMachine.isItemSelected()) {
            mPanel.displayAlert("Please select a item!");
            return;
        }

        try {
            mPanel.updateReturnFundsView(mVendingMachine.confirmSelection());
        } catch (NoEnoughItemsException ne) {
            //Show alert and return coins to users if the item has been sold out
            mPanel.displayAlert("This item has been sold out!");
            abortPurchasing();
        } catch (NoSufficientFundsException se) {
            //Show alert and return coins to users if there is no sufficient funds
            mPanel.displayAlert("Insufficient funds!");
            abortPurchasing();
        }
    }

    public List<PurchaseHistory> getHistories(){
        return mVendingMachine.getPurchaseHistory();
    }

    @Override
    public void onInventoryChanged(ItemInventory itemInventory) {
        mPanel.renderItemInventory(itemInventory.getItem().getId(), itemInventory.getQuantity());
        mPanel.kickOutItem(itemInventory.getItem().getId());
    }
}
