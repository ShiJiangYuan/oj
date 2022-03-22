package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.Fill;
import com.example.oj.mapper.FillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class FillService extends ServiceImpl<FillMapper, Fill> {

    @Autowired(required = false)
    private FillMapper fillMapper;

}
