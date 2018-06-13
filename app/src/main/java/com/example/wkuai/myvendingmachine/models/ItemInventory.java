package com.example.wkuai.myvendingmachine.models;

/**
 * Inventor info for a single item
 * 1. Item {@link Item}
 * 2. quantity
 */
public class ItemInventory {
    private Item item;
    private int quantity;

    public ItemInventory(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean removeOneItem() {
        if (quantity <= 0) return false;
        quantity--;
        return true;
    }

    /**
     * For future use. We can use it when the machine reloaded
     * @param quantity
     */
    public void resetInventory(int quantity) {
        this.quantity = quantity;
    }
}
