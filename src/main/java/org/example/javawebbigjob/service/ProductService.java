package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

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
}
