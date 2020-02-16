package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Receipt {
    private @Getter double totalTaxes;
    private @Getter double totalAmount;
    private @Getter List<ReceiptItem> receiptItemList;
}
