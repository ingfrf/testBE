package org.example;


import org.example.model.Item;
import org.example.model.Product;
import org.example.model.Receipt;
import org.example.service.PrintReceiptService;
import org.example.enums.ProductType;
import org.example.service.PurchaseItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(SpringBootConsoleApplication.class);

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private PrintReceiptService printReceiptService;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");

        List<Item> item1 = new ArrayList<>();

        item1.add(new Item(new Product(100, "book", 12.49, ProductType.BOOK, false), 1));
        item1.add(new Item(new Product(101, "music CD", 14.99, ProductType.OTHER, false), 1));
        item1.add(new Item(new Product(102, "chocolate bar", 0.85, ProductType.FOOD, false), 1));

        List<Item> item2 = new ArrayList<>();

        item2.add(new Item(new Product(103, "imported box of chocolates", 10.00, ProductType.FOOD, true), 1));
        item2.add(new Item(new Product(104, "imported bottle of perfume", 47.50, ProductType.OTHER, true), 1));

        List<Item> item3 = new ArrayList<>();

        item3.add(new Item(new Product(105, "imported bottle of perfume", 27.99, ProductType.OTHER, true), 1));
        item3.add(new Item(new Product(106, "bottle of perfume", 18.99, ProductType.OTHER, false), 1));
        item3.add(new Item(new Product(107, "package of headache pills", 9.75, ProductType.MEDICAL, false), 1));
        item3.add(new Item(new Product(108, "box of imported chocolates", 11.25, ProductType.FOOD, true), 1));

        List<List<Item>> testInput = new ArrayList<>();
        testInput.add(item1);
        testInput.add(item2);
        testInput.add(item3);

        for (List<Item> items : testInput) {
            Receipt receipt = purchaseItemService.purchaseItems(items);
            printReceiptService.printReceipt(receipt);
            LOG.info("=============================================");
        }
    }
}