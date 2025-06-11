package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    @Select("""
        SELECT 
            p.id AS productId,
            p.name AS productName,
            SUM(oi.quantity) AS totalSales
        FROM 
            order_item oi
        JOIN 
            product p ON oi.product_id = p.id
        GROUP BY 
            p.id, p.name
        ORDER BY 
            totalSales DESC
        """)
    List<Map<String, Object>> getProductSales();

    @Select("""
        SELECT 
            company AS company,
            COUNT(DISTINCT order_id) AS total
            FROM delivery
            GROUP BY company;
            """)
    List<Map<String, Object>> getDeliveries();

    @Select("""
        SELECT 
            p.id AS productId,
            p.name AS productName,
            SUM(oi.quantity * oi.unit_price)  AS totalPrices
        FROM 
            order_item oi
        JOIN 
            product p ON oi.product_id = p.id
        GROUP BY 
            p.id, p.name
        ORDER BY 
            totalPrices DESC
        """)
    List<Map<String, Object>> getProductPrices();
}
