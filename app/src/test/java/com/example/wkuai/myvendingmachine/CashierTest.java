package com.example.wkuai.myvendingmachine;

import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.Cashier;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CashierTest {

    @Test
    public void test_insertCoinsInSlot_ReturnCoinsInRightAmount(){
        Cashier cashier = new Cashier();
        cashier.addCoinInToSlot(AcceptedCoins.QUARTERS);
        cashier.addCoinInToSlot(AcceptedCoins.DIMES);
        assertTrue(cashier.returnCoinsInSlot() == 35);
    }
}
