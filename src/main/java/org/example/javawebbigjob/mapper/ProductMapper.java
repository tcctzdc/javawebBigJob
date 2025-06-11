package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product")
    List<Product> findAll();

    @Select("SELECT * FROM product LIMIT #{size} OFFSET #{offset}")
    List<Product> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product findById(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM product")
    int countAll();

    @Insert("INSERT INTO product (name, category_id, description, price, stock, image_url, status, create_time) " +
            "VALUES (#{name}, #{categoryId}, #{description}, #{price}, #{stock}, #{imageUrl}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Product product);

    @Update({
            "<script>",
            "UPDATE product",
            "<set>",
            "<if test='name != null'>name = #{name},</if>",
            "<if test='price != null'>price = #{price},</if>",
            "<if test='stock != null'>stock = #{stock},</if>",
            "<if test='imageUrl != null'>image_url = #{imageUrl},</if>",
            "<if test='categoryId != null'>category_id = #{categoryId},</if>",
            "<if test='description != null'>description = #{description},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    void update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    void delete(@Param("id") Long id);

    @Delete({
            "<script>",
            "DELETE FROM product WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteBatch(@Param("ids") List<Long> ids);

    @Select("SELECT * FROM product WHERE category_id = #{categoryId}")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Select({
        "<script>",
        "SELECT * FROM product",
        "WHERE 1=1",
        "<if test='parentCategory != null and parentCategory != \"\"'>",
        "AND parent_category LIKE CONCAT('%', #{parentCategory}, '%')",
        "</if>",
        "<if test='childCategory != null and childCategory != \"\"'>",
        "AND child_category LIKE CONCAT('%', #{childCategory}, '%')",
        "</if>",
        "<if test='name != null and name != \"\"'>",
        "AND name LIKE CONCAT('%', #{name}, '%')",
        "</if>",
        "</script>"
    })
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "price", column = "price"),
        @Result(property = "stock", column = "stock"),
        @Result(property = "parentCategory", column = "parent_category"),
        @Result(property = "childCategory", column = "child_category")
    })
    List<Product> search(
        @Param("name") String name,
        @Param("parentCategoryId") Long parentCategoryId,
        @Param("childCategoryId") Long childCategoryId
    );

    @Update("UPDATE product SET stock = #{stock} WHERE id = #{id}")
    void updateStock(@Param("id") Long id, @Param("stock") int stock);
}

