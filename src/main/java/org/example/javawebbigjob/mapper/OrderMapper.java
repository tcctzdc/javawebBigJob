package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Order;
import org.example.javawebbigjob.entity.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM `order` LIMIT #{size} OFFSET #{offset}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "totalPrice", column = "total_price"),
        @Result(property = "status", column = "status")
    })
    List<Order> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM `order`")
    int countAll();

    @Select("SELECT * FROM `order`")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "totalPrice", column = "total_price"),
        @Result(property = "status", column = "status")
    })
    List<Order> findAll();

    @Select("SELECT * FROM `order` WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "totalPrice", column = "total_price"),
        @Result(property = "status", column = "status")
    })
    Order findById(Long id);

    @Insert("INSERT INTO `order` (order_no, user_id, create_time, total_price, status) " +
            "VALUES (#{orderNo}, #{userId}, #{createTime}, #{totalPrice}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);

    @Update({
        "<script>",
        "UPDATE `order`",
        "<set>",
        "<if test='orderNo != null'>order_no = #{orderNo},</if>",
        "<if test='userId != null'>user_id = #{userId},</if>",
        "<if test='createTime != null'>create_time = #{createTime},</if>",
        "<if test='totalPrice != null'>total_price = #{totalPrice},</if>",
        "<if test='status != null'>status = #{status},</if>",
        "</set>",
        "WHERE id = #{id}",
        "</script>"
    })
    void update(Order order);

    @Delete("DELETE FROM `order` WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT * FROM `order` WHERE user_id = #{userId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "totalPrice", column = "total_price"),
        @Result(property = "status", column = "status")
    })
    List<Order> findByUserId(Long userId);

    @Select({
        "<script>",
        "SELECT * FROM `order`",
        "<where>",
        "<if test='orderNo != null and orderNo != \"\"'>",
        "AND order_no LIKE CONCAT('%', #{orderNo}, '%')",
        "</if>",
        "<if test='userId != null'>",
        "AND user_id = #{userId}",
        "</if>",
        "<if test='status != null and status != \"\"'>",
        "AND status = #{status}",
        "</if>",
        "</where>",
        "</script>"
    })
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "totalPrice", column = "total_price"),
        @Result(property = "status", column = "status")
    })
    List<Order> search(@Param("orderNo") String orderNo, @Param("userId") Long userId, @Param("status") String status);

    /**
     * 获取快递公司订单统计
     * @return 快递公司订单统计列表
     */
    @Select("SELECT d.company_name as companyName, COUNT(o.id) as orderCount " +
            "FROM delivery d " +
            "LEFT JOIN orders o ON d.order_id = o.id " +
            "GROUP BY d.company_name " +
            "ORDER BY orderCount DESC")
    List<Map<String, Object>> getDeliveryOrders();
}

