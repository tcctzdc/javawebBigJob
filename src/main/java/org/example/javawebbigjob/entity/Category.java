package org.example.javawebbigjob.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Category {
    private Long id;
    private String name;
    
    @JsonProperty("parent_id")
    private Long parentId;
    
    private List<Category> children;
    
    private String description;
    
    @JsonProperty("sort_order")
    private Integer sortOrder;
    
    private Integer status; // 1=启用，0=禁用

    public Category() {
    }

    public Category(Long id, String name, Long parentId, String description, Integer sortOrder, Integer status) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.description = description;
        this.sortOrder = sortOrder;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
