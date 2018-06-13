package com.example.wkuai.myvendingmachine;

import com.example.wkuai.myvendingmachine.models.VendingMachine;
import com.example.wkuai.myvendingmachine.utilts.VendingUtilts;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VendingMachineTest {

    @Test
    public void test_resetInventoryForAllItems() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.resetMachine();
        assertTrue(vendingMachine.getInventory().size() == 3);
        assertTrue(vendingMachine.getInventory().get(0).getQuantity() == 10);
    }

    @Test
    public void test_resetInventoryForItem1() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.resetMachine();
        assertTrue(vendingMachine.getInventory().get(0).getItem().getId().equals(VendingUtilts.ITEM_1_ID));
        assertTrue(vendingMachine.getInventory().get(0).getQuantity() == 10);
    }

    @Test
    public void test_isItemSelected_negative() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.resetMachine();
        assertFalse(vendingMachine.isItemSelected());
    }

    @Test
    public void test_isItemSelected_positive() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.resetMachine();
        vendingMachine.keyInId(VendingUtilts.ITEM_1_ID);
        assertTrue(vendingMachine.isItemSelected());
    }
}