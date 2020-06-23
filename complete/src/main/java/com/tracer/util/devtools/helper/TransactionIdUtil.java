package com.tracer.util.devtools.helper;

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
