package org.example.service;

import org.example.SpringBootConsoleApplication;
import org.example.enums.ProductType;
import org.example.model.Item;
import org.example.model.Product;
import org.example.model.Receipt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootConsoleApplication.class})
@SpringBootTest
public class PurchaseItemServiceTest {
    private static final double DELTA = 1e-10;

    @Autowired
    PurchaseItemService purchaseItemService;

    @Test
    public void testDependency() {
        assertEquals("PurchaseItemServiceImpl", purchaseItemService.getClass().getSimpleName());
    }

    @Test
    public void purchaseItems() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(105, "imported bottle of perfume", 27.99, ProductType.OTHER, true), 1));
        itemList.add(new Item(new Product(106, "bottle of perfume", 18.99, ProductType.OTHER, false), 1));
        itemList.add(new Item(new Product(107, "package of headache pills", 9.75, ProductType.MEDICAL, false), 1));
        itemList.add(new Item(new Product(108, "box of imported chocolates", 11.25, ProductType.FOOD, true), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(6.70, receipt.getTotalTaxes(), DELTA);
        assertEquals(74.68, receipt.getTotalAmount(), DELTA);
        assertEquals(4, receipt.getReceiptItemList().size());
    }
}