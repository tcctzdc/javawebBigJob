package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/product-sales")
    public List<Map<String, Object>> getProductSales() {
        return dashboardService.getProductSales();
    }

    @GetMapping("/deliveries")
    public List<Map<String, Object>> getDeliveries(){
        return dashboardService.getDeliveries();
    }

    @GetMapping("/product-prices")
    public List<Map<String, Object>> getProductPrices(){
        return dashboardService.getProductPrices();
    }

}
