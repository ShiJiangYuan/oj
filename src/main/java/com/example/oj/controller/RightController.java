package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.entity.Right;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author sjy
 * @date 2022/2/17
 **/
@RestController
@CrossOrigin
@RequestMapping("/right")
public class RightController {
    @Autowired
    private RightService rightService;

    @PostMapping("/rights")
    public Result getRights(){
        //权限分级
        List<Right> rightsGrade = rightService.getGrade();
        return Result.success(rightsGrade);
    }

    @PatchMapping("/icon/{id}")
    public Result updateIcon(@PathVariable Integer id, @RequestBody Map<String,String> data){
        String icon = data.get("data");
        UpdateWrapper<Right> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("icon",icon);
        try{
            rightService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @PatchMapping("/updateUM/{id}/{method}/{name}")
    public Result updateUM(@PathVariable String id,@RequestBody Map<String,String> url,@PathVariable String method,
                           @PathVariable String name){
        UpdateWrapper<Right> rightUpdateWrapper = new UpdateWrapper<>();
        rightUpdateWrapper.eq("id",id);
        rightUpdateWrapper.set("url",url.get("url"));
        rightUpdateWrapper.set("method",method);
        rightUpdateWrapper.set("name",name);
        try{
            rightService.update(null,rightUpdateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }
}
