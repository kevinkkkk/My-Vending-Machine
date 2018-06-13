package com.example.wkuai.myvendingmachine.models;

import com.example.wkuai.myvendingmachine.models.imodels.ICashier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides features like receive, count, return and accept coins
 */
public class Cashier implements ICashier {
    private List<AcceptedCoins> slot = new ArrayList<>();
    private Map<AcceptedCoins, Integer> cashBox = new HashMap<>();
    private Map<AcceptedCoins, Integer> acceptedCoinsInSlot = new HashMap<>();

    public Cashier() {
        resetCashier();
    }

    /**
     * Constructor call it to reset Cashier. It includes clear slot and fill cashBox to start state
     */
    @Override
    public void resetCashier() {
        cashBox.put(AcceptedCoins.NICKELS, 100);
        cashBox.put(AcceptedCoins.DIMES, 100);
        cashBox.put(AcceptedCoins.QUARTERS, 100);
        slot.clear();
    }

    /**
     * Returns coins summary in slot. It helps generate transaction records when the purchase done successfully
     * @return in Map format.
     */
    @Override
    public Map<AcceptedCoins, Integer> getCoinsInSlot() {
        return acceptedCoinsInSlot;
    }


    /**
     * Simulate the process that the user insert coin into slot.
     * The amount will be counting along insertion
     * Return total amount that been inserted.
     * @param coin Acceptable coins {@link AcceptedCoins}
     * @return int as the total amount
     */
    @Override
    public int addCoinInToSlot(AcceptedCoins coin) {
        //each time can not exceed 100 cents
        if (countCoinsInSlot() + coin.getValueInPennies() <= 100 ) slot.add(coin);
        return countCoinsInSlot();
    }


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
    @Override
    public int confirmPurchasingAndReturnChanges(int itemPrice) {
        int receivedInPennies = countCoinsInSlot();
        int changesInPennies = receivedInPennies - itemPrice;

        if (changesInPennies >= 0) {
            acceptCoins();
            takeChangesFromCashBox(changesInPennies);
        }

        return changesInPennies;
    }

    /**
     * It will be called when user abort purchasing or not enough funds
     * @return return coins in slot and clear slot.
     */
    @Override
    public int returnCoinsInSlot() {
        int returnFunds = countCoinsInSlot();
        slot.clear();
        return returnFunds;
    }

    private int countCoinsInSlot(){
        int receivedInPennies = 0;
        Iterator<AcceptedCoins> iterator = slot.listIterator();
        while (iterator.hasNext()) {
            receivedInPennies += iterator.next().getValueInPennies();
        }
        return receivedInPennies;
    }

    private void acceptCoins(){
        //clear coins in slot. ready for new transaction
        clearCoinsInSlot();
        for (int i = 0; i < slot.size(); i++){
            cashBox.put(slot.get(i), cashBox.get(slot.get(i)) + 1);

            //before clear slot. we save slot coins for purchase history
            acceptedCoinsInSlot.put(slot.get(i), acceptedCoinsInSlot.get(slot.get(i)) + 1);

        }
        //clear slot for next purchase
        slot.clear();
    }

    private void takeChangesFromCashBox(int changes){
        if (changes == 0) return;
        while (changes > 0) {
            if (changes >= 25) {
                cashBox.put(AcceptedCoins.QUARTERS, cashBox.get(AcceptedCoins.QUARTERS) - 1);
                changes -= 25;
                continue;
            }
            if (changes >= 10) {
                cashBox.put(AcceptedCoins.DIMES, cashBox.get(AcceptedCoins.DIMES) - 1);
                changes -= 10;
                continue;
            }
            if (changes >= 5) {
                cashBox.put(AcceptedCoins.NICKELS, cashBox.get(AcceptedCoins.NICKELS) - 1);
                changes -= 5;
                continue;
            }
        }
    }

    private void clearCoinsInSlot(){
        acceptedCoinsInSlot.put(AcceptedCoins.NICKELS, 0);
        acceptedCoinsInSlot.put(AcceptedCoins.DIMES, 0);
        acceptedCoinsInSlot.put(AcceptedCoins.QUARTERS, 0);
    }
}
