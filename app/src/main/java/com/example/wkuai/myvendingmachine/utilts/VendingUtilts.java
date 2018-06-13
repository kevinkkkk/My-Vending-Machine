package com.example.wkuai.myvendingmachine.utilts;

import com.example.wkuai.myvendingmachine.models.Item;
import com.example.wkuai.myvendingmachine.models.ItemInventory;

import java.util.ArrayList;
import java.util.List;

public class VendingUtilts {

    private final static int NUMBER_OF_ITEMS = 3;
    private final static int QUANTITY_OF_SINGLE_ITEM = 10;
    private final static String ITEM_1 = "Item1";
    private final static String ITEM_2 = "Item2";
    private final static String ITEM_3 = "Item3";
    public final static String ITEM_1_ID = "1";
    public final static String ITEM_2_ID = "2";
    public final static String ITEM_3_ID = "3";
    private final static int ITEM_1_PRICE = 55;
    private final static int ITEM_2_PRICE = 70;
    private final static int ITEM_3_PRICE = 75;

    public static List<ItemInventory> reloadInventory() {
        List<ItemInventory> inventory = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            ItemInventory item = reloadItem(i);
            inventory.add(item);
        }

        return inventory;
    }

    private static ItemInventory reloadItem(int id) {
        if (id == 0){
            return new ItemInventory(new Item(ITEM_1_ID, ITEM_1, ITEM_1_PRICE), QUANTITY_OF_SINGLE_ITEM);
        } else if (id == 1) {
            return new ItemInventory(new Item(ITEM_2_ID, ITEM_2, ITEM_2_PRICE), QUANTITY_OF_SINGLE_ITEM);
        }
        return new ItemInventory(new Item(ITEM_3_ID, ITEM_3, ITEM_3_PRICE), QUANTITY_OF_SINGLE_ITEM);
    }

}