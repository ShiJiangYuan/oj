package com.example.oj.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.entity.Programme;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sjy
 * @date 2022/2/24
 **/
@RestController
@CrossOrigin
@RequestMapping("/programme")
public class ProgrammeController {

    @Autowired
    private ProgrammeService service;

    @PostMapping("/queryByLike/{title}")
    public Result query(@PathVariable String title){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("title",title);
        try{
            List<Programme> list = service.list(queryWrapper);
            return Result.success(list);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list = reader.read(1);
        List<Programme>  programmes = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Programme programme = new Programme();
            programme.setTitle(row.get(0).toString());
            programme.setSure(row.get(1).toString());
            if(row.size() > 2){
                programme.setSource(row.get(2).toString());
            }
            programmes.add(programme);
        }
        try {
            service.saveBatch(programmes);
        } catch (Exception e){
            return Result.error(Constants.CODE_500,"系统错误");
        }
        return Result.success();
    }

    /**
     * 根据id删除题目
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id){
        try {
            service.removeById(id);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 修改题目
     * @param form
     * @return
     */
    @PutMapping("/add")
    public Result update(@RequestBody Map<String,String> form){
        try{
            UpdateWrapper<Programme> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",form.get("id"));
            updateWrapper.set("title",form.get("title"));
            updateWrapper.set("sure",form.get("sure"));
            updateWrapper.set("source",form.get("source"));
            service.update(null,updateWrapper);
            return Result.success("修改成功",null);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 添加题目
     * @param form
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Map<String,String> form){
        try{
            Programme programme = new Programme(form.get("title"),form.get("sure"),form.get("source"));
            service.save(programme);
            return Result.success("添加成功",null);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * mybatis-plus分页查询(降序)
     * @param
     * @return
     */
    @GetMapping("/page")
    public IPage<Programme> findPage(@RequestParam LinkedHashMap<String,String> pageNum,
                                        @RequestParam LinkedHashMap<String,String> pageSize,
                                        @RequestParam LinkedHashMap<String,String> title){
        IPage<Programme> page = new Page<>(Integer.parseInt(pageNum.get("pageNum")),
                Integer.parseInt(pageSize.get("pageSize")));
        QueryWrapper<Programme> queryWrapper = new QueryWrapper<>();
        if(title.get("title") != null){
            queryWrapper.like("title",title.get("title"));
        }
        queryWrapper.orderByDesc("create_time");
        return service.page(page,queryWrapper);
    }

}
