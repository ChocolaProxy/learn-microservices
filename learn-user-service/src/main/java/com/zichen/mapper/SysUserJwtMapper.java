package com.zichen.mapper;

import com.zichen.pojo.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserJwtMapper {

    @Select("select * from sys_user where username = #{username}")
    SysUserEntity findByUsername(String username);
}
