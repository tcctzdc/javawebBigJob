package org.example.javawebbigjob.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminUserMapper {
    @Select("SELECT password FROM admin_user WHERE username = #{username}")
    String getPasswordByUsername(@Param("username") String username);
}
