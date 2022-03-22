package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface UserMapper extends BaseMapper<User> {


    @Insert("insert sys_user(username,password,role,email,state,confirm_code,create_time) values(#{username}," +
            "#{password},#{role}," +
            "#{email}," +
            "#{state},#{confirmCode},#{createTime})")
    void register(User user);

    @Update("update sys_user set password = #{password},role =  #{role},email = #{email},confirm_code = " +
            "#{confirmCode}, create_time = #{createTime} where " +
            "username = " +
            "#{username} ")
    void registerUpdate(User user);

    @Delete("delete from sys_user where confirm_code = #{confirmCode}")
    void deleteByconfirmCode(@Param(value = "confirmCode") String confirmCode);
}
