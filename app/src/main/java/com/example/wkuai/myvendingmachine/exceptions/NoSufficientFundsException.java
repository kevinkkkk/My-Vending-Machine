package com.example.wkuai.myvendingmachine.exceptions;

/**
 * This exception will be thrown out when there is no sufficient funds
 */
public class NoSufficientFundsException extends Exception {
    public int addMore;

    public NoSufficientFundsException(int value) {
        this.addMore = value;
    }
}
