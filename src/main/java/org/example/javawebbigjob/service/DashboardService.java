package org.example.javawebbigjob.service;

import org.example.javawebbigjob.mapper.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    //  产品销量统计
    public List<Map<String, Object>> getProductSales() {
        return dashboardMapper.getProductSales();
    }

    //  快递公司订单统计
    public List<Map<String, Object>> getDeliveries(){
        return dashboardMapper.getDeliveries();
    }

    // 产品销量价值统计
    public List<Map<String, Object>> getProductPrices() {
        return dashboardMapper.getProductPrices();
    }
}
