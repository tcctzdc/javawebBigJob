package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.OrderItem;
import java.util.List;

@Mapper
public interface OrderItemMapper {

//    根据订单ID查询订单项
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "unitPrice", column = "unit_price"),
        @Result(property = "subtotal", column = "subtotal")
    })
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);


//      添加订单项
    @Insert("INSERT INTO order_item (order_id, product_id, quantity, unit_price, subtotal) " +
            "VALUES (#{orderId}, #{productId}, #{quantity}, #{unitPrice}, #{subtotal})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(OrderItem item);


//      删除指定订单的所有订单项
    @Delete("DELETE FROM order_item WHERE order_id = #{orderId}")
    void deleteByOrderId(@Param("orderId") Long orderId);


//      查询所有订单项
    @Select("SELECT * FROM order_item")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "unitPrice", column = "unit_price"),
        @Result(property = "subtotal", column = "subtotal")
    })
    List<OrderItem> findAll();

//     更新订单项
    @Update({
        "<script>",
        "UPDATE order_item",
        "<set>",
        "<if test='orderId != null'>order_id = #{orderId},</if>",
        "<if test='productId != null'>product_id = #{productId},</if>",
        "<if test='quantity != null'>quantity = #{quantity},</if>",
        "<if test='unitPrice != null'>unit_price = #{unitPrice},</if>",
        "<if test='subtotal != null'>subtotal = #{subtotal},</if>",
        "</set>",
        "WHERE id = #{id}",
        "</script>"
    })
    void update(OrderItem item);


//      删除单个订单项
    @Delete("DELETE FROM order_item WHERE id = #{id}")
    void delete(@Param("id") Long id);


//    批量删除订单项
    @Delete({
        "<script>",
        "DELETE FROM order_item WHERE id IN",
        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</script>"
    })
    void deleteBatch(@Param("ids") List<Long> ids);
}
