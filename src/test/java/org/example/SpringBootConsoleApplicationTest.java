package org.example;

import org.example.enums.ProductType;
import org.example.model.Item;
import org.example.model.Product;
import org.example.model.Receipt;
import org.example.service.PrintReceiptService;
import org.example.service.PurchaseItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootConsoleApplication.class})
@SpringBootTest
public class SpringBootConsoleApplicationTest {
    private static final double DELTA = 1e-10;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private PrintReceiptService printReceiptService;

    @Test
    public void testPrintReceiptDependency() {
        assertEquals("PrintReceiptServiceImpl", printReceiptService.getClass().getSimpleName());
    }

    @Test
    public void testPurchaseItemDependency() {
        assertEquals("PurchaseItemServiceImpl", purchaseItemService.getClass().getSimpleName());
    }

    @Test
    public void purchaseEmptyCollection() {
        Receipt receipt = purchaseItemService.purchaseItems(new ArrayList<>());
        assertEquals(0, receipt.getReceiptItemList().size());
        assertEquals(0.0d, receipt.getTotalTaxes(), DELTA);
        assertEquals(0.0d, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void purchaseTaxFreeProduct() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(100, "book", 12.49, ProductType.BOOK, false), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(1, receipt.getReceiptItemList().size());
        assertEquals(100, receipt.getReceiptItemList().get(0).getProduct().getId());
        assertEquals(0.0d, receipt.getTotalTaxes(), DELTA);
        assertEquals(12.49, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void purchaseBasicTaxProduct() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(101, "music CD", 14.99, ProductType.OTHER, false), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(1, receipt.getReceiptItemList().size());
        assertEquals(101, receipt.getReceiptItemList().get(0).getProduct().getId());
        assertEquals(1.50, receipt.getTotalTaxes(), DELTA);
        assertEquals(16.49, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void purchaseImportedTaxFreeProduct() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(108, "box of imported chocolates", 11.25, ProductType.FOOD, true), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(1, receipt.getReceiptItemList().size());
        assertEquals(108, receipt.getReceiptItemList().get(0).getProduct().getId());
        assertEquals(0.60, receipt.getTotalTaxes(), DELTA);
        assertEquals(11.85, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void purchaseImportedBasicTaxProduct() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(104, "imported bottle of perfume", 47.50, ProductType.OTHER, true), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(1, receipt.getReceiptItemList().size());
        assertEquals(104, receipt.getReceiptItemList().get(0).getProduct().getId());
        assertEquals(7.15, receipt.getTotalTaxes(), DELTA);
        assertEquals(54.65, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void testInput1() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(100, "book", 12.49, ProductType.BOOK, false), 1));
        itemList.add(new Item(new Product(101, "music CD", 14.99, ProductType.OTHER, false), 1));
        itemList.add(new Item(new Product(102, "chocolate bar", 0.85, ProductType.FOOD, false), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(3, receipt.getReceiptItemList().size());
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 100));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 101));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 102));

        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(12.49, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(1.50, DELTA))),
                        hasProperty("amount", is(closeTo(16.49, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(0.85, DELTA)))
                )

        ));
        assertEquals(1.50, receipt.getTotalTaxes(), DELTA);
        assertEquals(29.83, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void testInput2() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(103, "imported box of chocolates", 10.00, ProductType.FOOD, true), 1));
        itemList.add(new Item(new Product(104, "imported bottle of perfume", 47.50, ProductType.OTHER, true), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(2, receipt.getReceiptItemList().size());
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 103));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 104));

        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.50, DELTA))),
                        hasProperty("amount", is(closeTo(10.50, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(7.15, DELTA))),
                        hasProperty("amount", is(closeTo(54.65, DELTA)))
                )

        ));
        assertEquals(7.65, receipt.getTotalTaxes(), DELTA);
        assertEquals(65.15, receipt.getTotalAmount(), DELTA);
    }

    @Test
    public void testInput3() {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(new Product(105, "imported bottle of perfume", 27.99, ProductType.OTHER, true), 1));
        itemList.add(new Item(new Product(106, "bottle of perfume", 18.99, ProductType.OTHER, false), 1));
        itemList.add(new Item(new Product(107, "package of headache pills", 9.75, ProductType.MEDICAL, false), 1));
        itemList.add(new Item(new Product(108, "box of imported chocolates", 11.25, ProductType.FOOD, true), 1));

        Receipt receipt = purchaseItemService.purchaseItems(itemList);
        assertEquals(4, receipt.getReceiptItemList().size());
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 105));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 106));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 107));
        assertTrue(receipt.getReceiptItemList().stream().anyMatch(receiptItem -> receiptItem.getProduct().getId() == 108));

        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(4.20, DELTA))),
                        hasProperty("amount", is(closeTo(32.19, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(1.9, DELTA))),
                        hasProperty("amount", is(closeTo(20.89, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.0d, DELTA))),
                        hasProperty("amount", is(closeTo(9.75, DELTA)))
                )

        ));
        assertThat(receipt.getReceiptItemList(), hasItem(
                allOf(
                        hasProperty("quantity", is(1)),
                        hasProperty("salesTax", is(closeTo(0.60, DELTA))),
                        hasProperty("amount", is(closeTo(11.85, DELTA)))
                )

        ));
        assertEquals(6.70, receipt.getTotalTaxes(), DELTA);
        assertEquals(74.68, receipt.getTotalAmount(), DELTA);
    }

}