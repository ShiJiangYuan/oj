package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.Programme;
import com.example.oj.mapper.ProgrammeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class ProgrammeService extends ServiceImpl<ProgrammeMapper, Programme> {

    @Autowired(required = false)
    private ProgrammeMapper programmeMapper;

}
