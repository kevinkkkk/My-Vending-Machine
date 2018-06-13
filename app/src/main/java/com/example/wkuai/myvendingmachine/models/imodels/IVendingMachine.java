package com.example.wkuai.myvendingmachine.models.imodels;

import com.example.wkuai.myvendingmachine.exceptions.NoEnoughItemsException;
import com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException;
import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.ItemInventory;
import com.example.wkuai.myvendingmachine.models.PurchaseHistory;
import com.example.wkuai.myvendingmachine.models.VendingMachine;

import java.util.List;

/**
 * It provides all main logic of Vending machine
 */
public interface IVendingMachine {

    /**
     * {@link com.example.wkuai.myvendingmachine.models.VendingMachine.InventorListener}
     * The listener is listening inventory changes. If it happens it will proactively update inventory number in view layer.
     * {@link com.example.wkuai.myvendingmachine.presenters.VendingMachineFragPresenter} implements it
     * @param listener
     */
    void setInventoryListener(VendingMachine.InventorListener listener);

    /**
     * Reset Vending machine to start state. include:
     * 1. initial Inventory
     * 2. initial cashier
     */
    void resetMachine();

    /**
     * Insert coins into slot. Cashier {@link com.example.wkuai.myvendingmachine.models.Cashier} takes care the rest.
     * @param coin Acceptable coins in {@link AcceptedCoins}
     * @return int as the inserted amount
     */
    int insertCoin(AcceptedCoins coin);

    /**
     * Validate itemID that user input.
     * @param itemId {@link com.example.wkuai.myvendingmachine.models.Item}
     * @return true if the ID is valid. Otherwise returns false.
     */
    boolean keyInId(String itemId);

    /**
     * Confirm selection and move forward purchasing
     * @return int as changes will be returning to user
     * @throws NoSufficientFundsException if there is not sufficient funds been received
     * @throws NoEnoughItemsException  if the selected item has been sold out.
     */
    int confirmSelection() throws NoSufficientFundsException, NoEnoughItemsException;

    /**
     * It will be called ehrn the user abort purchasing.
     * 1. clear selection
     * 2. return coins in slot
     * @return int as the amount in slot
     */
    int abortPurchasing();

    /**
     * Get Inventory information for all items.
     * @return {@link ItemInventory}
     */
    List<ItemInventory> getInventory();

    /**
     * Check if there is a selected item
     * @return true when there a item been selected. otherwise returns false
     */
    boolean isItemSelected();

    /**
     * Provides history transactions. Use it to generate transaction log.
     * @return {@link PurchaseHistory}
     */
    List<PurchaseHistory> getPurchaseHistory();
}
