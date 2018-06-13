package com.example.wkuai.myvendingmachine.models.imodels;

import com.example.wkuai.myvendingmachine.models.AcceptedCoins;

import java.util.Map;

/**
 * Provides features like receive, count, return and accept coins
 */
public interface ICashier {

    /**
     * Simulate the process that the user insert coin into slot.
     * The amount will be counting along insertion
     * Return total amount that been inserted.
     * @param coin Acceptable coins {@link AcceptedCoins}
     * @return int as the total amount
     */
    int addCoinInToSlot(AcceptedCoins coin);

    /**
     * Once the purchase is confirmed. Do the following steps:
     * 1. Check inserted amount. Compare it with itemPrice. {@link com.example.wkuai.myvendingmachine.models.Item}
     *    a. If it's great or equal to itemPrice. Accept coins into cashBox and take changes out of cashBox.
     *    b. If it's less than itemPrice. Coins will jeep in slot
     * 2. return difference amount in int. If it's negative that means the inserted fund is insufficient.
     * {@link com.example.wkuai.myvendingmachine.models.VendingMachine} VendingMachine will use it to determine weather
     * throw put NoSufficientFundsException. {@link com.example.wkuai.myvendingmachine.exceptions.NoSufficientFundsException}
     *
     * @param itemPrice
     * @return int as changes
     */
    int confirmPurchasingAndReturnChanges(int itemPrice);

    /**
     * It will be called when user abort purchasing or not enough funds
     * @return return coins in slot and clear slot.
     */
    int returnCoinsInSlot();

    /**
     * Constructor call it to reset Cashier. It includes clear slot and fill cashBox to start state
     */
    void resetCashier();

    /**
     * Returns coins summary in slot. It helps generate transaction records when the purchase done successfully
     * @return in Map format.
     */
    Map<AcceptedCoins, Integer> getCoinsInSlot();
}
