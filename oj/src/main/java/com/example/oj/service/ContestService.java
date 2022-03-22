package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.entity.Contest;
import com.example.oj.mapper.ContestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class ContestService extends ServiceImpl<ContestMapper, Contest> {

    @Autowired(required = false)
    private ContestMapper contestMapper;

    public void deleteByExamId(String id) {
        contestMapper.deleteByExamId(id);
    }

    public void deleteByUsername(String username) {
        contestMapper.deleteByUsername(username);
    }
}
