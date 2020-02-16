package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.enums.ProductType;

@AllArgsConstructor
public class Product {
    private @Getter int id;
    private @Getter String description;
    private @Getter double price;
    private @Getter ProductType productType;
    private @Getter boolean isImported;
}
