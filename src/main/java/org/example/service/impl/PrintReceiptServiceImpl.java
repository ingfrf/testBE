package org.example.service.impl;

import org.example.model.Receipt;
import org.example.model.ReceiptItem;
import org.example.service.PrintReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Component
public class PrintReceiptServiceImpl implements PrintReceiptService {
    private static Logger LOG = LoggerFactory
            .getLogger(PrintReceiptServiceImpl.class);
    private final DecimalFormat df;

    public PrintReceiptServiceImpl() {
        df = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
    }

    @Override
    public void printReceipt(Receipt receipt) {
        for (ReceiptItem receiptItem : receipt.getReceiptItemList()) {
            LOG.info("--> " + receiptItem.getQuantity() + " " + receiptItem.getProduct().getDescription() + " at " + df.format(receiptItem.getAmount()));
        }
        LOG.info("Sales Taxes: " + df.format(receipt.getTotalTaxes()));
        LOG.info("Total: " + df.format(receipt.getTotalAmount()));
    }
}
