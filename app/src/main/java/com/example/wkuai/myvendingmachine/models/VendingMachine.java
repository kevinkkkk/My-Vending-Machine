package com.example.wkuai.myvendingmachine.models;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.imodels.IVendingMachine;
import com.example.wkuai.myvendingmachine.utilts.VendingUtilts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachine implements IVendingMachine {
    private List<ItemInventory> inventory;
    private List<PurchaseHistory> history = new ArrayList<>();
    private int purcahseId = 1;
    private Cashier cashier;
    private String keyInItemId;
    private boolean isItemSelected = false;

    public interface InventorListener {
        void onInventoryChanged(ItemInventory itemInventory);
    }

    private InventorListener listener;

    public VendingMachine(){
        resetMachine();
    }

    @Override
    public void setInventoryListener(InventorListener listener) {
        this.listener = listener;
    }

    @Override
    public void resetMachine() {
        this.inventory = VendingUtilts.reloadInventory();
        this.cashier = new Cashier();
    }

    @Override
    public int insertCoin(AcceptedCoins coin) {
        return cashier.addCoinInToSlot(coin);
    }

    @Override
    public boolean keyInId(String itemId) {
        if (!validate(itemId)) return false;
        keyInItemId = itemId;
        isItemSelected = true;
        return true;
    }

    @Override
    public int confirmSelection() throws NoSufficientFundsException, NoEnoughItemsException {
        int changes = cashier.confirmPurchasingAndReturnChanges(inventory.get(Integer.valueOf(keyInItemId) - 1).getItem().getPrice());

        //If the user has not inserted sufficient funds, throw exception.
        if (changes < 0) {
            postPurchase();
            throw new NoSufficientFundsException(Math.abs(changes));
        }

        //update inventor. If there is no enough items, throw another exception
        if (!inventory.get(Integer.valueOf(keyInItemId) - 1).removeOneItem()) {
            postPurchase();
            throw new NoEnoughItemsException();
        }

        //otherwise, if went through fine. Update inventory view and return changes amount.
        listener.onInventoryChanged(inventory.get(Integer.valueOf(keyInItemId) - 1));
        saveToPurchaseHistory(changes);
        postPurchase();
        return changes;
    }

    @Override
    public int abortPurchasing() {
        postPurchase();
        return cashier.returnCoinsInSlot();
    }

    @Override
    public List<ItemInventory> getInventory() {
        return inventory;
    }

    @Override
    public boolean isItemSelected() {
        return isItemSelected;
    }

    @Override
    public List<PurchaseHistory> getPurchaseHistory() {
        return history;
    }

    private boolean validate(String itemId) {
        return  (itemId.equals(VendingUtilts.ITEM_1_ID)
                || itemId.equals(VendingUtilts.ITEM_2_ID)
                || itemId.equals(VendingUtilts.ITEM_3_ID));
    }

    private void postPurchase(){
        keyInItemId = "";
        isItemSelected = false;
    }

    private void saveToPurchaseHistory(int changes){
        Map<AcceptedCoins, Integer> slotCoinsInfo = cashier.getCoinsInSlot();
        int itemPrice = inventory.get(Integer.valueOf(keyInItemId) - 1).getItem().getPrice();
        String itemName = inventory.get(Integer.valueOf(keyInItemId) - 1).getItem().getItemName();
        PurchaseHistory purchaseHistory = new PurchaseHistory(purcahseId, itemName, itemPrice, slotCoinsInfo, changes);
        history.add(purchaseHistory);
        purcahseId++;
    }
}
