package com.zichen.mapper;

import com.zichen.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {

    SysUser selectByUsername(@Param("username") String username);

    SysUser selectById(@Param("id") Long id);

    int insert(SysUser user);
}

