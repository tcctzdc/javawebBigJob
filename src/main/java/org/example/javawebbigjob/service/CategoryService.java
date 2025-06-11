package org.example.javawebbigjob.service;

import org.example.javawebbigjob.entity.Category;
import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.mapper.CategoryMapper;
import org.example.javawebbigjob.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

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
        
        // 获取所有已使用的ID
        List<Long> usedIds = categoryMapper.getAllIds();
        
        // 找到第一个可用的ID
        Long nextId = 1L;
        for (Long id : usedIds) {
            if (id > nextId) {
                break;
            }
            nextId = id + 1;
        }
        
        // 设置新分类的ID
        category.setId(nextId);
        
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

    public List<Category> findByPage(int offset, int size){return categoryMapper.findByPage(offset, size);}

    public int countAll(){return categoryMapper.countAll();}

    public List<Category> listAll() {
        return categoryMapper.findAll();
    }

    public List<Category> getCategoryTree() {
        try {
            // 获取所有分类
            List<Category> allCategories = categoryMapper.findAll();
            logger.info("Found {} categories", allCategories.size());

            // 创建分类ID到分类对象的映射
            Map<Long, Category> categoryMap = new HashMap<>();
            List<Category> rootCategories = new ArrayList<>();

            // 将所有分类放入Map
            for (Category category : allCategories) {
                categoryMap.put(category.getId(), category);
                // 如果是根分类（parent_id 为 null 或 0），添加到根分类列表
                if (category.getParentId() == null || category.getParentId() == 0) {
                    rootCategories.add(category);
                }
            }

            // 构建树形结构
            for (Category category : allCategories) {
                // 如果不是根分类，将其添加到父分类的children列表中
                if (category.getParentId() != null && category.getParentId() > 0) {
                    Category parent = categoryMap.get(category.getParentId());
                    if (parent != null) {
                        if (parent.getChildren() == null) {
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(category);
                    }
                }
            }

            logger.info("Built category tree with {} root categories", rootCategories.size());
            return rootCategories;
        } catch (Exception e) {
            logger.error("Error building category tree: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to build category tree", e);
        }
    }
}
