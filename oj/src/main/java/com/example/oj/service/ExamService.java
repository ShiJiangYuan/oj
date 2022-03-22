package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.*;
import com.example.oj.mapper.ExamMapper;
import com.example.oj.mapper.FillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class ExamService extends ServiceImpl<ExamMapper, Exam> {

    @Autowired(required = false)
    private ExamMapper examMapper;

    public List<SingleChoice> getRadios(String[] radio) {
        return examMapper.getRadios(radio);
    }

    public List<Judge> getJudges(String[] judge) {
        return examMapper.getJudges(judge);
    }

    public List<Fill> getFills(String[] fill) {
        return examMapper.getFills(fill);
    }

    public List<Programme> getProgrammes(String[] programme) {
        return examMapper.getProgrammes(programme);
    }
}
