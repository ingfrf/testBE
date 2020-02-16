package org.example.service.impl;

import org.example.model.Item;
import org.example.model.Receipt;
import org.example.model.ReceiptItem;
import org.example.service.PurchaseItemService;
import org.example.service.ReceiptItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseItemServiceImpl implements PurchaseItemService {

    @Autowired
    private ReceiptItemFactory receiptItemFactory;

    @Override
    public Receipt purchaseItems(List<Item> itemList) {
        List<ReceiptItem> receiptItemList = new ArrayList<>();
        double totalTaxes = 0.0d;
        double totalAmount = 0.0d;
        for (Item item : itemList) {
            ReceiptItem receiptItem = receiptItemFactory.createReceiptItem(item.getProduct(), item.getQuantity());
            receiptItemList.add(receiptItem);
            totalTaxes += receiptItem.getSalesTax();
            totalAmount += receiptItem.getAmount();
        }
        return new Receipt(totalTaxes, totalAmount, receiptItemList);
    }
}
