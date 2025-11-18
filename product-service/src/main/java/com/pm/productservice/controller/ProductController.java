package com.pm.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<Map<String, Object>> getProducts() {

        List<Map<String, Object>> productsList = new ArrayList<>();

        Map<String, Object> product1 = new HashMap<>();
        product1.put("id", 101);
        product1.put("name", "Wireless Mouse");
        product1.put("price", 25.99);
        product1.put("inStock", true);
        productsList.add(product1);

        Map<String, Object> product2 = new HashMap<>();
        product2.put("id", 102);
        product2.put("name", "Mechanical Keyboard");
        product2.put("price", 119.50);
        product2.put("inStock", false);
        productsList.add(product2);

        return productsList;
    }

}
