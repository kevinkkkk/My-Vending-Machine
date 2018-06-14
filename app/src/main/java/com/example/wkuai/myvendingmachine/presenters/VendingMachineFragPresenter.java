package com.example.wkuai.myvendingmachine.presenters;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.ItemInventory;
import com.example.wkuai.myvendingmachine.models.VendingMachine;
import com.example.wkuai.myvendingmachine.models.PurchaseHistory;
import com.example.wkuai.myvendingmachine.panels.VendingMachineFragPanel;

import java.util.List;

/**
 * The presenter is responsible to act as the middle man between view and model. It retrieves data from the model and returns it formatted to the view
 */
public class VendingMachineFragPresenter implements VendingMachine.InventorListener{
    VendingMachineFragPanel mPanel;
    VendingMachine mVendingMachine;

    /**
     * It holds a instance of VendingMachine and implements InventoryListener {@link com.example.wkuai.myvendingmachine.models.VendingMachine.InventorListener}
     */
    public VendingMachineFragPresenter() {
        mVendingMachine = new VendingMachine();
        mVendingMachine.setInventoryListener(this);
    }

    /**
     * Hold a panel instance which is implemented by VendingMachineFragment {@link com.example.wkuai.myvendingmachine.views.VendingMachineFragment}
     * @param panel
     */
    public void setPanel(VendingMachineFragPanel panel) {
        mPanel = panel;
    }

    /**
     * Retrieve Inventory info from VendingMachine and then call panel to render the view
     */
    public void getInventory(){
        List<ItemInventory> inventories = mVendingMachine.getInventory();
        for (int i = 0; i < inventories.size(); i++) {
            mPanel.renderItemInventory(inventories.get(i).getItem().getId(), inventories.get(i).getQuantity());
        }
        mPanel.clearViews();
    }

    /**
     * Count coins in slot while the user inserts them. Update view with the running amount
     * @param coin AcceptedCoin {@link AcceptedCoins}
     */
    public void insertCoin(AcceptedCoins coin){
        mPanel.updateAvailableFundsView(mVendingMachine.insertCoin(coin));
    }

    /**
     * Call VendingMachine to validate the selected itemId and send to view
     * @param itemId
     */
    public void selectItemId(String itemId){
        if (mVendingMachine.keyInId(itemId)){
            mPanel.updateItemSelectView(itemId);
        }
    }

    /**
     * Call this when the user abort purchasing
     */
    public void abortPurchasing(){
        mPanel.updateReturnFundsView(mVendingMachine.abortPurchasing());
    }

    /**
     * Call this when user confirm the purchasing and then process the purchasing through VendingMachine
     * here are two exception might be caught:
     * 1. {@link NoSufficientFundsException} there is no sufficient funds
     * 2. {@link NoEnoughItemsException} the selected item has been sold out
     */
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

    /**
     * get history transaction when the user press Summary button
     * @return
     */
    public List<PurchaseHistory> getHistories(){
        return mVendingMachine.getPurchaseHistory();
    }

    /**
     * Implements {@link com.example.wkuai.myvendingmachine.models.VendingMachine.InventorListener}
     * Whenever the inventory info were updated. Call panel to update new number into view layer
     * @param itemInventory
     */
    @Override
    public void onInventoryChanged(ItemInventory itemInventory) {
        mPanel.renderItemInventory(itemInventory.getItem().getId(), itemInventory.getQuantity());
        mPanel.kickOutItem(itemInventory.getItem().getId());
    }
}
