package org.example.service.impl;

import org.example.model.Product;
import org.example.model.ReceiptItem;
import org.example.service.ReceiptItemFactory;
import org.example.service.TaxCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiptItemFactoryImpl implements ReceiptItemFactory {
    private static final double BASIC_TAX = 0.1d;
    private static final double IMPORTED_TAX = 0.05d;

    @Autowired
    private TaxCalculatorService taxCalculatorService;

    public ReceiptItem createReceiptItem(Product product, int quantity) {
        double tax = 0.00d;
        switch (product.getProductType()) {
            case OTHER:
                tax = BASIC_TAX;
                break;
            case BOOK:
            case FOOD:
            case MEDICAL:
            default:
                break;
        }
        tax += product.isImported() ? IMPORTED_TAX : 0;
        double amount = taxCalculatorService.calculateAmountWithTax(product.getPrice(), quantity, tax);
        double salesTax = amount - product.getPrice();
        return new ReceiptItem(product, quantity, salesTax, amount);
    }
}
