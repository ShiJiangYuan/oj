package com.example.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/10
 **/

public interface ExamMapper extends BaseMapper<Exam> {


    List<SingleChoice> getRadios(@Param("radio")String[] radio);

    List<Judge> getJudges(@Param("judge")String[] judge);

    List<Fill> getFills(@Param("fill")String[] fill);

    List<Programme> getProgrammes(@Param("programme")String[] programme);
}
