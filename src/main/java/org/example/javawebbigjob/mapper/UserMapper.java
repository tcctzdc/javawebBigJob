package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebbigjob.entity.Product;
import org.example.javawebbigjob.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);


    @Select("SELECT * FROM user LIMIT #{size} OFFSET #{offset}")
    List<User> findByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM user")
    int countAll();

    @Insert("INSERT INTO user (username, password, phone, address, real_name) " +
            "VALUES (#{username}, #{password}, #{phone}, #{address}, #{realName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("<script>"
            + "UPDATE user SET "
            + "username = #{username}, "
            + "<if test='password != null and password != \"\"'>"
            + "password = #{password}, "
            + "</if>"
            + "phone = #{phone}, "
            + "address = #{address}, "
            + "real_name = #{realName} "
            + "WHERE id = #{id}"
            + "</script>")
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

}
