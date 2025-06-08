package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // 查询所有父分类（parent_id 为 null 或 0）
    @Select("SELECT id, name, parent_id, description, sort_order, status " +
            "FROM category WHERE (parent_id IS NULL OR parent_id = 0) ORDER BY status DESC, sort_order")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "parentId", column = "parent_id"),
        @Result(property = "description", column = "description"),
        @Result(property = "sortOrder", column = "sort_order"),
        @Result(property = "status", column = "status")
    })
    List<Category> findAllParentCategories();

    // 查询所有子分类
    @Select("SELECT id, name, parent_id, description, sort_order, status " +
            "FROM category WHERE parent_id > 0 ORDER BY parent_id, status DESC, sort_order")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "parentId", column = "parent_id"),
        @Result(property = "description", column = "description"),
        @Result(property = "sortOrder", column = "sort_order"),
        @Result(property = "status", column = "status")
    })
    List<Category> findAllChildCategories();

    // 查询指定父分类下的所有子分类
    @Select("SELECT id, name, parent_id, description, sort_order, status " +
            "FROM category WHERE parent_id = #{parentId} ORDER BY status DESC, sort_order")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "parentId", column = "parent_id"),
        @Result(property = "description", column = "description"),
        @Result(property = "sortOrder", column = "sort_order"),
        @Result(property = "status", column = "status")
    })
    List<Category> findChildCategoriesByParentId(@Param("parentId") Long parentId);

    @Select("SELECT id, name, parent_id, description, sort_order, status " +
            "FROM category WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "parentId", column = "parent_id"),
        @Result(property = "description", column = "description"),
        @Result(property = "sortOrder", column = "sort_order"),
        @Result(property = "status", column = "status")
    })
    Category findById(@Param("id") Long id);

    @Insert("INSERT INTO category (name, parent_id, description, sort_order, status) " +
            "VALUES (#{name}, #{parentId}, #{description}, #{sortOrder}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Category category);

    @Update({
            "<script>",
            "UPDATE category",
            "<set>",
            "<if test='name != null'>name = #{name},</if>",
            "<if test='parentId != null'>parent_id = #{parentId},</if>",
            "<if test='description != null'>description = #{description},</if>",
            "<if test='sortOrder != null'>sort_order = #{sortOrder},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void update(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(@Param("id") Long id);

    @Delete({
            "<script>",
            "DELETE FROM category WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteBatch(@Param("ids") List<Long> ids);

    // 获取指定父分类下最大的排序值
    @Select("SELECT COALESCE(MAX(sort_order), 0) FROM category WHERE parent_id = #{parentId}")
    Integer getMaxSortOrderByParentId(@Param("parentId") Long parentId);
}