package com.pm.eventservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-client", url = "${application.service.product.url}")
public interface ProductClient {

    @GetMapping
    List<Map<String, Object>> getAllProducts();
}
