package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.Judge;
import com.example.oj.mapper.JudgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class JudgeService extends ServiceImpl<JudgeMapper, Judge> {

    @Autowired(required = false)
    private JudgeMapper judgeMapper;

}
