package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Delivery;
import org.example.javawebbigjob.entity.User;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    @Select("SELECT * FROM delivery LIMIT #{size} OFFSET #{offset}")
    List<Delivery> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM delivery")
    int countAll();


    @Select("SELECT * FROM delivery")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "courierName", column = "courier_name"),
        @Result(property = "company", column = "company"),
        @Result(property = "trackingNo", column = "tracking_no"),
        @Result(property = "status", column = "status"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<Delivery> findAll();

    @Select("SELECT * FROM delivery WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "courierName", column = "courier_name"),
        @Result(property = "company", column = "company"),
        @Result(property = "trackingNo", column = "tracking_no"),
        @Result(property = "status", column = "status"),
        @Result(property = "updateTime", column = "update_time")
    })
    Delivery findById(Long id);

    @Select("SELECT * FROM delivery WHERE order_id = #{orderId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "courierName", column = "courier_name"),
        @Result(property = "company", column = "company"),
        @Result(property = "trackingNo", column = "tracking_no"),
        @Result(property = "status", column = "status"),
        @Result(property = "updateTime", column = "update_time")
    })
    Delivery findByOrderId(Long orderId);

    @Insert("INSERT INTO delivery (order_id, courier_name, company, tracking_no, status, update_time) " +
            "VALUES (#{orderId}, #{courierName}, #{company}, #{trackingNo}, #{status}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Delivery delivery);

    @Update({
        "<script>",
        "UPDATE delivery",
        "<set>",
        "<if test='orderId != null'>order_id = #{orderId},</if>",
        "<if test='courierName != null and courierName != \"\"'>courier_name = #{courierName},</if>",
        "<if test='company != null'>company = #{company},</if>",
        "<if test='trackingNo != null and trackingNo != \"\"'>tracking_no = #{trackingNo},</if>",
        "<if test='status != null'>status = #{status},</if>",
        "<if test='updateTime != null'>update_time = #{updateTime},</if>",
        "</set>",
        "WHERE id = #{id}",
        "</script>"
    })
    void update(Delivery delivery);

    @Delete("DELETE FROM delivery WHERE id = #{id}")
    void delete(Long id);

    @Delete("DELETE FROM delivery WHERE order_id = #{orderId}")
    void deleteByOrderId(Long orderId);
}
