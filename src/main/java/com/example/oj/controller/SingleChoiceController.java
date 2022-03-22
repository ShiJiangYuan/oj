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
import com.example.oj.entity.Exam;
import com.example.oj.entity.SingleChoice;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.ExamService;
import com.example.oj.service.SingleChoiceService;
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
@RequestMapping("/radio")
public class SingleChoiceController {

    @Autowired
    private SingleChoiceService service;
    @Autowired
    private ExamService examService;

    /**
     * 组试卷使用title模糊查询
     * @param title
     * @return
     */
    @PostMapping("/queryByLike/{title}")
    public Result query(@PathVariable String title){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("title",title);
        try{
            List<SingleChoice> list = service.list(queryWrapper);
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
        List<SingleChoice>  singleChoices = CollUtil.newArrayList();
        for (List<Object> row : list) {
            SingleChoice singleChoice = new SingleChoice();
            singleChoice.setTitle(row.get(0).toString());
            singleChoice.setPlanA(row.get(1).toString());
            singleChoice.setPlanB(row.get(2).toString());
            singleChoice.setPlanC(row.get(3).toString());
            singleChoice.setPlanD(row.get(4).toString());
            singleChoice.setSure(row.get(5).toString());
            if(row.size() > 7){
                singleChoice.setSource(row.get(6).toString());
            }
            singleChoices.add(singleChoice);
        }
        try {
            service.saveBatch(singleChoices);
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
            //删除之前需要先判断试题是否作为试卷
            QueryWrapper<Exam> wrapper = new QueryWrapper<>();
            wrapper.like("radio",id+";");
            List<Exam> list = examService.list(wrapper);
            if(!list.isEmpty()) {
                return Result.error("409","题目已加入试卷，目前不可删除");
            }
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
            UpdateWrapper<SingleChoice> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",form.get("id"));
            updateWrapper.set("title",form.get("title"));
            updateWrapper.set("plan_a",form.get("planA"));
            updateWrapper.set("plan_b",form.get("planB"));
            updateWrapper.set("plan_c",form.get("planC"));
            updateWrapper.set("plan_d",form.get("planD"));
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
            SingleChoice singleChoice = new SingleChoice(form.get("title"),form.get("planA"),form.get("planB"),form.get(
                    "planC"), form.get("planD"),form.get("sure"),form.get("source"));
            service.save(singleChoice);
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
    public IPage<SingleChoice> findPage(@RequestParam LinkedHashMap<String,String> pageNum,
                                        @RequestParam LinkedHashMap<String,String> pageSize,
                                        @RequestParam LinkedHashMap<String,String> title){
        IPage<SingleChoice> page = new Page<>(Integer.parseInt(pageNum.get("pageNum")),
                Integer.parseInt(pageSize.get("pageSize")));
        QueryWrapper<SingleChoice> queryWrapper = new QueryWrapper<>();
        if(title.get("title") != null){
            queryWrapper.like("title",title.get("title"));
        }
        queryWrapper.orderByDesc("create_time");
        return service.page(page,queryWrapper);
    }

}
