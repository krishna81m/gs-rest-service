package com.paycycle.util.devtools.helper;

import com.paycycle.util.devtools.filter.HttpLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionIdUtil {

    private static final Logger logger = LoggerFactory.getLogger(TransactionIdUtil.class);

    private static String txId;

    public static void setTransactionId() {
        txId = "txId-" + (int) (Math.random() * 10);
        logger.info("TxId created: " + txId);
    }

    public static String getTransactionId() {
        return txId;
    }
}
