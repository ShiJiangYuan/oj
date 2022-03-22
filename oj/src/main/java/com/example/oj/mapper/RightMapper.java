package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.Right;
import com.example.oj.entity.RoleRight;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface RightMapper extends BaseMapper<Right> {

    @Select("select * from sys_right where parent is null or parent = ''")
    List<Right> getFirstMenu();

    @Select("select * from sys_right where parent = #{rightId}")
    List<Right> getSonMenu(@Param("rightId") Integer id);

    @Select("select * from sys_role_right where role_id=#{roleId} and right_id = #{rightId}")
    RoleRight judgeByRoleIdAndRightId(@Param("roleId")Integer roleId, @Param("rightId") Integer rightId);
}
