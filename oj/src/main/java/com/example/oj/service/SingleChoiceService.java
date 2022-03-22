package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.SingleChoice;
import com.example.oj.mapper.SingleChoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class SingleChoiceService extends ServiceImpl<SingleChoiceMapper, SingleChoice> {

    @Autowired(required = false)
    private SingleChoiceMapper singleChoiceMapper;

}
