package com.example.oj.controller;

import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.entity.Contest;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author sjy
 * @date 2022/3/9
 **/
@RestController
@CrossOrigin
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;


    /**
     * 交卷
     * @param data
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, List<String>> data) {
        String radios = "";
        for (int i = 1; i < data.get("radio").size(); i++) {
            radios += data.get("radio").get(i) + ";";
        }
        String fills = "";
        for (int i = 1; i < data.get("fill").size(); i++) {
            fills += data.get("fill").get(i) + ";";
        }
        String judges = "";
        for (int i = 1; i < data.get("judge").size(); i++) {
            String tmp = data.get("judge").get(i).replaceAll(";","，");
            judges+= tmp + ";";
        }
        String programmes = "";
        Integer score = Integer.parseInt(data.get("score").get(0));
        String username = data.get("username").get(0);
        String examId = data.get("examId").get(0);
        try{
            contestService.save(new Contest(username,examId,radios,judges,fills,programmes,score));
            return Result.success();
        }catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }
}
