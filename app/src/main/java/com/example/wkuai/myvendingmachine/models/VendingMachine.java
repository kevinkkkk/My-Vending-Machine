package com.example.wkuai.myvendingmachine.models;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.imodels.IVendingMachine;
import com.example.wkuai.myvendingmachine.utilts.VendingUtilts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It provides all main logic of Vending machine
 */
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

    /**
     * {@link com.example.wkuai.myvendingmachine.models.VendingMachine.InventorListener}
     * The listener is listening inventory changes. If it happens it will proactively update inventory number in view layer.
     * {@link com.example.wkuai.myvendingmachine.presenters.VendingMachineFragPresenter} implements it
     * @param listener
     */
    @Override
    public void setInventoryListener(InventorListener listener) {
        this.listener = listener;
    }

    /**
     * Reset Vending machine to start state. include:
     * 1. initial Inventory
     * 2. initial cashier
     */
    @Override
    public void resetMachine() {
        this.inventory = VendingUtilts.reloadInventory();
        this.cashier = new Cashier();
    }

    /**
     * Insert coins into slot. Cashier {@link com.example.wkuai.myvendingmachine.models.Cashier} takes care the rest.
     * @param coin Acceptable coins in {@link AcceptedCoins}
     * @return int as the inserted amount
     */
    @Override
    public int insertCoin(AcceptedCoins coin) {
        return cashier.addCoinInToSlot(coin);
    }

    /**
     * Validate itemID that user input.
     * @param itemId {@link com.example.wkuai.myvendingmachine.models.Item}
     * @return true if the ID is valid. Otherwise returns false.
     */
    @Override
    public boolean keyInId(String itemId) {
        if (!validate(itemId)) return false;
        keyInItemId = itemId;
        isItemSelected = true;
        return true;
    }

    /**
     * Confirm selection and move forward purchasing
     * @return int as changes will be returning to user
     * @throws NoSufficientFundsException if there is not sufficient funds been received
     * @throws NoEnoughItemsException  if the selected item has been sold out.
     */
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

    /**
     * It will be called ehrn the user abort purchasing.
     * 1. clear selection
     * 2. return coins in slot
     * @return int as the amount in slot
     */
    @Override
    public int abortPurchasing() {
        postPurchase();
        return cashier.returnCoinsInSlot();
    }

    /**
     * Get Inventory information for all items.
     * @return {@link ItemInventory}
     */
    @Override
    public List<ItemInventory> getInventory() {
        return inventory;
    }

    /**
     * Check if there is a selected item
     * @return true when there a item been selected. otherwise returns false
     */
    @Override
    public boolean isItemSelected() {
        return isItemSelected;
    }

    /**
     * Provides history transactions. Use it to generate transaction log.
     * @return {@link PurchaseHistory}
     */
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
