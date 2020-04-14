package com.paycycle.util.devtools.helper;

public class TransactionIdUtil {
    public static String getTransactionId() {
        return "txId-" + (int) (Math.random() * 10);
    }
}
