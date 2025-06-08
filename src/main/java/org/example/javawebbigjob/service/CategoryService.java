package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Category;
import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.mapper.CategoryMapper;
import org.example.javawebbigjob.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    // 获取所有启用的父分类
    public List<Category> listAllParentCategories() {
        return categoryMapper.findAllParentCategories();
    }

    // 获取所有启用的子分类
    public List<Category> listAllChildCategories() {
        return categoryMapper.findAllChildCategories();
    }

    // 获取指定父分类下的所有启用的子分类
    public List<Category> listChildCategoriesByParentId(Long parentId) {
        return categoryMapper.findChildCategoriesByParentId(parentId);
    }

    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    public void add(Category category) {
        // 设置默认值
        if (category.getStatus() == null) {
            category.setStatus(1); // 默认启用
        }
        
        // 设置排序值
        if (category.getSortOrder() == null) {
            // 如果是父分类（parentId 为 null 或 0）
            if (category.getParentId() == null || category.getParentId() == 0) {
                category.setParentId(0L); // 确保父分类的 parentId 为 0
                Integer maxSortOrder = categoryMapper.getMaxSortOrderByParentId(0L);
                category.setSortOrder(maxSortOrder + 1);
            } else {
                // 如果是子分类，获取该父分类下最大的排序值
                Integer maxSortOrder = categoryMapper.getMaxSortOrderByParentId(category.getParentId());
                category.setSortOrder(maxSortOrder + 1);
            }
        }
        
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.update(category);
    }

    public void delete(Long id) {
        // 检查是否有子分类
        List<Category> children = categoryMapper.findChildCategoriesByParentId(id);
        if (!children.isEmpty()) {
            throw new RuntimeException("该分类下存在子分类，无法删除");
        }
        categoryMapper.delete(id);
    }

    public void deleteBatch(List<Long> ids) {
        // 检查是否有子分类
        for (Long id : ids) {
            List<Category> children = categoryMapper.findChildCategoriesByParentId(id);
            if (!children.isEmpty()) {
                throw new RuntimeException("分类ID " + id + " 下存在子分类，无法删除");
            }
        }
        categoryMapper.deleteBatch(ids);
    }

    public void updateStatus(Long id, Integer status, boolean syncChildren, boolean syncProducts) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        categoryMapper.update(category);

        // 如果是父分类且需要同步子分类
        if (syncChildren) {
            List<Category> children = categoryMapper.findChildCategoriesByParentId(id);
            for (Category child : children) {
                child.setStatus(status);
                categoryMapper.update(child);
                
                // 同步更新该子分类下的所有商品状态
                List<Product> products = productMapper.findByCategoryId(child.getId());
                for (Product product : products) {
                    Product updateProduct = new Product();
                    updateProduct.setId(product.getId());
                    updateProduct.setStatus(status);
                    updateProduct.setUpdateTime(new Date());
                    productMapper.update(updateProduct);
                }
            }
        }
        
        // 如果是子分类且需要同步商品
        if (syncProducts) {
            List<Product> products = productMapper.findByCategoryId(id);
            for (Product product : products) {
                Product updateProduct = new Product();
                updateProduct.setId(product.getId());
                updateProduct.setStatus(status);
                updateProduct.setUpdateTime(new Date());
                productMapper.update(updateProduct);
            }
        }
    }

    public void updateSortOrder(Long id, Integer sortOrder) {
        Category category = new Category();
        category.setId(id);
        category.setSortOrder(sortOrder);
        categoryMapper.update(category);
    }
}
