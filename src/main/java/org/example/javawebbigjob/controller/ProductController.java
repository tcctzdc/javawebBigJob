package org.example.javawebbigjob.controller;

import org.apache.ibatis.annotations.Update;
import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        int offset = (page - 1) * size;
        List<Product> products = productService.findByPage(offset, size);
        int total = productService.countAll();
        
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", total);
        response.put("currentPage", page);
        response.put("pageSize", size);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        Product product = productService.findById(id);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Product product) {
       try {
           productService.add(product);
           return ResponseEntity.ok("商品添加成功");
       }catch (Exception e){
           return ResponseEntity.status(500).body("添加商品失败: " + e.getMessage());
       }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Product product) {
        try {
            productService.update(product);
            return ResponseEntity.ok("商品更新成功");
        }catch (Exception e){
            return ResponseEntity.status(500).body("商品更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("商品删除成功");
        }catch (Exception e){
            return ResponseEntity.status(500).body("删除商品失败" + e.getMessage());
        }
    }

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> ids) {
        try {
            productService.deleteBatch(ids);
            return ResponseEntity.ok("批量删除成功");
        } catch (Exception e){
            return ResponseEntity.status(500).body("批量删除失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            productService.updateStatus(id, status);
            return ResponseEntity.ok("状态更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("状态更新失败: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String parentCategory,
            @RequestParam(required = false) String childCategory) {
        try {
            logger.info("Searching products with name: {}, parentCategory: {}, childCategory: {}", 
                name, parentCategory, childCategory);
            List<Product> products = productService.searchProducts(name, parentCategory, childCategory);
            logger.info("Found {} products", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error searching products", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null) {
                return ResponseEntity.badRequest().build();
            }
            productService.updateStock(id, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating product stock", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}