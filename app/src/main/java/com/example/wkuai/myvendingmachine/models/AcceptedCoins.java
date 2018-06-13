package com.example.wkuai.myvendingmachine.models;

/**
 * Enum for accepted coin type and their unique keys
 * NICKELS - nickels
 * DIMES - dimes
 * QUARTER - quarter
 */
public enum  AcceptedCoins {

    NICKELS (5),
    DIMES (10),
    QUARTERS (25);

    private int valueInPennies;
    private AcceptedCoins(int valueInPennies) {
        this.valueInPennies = valueInPennies;
    }

    public int getValueInPennies(){
        return valueInPennies;
    }
}
