package com.example.wkuai.myvendingmachine.models;

import com.example.wkuai.myvendingmachine.models.imodels.ICashier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cashier implements ICashier {
    private List<AcceptedCoins> slot = new ArrayList<>();
    private Map<AcceptedCoins, Integer> cashBox = new HashMap<>();
    private Map<AcceptedCoins, Integer> acceptedCoinsInSlot = new HashMap<>();

    public Cashier() {
        resetCashier();
    }

    @Override
    public void resetCashier() {
        cashBox.put(AcceptedCoins.NICKELS, 100);
        cashBox.put(AcceptedCoins.DIMES, 100);
        cashBox.put(AcceptedCoins.QUARTERS, 100);
        slot.clear();
    }

    @Override
    public Map<AcceptedCoins, Integer> getCoinsInSlot() {
        return acceptedCoinsInSlot;
    }

    @Override
    public int addCoinInToSlot(AcceptedCoins coin) {
        //each time can not exceed 100 cents
        if (countCoinsInSlot() + coin.getValueInPennies() <= 100 ) slot.add(coin);
        return countCoinsInSlot();
    }

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
