package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.entity.Category;
import org.example.javawebbigjob.mapper.ProductMapper;
import org.example.javawebbigjob.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Product> listAll() { 
        return productMapper.findAll(); 
    }

    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    public void add(Product product) {
        // 设置默认值
        if (product.getStatus() == null) {
            product.setStatus(1); // 默认上架
        }
        product.setCreateTime(new Date());
        productMapper.insert(product);
    }

    public void update(Product product) {
        product.setUpdateTime(new Date());
        productMapper.update(product);
    }

    public void delete(Long id) {
        productMapper.delete(id);
    }

    public void deleteBatch(List<Long> ids) {
        productMapper.deleteBatch(ids);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productMapper.findByCategoryId(categoryId);
    }

    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        productMapper.update(product);
    }

    public List<Product> findByPage(int offset, int size){
        return productMapper.findByPage(offset, size);
    }

    public int countAll(){
        return productMapper.countAll();
    }

    public List<Product> searchProducts(String name, String parentCategory, String childCategory) {
        if (name == null && parentCategory == null && childCategory == null) {
            return productMapper.findAll();
        }

        // 如果指定了父分类，先获取父分类ID
        Long parentCategoryId = null;
        if (parentCategory != null && !parentCategory.trim().isEmpty()) {
            Category parent = categoryMapper.findByName(parentCategory.trim());
            if (parent != null) {
                parentCategoryId = parent.getId();
            }
        }

        // 如果指定了子分类，先获取子分类ID
        Long childCategoryId = null;
        if (childCategory != null && !childCategory.trim().isEmpty()) {
            Category child = categoryMapper.findByName(childCategory.trim());
            if (child != null) {
                childCategoryId = child.getId();
            }
        }

        return productMapper.search(name, parentCategoryId, childCategoryId);
    }

    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        int newStock = product.getStock() + quantity;
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        productMapper.updateStock(productId, newStock);
    }
}
