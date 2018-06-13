package com.example.wkuai.myvendingmachine.models;

import java.util.Map;

/**
 * Summary all transactions. It logs:
 * 1. purcahseId - unique ID for each transaction
 * 2. itemName
 * 3. itemPrice
 * 4. Inserted Nickels number
 * 5. Inserted Dimes number
 * 6. Inserted Quarters number
 * 7. Total inserted
 * 8. Changes returned
 */
public class PurchaseHistory {
    private  int purcahseId;
    private String itemName;
    private int itemPrice;
    private int nickel;
    private int dime;
    private int quarter;
    private int totalInsert;
    private int changes;

    public PurchaseHistory(int purcahseId, String itemName, int itemPrice, Map<AcceptedCoins, Integer> coinsInSlot, int changes) {
        this.purcahseId = purcahseId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.nickel = coinsInSlot.get(AcceptedCoins.NICKELS);
        this.dime = coinsInSlot.get(AcceptedCoins.DIMES);
        this.quarter = coinsInSlot.get(AcceptedCoins.QUARTERS);
        this.totalInsert = nickel * 5 + dime * 10 + quarter * 25;
        this.changes = changes;
    }

    public  int getPurcahseId() {
        return purcahseId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getNickel() {
        return nickel;
    }

    public int getDime() {
        return dime;
    }

    public int getQuarter() {
        return quarter;
    }

    public int getTotalInsert() {
        return totalInsert;
    }

    public int getChanges() {
        return changes;
    }
}
