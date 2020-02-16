package org.example.service;

import org.example.model.Product;
import org.example.model.ReceiptItem;


public interface ReceiptItemFactory {
    ReceiptItem createReceiptItem(Product product, int quantity);
}
