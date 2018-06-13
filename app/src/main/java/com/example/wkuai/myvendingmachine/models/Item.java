package com.example.wkuai.myvendingmachine.models;

/**
 * This class represent a item in vending machine
 */
public class Item {

    private final String id;
    private final String itemName;
    private final int price;

    /**
     * Parametzed constructor.
     * @param id: product unique ID.
     * @param price: sale price in pennies
     */
    public Item(String id, String itemName, int price){
        this.id = id;
        this.itemName = itemName;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }
}
