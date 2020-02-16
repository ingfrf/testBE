package org.example.service;

import org.example.model.Item;
import org.example.model.Receipt;

import java.util.List;

public interface PurchaseItemService {
    Receipt purchaseItems(List<Item> itemList);
}
