package com.example.wkuai.myvendingmachine.models;

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

    public void resetInventory(int quantity) {
        this.quantity = quantity;
    }
}
