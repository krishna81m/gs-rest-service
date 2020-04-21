package com.paycycle.util.devtools.helper;

public class TransactionIdUtil {

    private static String txId;

    public static void setTransactionId() {
        txId = "txId-" + (int) (Math.random() * 10);
    }

    public static String getTransactionId() {
        return txId;
    }
}
