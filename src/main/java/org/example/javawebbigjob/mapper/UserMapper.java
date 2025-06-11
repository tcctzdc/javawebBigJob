package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "realName", column = "real_name"),
        @Result(property = "createTime", column = "create_time")
    })
    List<User> findAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "realName", column = "real_name"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "createTime", column = "create_time")
    })
    User findById(Long id);

    @Select("SELECT * FROM user LIMIT #{size} OFFSET #{offset}")
    List<User> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM user")
    int countAll();

    @Insert("INSERT INTO user (username, password, phone, address, real_name, create_time) " +
            "VALUES (#{username}, #{password}, #{phone}, #{address}, #{realName}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update({
        "<script>",
        "UPDATE user",
        "<set>",
        "<if test='username != null'>username = #{username},</if>",
        "<if test='password != null'>password = #{password},</if>",
        "<if test='phone != null'>phone = #{phone},</if>",
        "<if test='address != null'>address = #{address},</if>",
        "<if test='realName != null'>real_name = #{realName},</if>",
        "<if test='createTime != null'>create_time = #{createTime},</if>",
        "</set>",
        "WHERE id = #{id}",
        "</script>"
    })
    void update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(@Param("id") Long id);

    @Delete({
        "<script>",
        "DELETE FROM user WHERE id IN ",
        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</script>"
    })
    void deleteBatch(@Param("ids") List<Long> ids);

    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{query}, '%')")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "role", column = "role")
    })
    List<User> searchByUsername(@Param("query") String query);

    @Select({
        "<script>",
        "SELECT * FROM user",
        "WHERE 1=1",
        "<if test='username != null and username != \"\"'>",
        "AND username LIKE CONCAT('%', #{username}, '%')",
        "</if>",
        "<if test='id != null and id != \"\"'>",
        "AND id = #{id}",
        "</if>",
        "ORDER BY id DESC",
        "</script>"
    })
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "realName", column = "real_name"),
        @Result(property = "createTime", column = "create_time")
    })
    List<User> search(@Param("username") String username, @Param("id") String id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "realName", column = "real_name"),
        @Result(property = "createTime", column = "create_time")
    })
    User findByUsername(@Param("username") String username);

}
