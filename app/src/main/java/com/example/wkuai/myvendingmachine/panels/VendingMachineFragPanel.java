package com.example.wkuai.myvendingmachine.panels;

/**
 * Abstract view layer methods. Presenter use panel to access view.
 * {@link com.example.wkuai.myvendingmachine.views.VendingMachineFragment} implements it.
 */
public interface VendingMachineFragPanel {

    /**
     * Update inventory info to the viewlayer
     * @param itemId
     * @param count
     */
    void renderItemInventory(String itemId, int count);

    /**
     * Reset view when purchase is finished
     */
    void clearViews();

    /**
     * Update funds info while the user is inserting coins into slot
     * @param funds
     */
    void updateAvailableFundsView(int funds);

    /**
     * Upadate item selection view when the user selects items
     * @param itemId
     */
    void updateItemSelectView(String itemId);

    /**
     * Update funds return view when the machine returns changes or the purchasing been aborted.
     * @param funds
     */
    void updateReturnFundsView(int funds);

    /**
     * Toast alert in two cases:
     * 1. No sufficient funds. The inserted coins will be returned
     * 2. The selected item was sold out. The inserted coins will be returned
     * @param msg
     */
    void displayAlert(String msg);

    /**
     * Display an item image indicates teh selected item was delivered successfully.
     * @param itemId
     */
    void kickOutItem(String itemId);
}
