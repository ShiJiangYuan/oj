package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.Right;
import com.example.oj.entity.RoleRight;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface RoleRightMapper extends BaseMapper<RoleRight> {

    @Delete("delete from sys_role_right where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select right_id from sys_role_right where role_id = #{roleId}")
    List<Integer> getRights(@Param("roleId") Integer roleId);

    List<Right> getFirstMenu(@Param("rights") List<Integer> rights);

    List<Right> getSonMenu(@Param("rightId")Integer rightId,@Param("rights") List<Integer> rights);

    Boolean deleteByRightAndRole(@Param("roleId")Integer roleId,@Param("rightIds") List<Integer> rightIds);

    List<Integer> getAllRightChildren(@Param("rightId")Integer rightId);


}
