package org.example.javawebbigjob.controller;

import org.example.javawebbigjob.entity.Category;
import org.example.javawebbigjob.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size){
        int offset = (page - 1)/size;
        List<Category> categories = categoryService.findByPage(offset, size);
        int total = categoryService.countAll();

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("total", total);
        response.put("currentPage", page);
        response.put("pageSize", size);
        return ResponseEntity.ok(response);
    }

    // 获取所有启用的父分类
    @GetMapping("/parents")
    public ResponseEntity<List<Category>> listParentCategories() {
        try {
            logger.info("\nFetching all parent categories");
            List<Category> categories = categoryService.listAllParentCategories();
            logger.info("Found {} parent categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching parent categories", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 获取所有启用的子分类
    @GetMapping("/children")
    public ResponseEntity<List<Category>> listChildCategories() {
        try {
            logger.info("\nFetching all child categories");
            List<Category> categories = categoryService.listAllChildCategories();
            logger.info("Found {} child categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching child categories", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 获取指定父分类下的所有启用的子分类
    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<List<Category>> listChildCategoriesByParentId(@PathVariable Long parentId) {
        try {
            logger.info("\nFetching child categories for parent ID: {}", parentId);
            List<Category> categories = categoryService.listChildCategoriesByParentId(parentId);
            logger.info("Found {} child categories for parent ID: {}", categories.size(), parentId);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching child categories for parent ID: " + parentId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Category category) {
        try {
            logger.info("\nAdding new category: {}", category.getName());
            categoryService.add(category);
            return ResponseEntity.ok("分类添加成功");
        } catch (Exception e) {
            logger.error("Error adding category", e);
            return ResponseEntity.status(500).body("添加分类失败: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Category category) {
        try {
            logger.info("\nUpdating category ID: {}", category.getId());
            categoryService.update(category);
            return ResponseEntity.ok("分类更新成功");
        } catch (Exception e) {
            logger.error("Error updating category", e);
            return ResponseEntity.status(500).body("更新分类失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            logger.info("\nDeleting category ID: {}", id);
            categoryService.delete(id);
            return ResponseEntity.ok("分类删除成功");
        } catch (Exception e) {
            logger.error("Error deleting category", e);
            return ResponseEntity.status(500).body("删除分类失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteBatch(@RequestBody List<Long> ids) {
        try {
            logger.info("\nBatch deleting categories: {}", ids);
            categoryService.deleteBatch(ids);
            return ResponseEntity.ok("批量删除成功");
        } catch (Exception e) {
            logger.error("Error batch deleting categories", e);
            return ResponseEntity.status(500).body("批量删除失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
        @PathVariable Long id,
        @RequestParam Integer status,
        @RequestParam(required = false, defaultValue = "false") boolean syncChildren,
        @RequestParam(required = false, defaultValue = "false") boolean syncProducts
    ) {
        try {
            logger.info("\nUpdating status for category ID: {} to {} (syncChildren: {}, syncProducts: {})",
                id, status, syncChildren, syncProducts);
            categoryService.updateStatus(id, status, syncChildren, syncProducts);
            return ResponseEntity.ok("状态更新成功");
        } catch (Exception e) {
            logger.error("Error updating category status", e);
            return ResponseEntity.status(500).body("状态更新失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/sort")
    public ResponseEntity<String> updateSortOrder(@PathVariable Long id, @RequestParam Integer sortOrder) {
        try {
            logger.info("\nUpdating sort order for category ID: {} to {}", id, sortOrder);
            categoryService.updateSortOrder(id, sortOrder);
            return ResponseEntity.ok("排序更新成功");
        } catch (Exception e) {
            logger.error("Error updating category sort order", e);
            return ResponseEntity.status(500).body("排序更新失败: " + e.getMessage());
        }
    }

    @GetMapping("/tree")
    public ResponseEntity<List<Category>> getCategoryTree() {
        try {
            logger.info("Fetching category tree");
            List<Category> categories = categoryService.getCategoryTree();
            logger.info("Found {} categories in tree", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching category tree: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}