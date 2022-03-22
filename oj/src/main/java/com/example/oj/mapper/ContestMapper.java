package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.Contest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface ContestMapper extends BaseMapper<Contest> {


    @Delete("delete from sys_user_exam where exam_id = #{id}")
    void deleteByExamId(@Param(value = "id") String id);

    @Delete("delete from sys_user_exam where username = #{username}")
    void deleteByUsername(@Param(value = "username") String username);
}
