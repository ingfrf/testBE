package org.example.service;

import org.example.SpringBootConsoleApplication;
import org.example.enums.ProductType;
import org.example.model.Item;
import org.example.model.Product;
import org.example.model.ReceiptItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootConsoleApplication.class})
@SpringBootTest
public class ReceiptItemFactoryTest {
    private static final double DELTA = 1e-10;

    @Autowired
    ReceiptItemFactory receiptItemFactory;

    @Test
    public void testDependency() {
        assertEquals("ReceiptItemFactoryImpl", receiptItemFactory.getClass().getSimpleName());
    }

    @Test
    public void createTaxFreeReceiptItem() {
        Item item = new Item(new Product(100, "book", 12.49, ProductType.BOOK, false), 1);
        ReceiptItem receiptItem = receiptItemFactory.createReceiptItem(item.getProduct(), item.getQuantity());
        assertEquals(1, receiptItem.getQuantity());
        assertEquals(0.0d, receiptItem.getSalesTax(), DELTA);
        assertEquals(12.49, receiptItem.getAmount(), DELTA);
        assertEquals(100, item.getProduct().getId());
    }

    @Test
    public void createBasicTaxReceiptItem() {
        Item item = new Item(new Product(101, "music CD", 14.99, ProductType.OTHER, false), 1);
        ReceiptItem receiptItem = receiptItemFactory.createReceiptItem(item.getProduct(), item.getQuantity());
        assertEquals(1, receiptItem.getQuantity());
        assertEquals(1.50, receiptItem.getSalesTax(), DELTA);
        assertEquals(16.49, receiptItem.getAmount(), DELTA);
        assertEquals(101, item.getProduct().getId());
    }

    @Test
    public void createImportedTaxFreeReceiptItem() {
        Item item = new Item(new Product(108, "box of imported chocolates", 11.25, ProductType.FOOD, true), 1);
        ReceiptItem receiptItem = receiptItemFactory.createReceiptItem(item.getProduct(), item.getQuantity());
        assertEquals(1, receiptItem.getQuantity());
        assertEquals(0.60, receiptItem.getSalesTax(), DELTA);
        assertEquals(11.85, receiptItem.getAmount(), DELTA);
        assertEquals(108, item.getProduct().getId());
    }

    @Test
    public void createImportedBasicTaxReceiptItem() {
        Item item = new Item(new Product(104, "imported bottle of perfume", 47.50, ProductType.OTHER, true), 1);
        ReceiptItem receiptItem = receiptItemFactory.createReceiptItem(item.getProduct(), item.getQuantity());
        assertEquals(1, receiptItem.getQuantity());
        assertEquals(7.15, receiptItem.getSalesTax(), DELTA);
        assertEquals(54.65, receiptItem.getAmount(), DELTA);
        assertEquals(104, item.getProduct().getId());
    }
}