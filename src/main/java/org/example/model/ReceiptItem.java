package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ReceiptItem {
    private @Getter Product product;
    private @Getter int quantity;
    private @Getter double salesTax;
    private @Getter double amount;
}
