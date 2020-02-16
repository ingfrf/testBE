package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Item {
    private @Getter
    Product product;
    private @Getter int quantity;
}
