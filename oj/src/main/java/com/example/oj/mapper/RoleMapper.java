package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface RoleMapper extends BaseMapper<Role> {


    @Select("select * from sys_role where id = #{roleId}")
    Role getRoleById(@Param("roleId") Integer roleId);

    @Delete("delete from sys_role where id = #{roleId}")
    void deleteByRoleId(@Param("roleId")Integer roleId);

    @Select("select id from sys_role where name = (select role from sys_user where username = #{username})")
    Integer getRoleId(@Param("username")String username);
}
