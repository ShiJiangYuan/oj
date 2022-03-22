package com.example.oj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.dto.FillDTO;
import com.example.oj.dto.JudgeDTO;
import com.example.oj.dto.RadioDTO;
import com.example.oj.entity.*;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.ContestService;
import com.example.oj.service.ExamService;
import com.example.oj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sjy
 * @date 2022/2/24
 **/
@RestController
@CrossOrigin
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService service;
    @Autowired
    private UserService userService;

    @Autowired
    private ContestService contestService;


    /**
     * 获取各个模块每道题目的分析
     * @param id
     * @return
     */
    @PostMapping("/getDetailScore/{id}")
    public Result getDetailScore(@PathVariable String id) {
        try{
            JSONObject jsonObject = getDetail(id);
            return Result.success(jsonObject);
        }catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
    }

    /**
     * 获取每个题目的答题情况
     * @param id
     * @return
     */
    private JSONObject getDetail (String id) {
        //得到试卷的信息
        Exam exam = service.getById(id);
        //获得考试信息
        QueryWrapper<Contest> contestQueryWrapper = new QueryWrapper<>();
        contestQueryWrapper.eq("exam_id",id);
        List<Contest> contestList = contestService.list(contestQueryWrapper);
        //存储对象
        JSONObject jsonObject = new JSONObject();
        //分数
        Integer score = 0;
        //划分题目的正确答案（不包括简答题）
        //选择题
        if(!"".equals(exam.getRadio())){
            //划分得到题目
            String[] radio = exam.getRadio().split(";");
            //计算得到选择题的总分数
            score += radio.length * Integer.parseInt(exam.getRadioValue());
            //得到每个题目所有信息
            List<SingleChoice> radios = service.getRadios(radio);
            //设置要显示再页面的信息
            List<RadioDTO> radioDTO = new ArrayList(radios.size());
            for (SingleChoice singleChoice : radios) {
                RadioDTO radioTemp = new RadioDTO();
                BeanUtil.copyProperties(singleChoice, radioTemp, true);
                radioDTO.add(radioTemp);
            }
            //初始化每个选项选择的人数
            for (RadioDTO dto : radioDTO) {
                dto.setNumberA(0);
                dto.setNumberB(0);
                dto.setNumberC(0);
                dto.setNumberD(0);
            }
            //遍历每份试卷的
            for (Contest contest : contestList) {
                if(contest.getRadio()== null){
                    continue;
                }
                String[] split = contest.getRadio().split(";");
                //循环遍历选项是否正确
                for (int i = 0; i < split.length; i++) {
                    if(split[i].toUpperCase().equals("A")) {
                        radioDTO.get(i).setNumberA(radioDTO.get(i).getNumberA() + 1);
                    } else if (split[i].toUpperCase().equals("B")){
                        radioDTO.get(i).setNumberB(radioDTO.get(i).getNumberB() + 1);
                    }else if (split[i].toUpperCase().equals("C")){
                        radioDTO.get(i).setNumberC(radioDTO.get(i).getNumberC() + 1);
                    }else if (split[i].toUpperCase().equals("D")){
                        radioDTO.get(i).setNumberD(radioDTO.get(i).getNumberD() + 1);
                    }
                }
            }
            jsonObject.put("radio",radioDTO);
        } else {
            jsonObject.put("radio",new ArrayList<>());
        }
        if(!"".equals(exam.getJudge())){
            //划分得到题目
            String[] radio = exam.getJudge().split(";");
            //计算得到选择题的总分数
            score += radio.length * Integer.parseInt(exam.getJudgeValue());
            //得到每个题目所有信息
            List<Judge> radios = service.getJudges(radio);
            //设置要显示再页面的信息
            List<JudgeDTO> radioDTO = new ArrayList(radios.size());
            for (Judge singleChoice : radios) {
                JudgeDTO radioTemp = new JudgeDTO();
                BeanUtil.copyProperties(singleChoice, radioTemp, true);
                radioDTO.add(radioTemp);
            }
            //初始化每个选项选择的人数
            for (JudgeDTO dto : radioDTO) {
                dto.setCorrectNumber(0);
                dto.setErrorNumber(0);
            }
            //遍历每份试卷的
            for (Contest contest : contestList) {
                if(contest.getJudge()== null){
                    continue;
                }
                String[] split = contest.getJudge().split(";");
                //循环遍历选项是否正确
                for (int i = 0; i < split.length; i++) {
                    if(split[i].equals(radios.get(i).getSure())) {
                        radioDTO.get(i).setCorrectNumber(radioDTO.get(i).getCorrectNumber() + 1);
                    } else {
                        radioDTO.get(i).setErrorNumber(radioDTO.get(i).getErrorNumber() + 1);
                    }
                }
            }
            jsonObject.put("judge",radioDTO);
        } else {
            jsonObject.put("judge",new ArrayList<>());
        }
        if(!"".equals(exam.getFill())){
            //划分得到题目
            String[] radio = exam.getFill().split(";");
            //计算得到选择题的总分数
            score += radio.length * Integer.parseInt(exam.getFillValue());
            //得到每个题目所有信息
            List<Fill> radios = service.getFills(radio);
            //设置要显示再页面的信息
            List<FillDTO> radioDTO = new ArrayList(radios.size());
            for (Fill singleChoice : radios) {
                FillDTO radioTemp = new FillDTO();
                BeanUtil.copyProperties(singleChoice, radioTemp, true);
                radioDTO.add(radioTemp);
            }
            //初始化每个选项选择的人数
            for (FillDTO dto : radioDTO) {
                dto.setCorrectNumber(0);
                dto.setErrorNumber(0);
            }
            //遍历每份试卷的
            for (Contest contest : contestList) {
                if(contest.getFill()== null){
                    continue;
                }
                String[] split = contest.getFill().split(";");
                //循环遍历选项是否正确
                for (int i = 0; i < split.length; i++) {
                    if(split[i].equals(radios.get(i).getSure())) {
                        radioDTO.get(i).setCorrectNumber(radioDTO.get(i).getCorrectNumber() + 1);
                    } else {
                        radioDTO.get(i).setErrorNumber(radioDTO.get(i).getErrorNumber() + 1);
                    }
                }
            }
            jsonObject.put("fill",radioDTO);
        } else {
            jsonObject.put("fill",new ArrayList<>());
        }
        jsonObject.put("totalValue",score);
        return jsonObject;
    }


    /**
     * 根据考试id查找出所有参加考试学生的成绩
     * @param id
     * @return
     */
    @PostMapping("/getUserList/{id}")
    public Result getUserList(@PathVariable String id) {
        QueryWrapper<Contest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id",id);
        queryWrapper.orderByDesc("score");
        List<Contest> list = contestService.list(queryWrapper);
        return Result.success(list);
    }


    /**
     * 查看考试结果
     * @param examId
     * @param username
     * @return
     */
    @PostMapping("/finished/{examId}/{username}")
    public Result finished(@PathVariable String examId,@PathVariable String username){
        try{
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // new Date()为获取当前系统时间
            String nowTime = df.format(new Date());
            QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",examId);
            // 考试结束才可以查看成绩
            queryWrapper.lt("end_time",nowTime);
            Exam one = service.getOne(queryWrapper);
            if (one != null) {
                //防止url直接访问，加一步验证
                User user = userService.getById(username);
                if(user==null|| !user.getRole().equals(one.getParticipate())) {
                    return Result.error("409","访问页面不存在");
                }
                QueryWrapper<Contest> wrapper = new QueryWrapper<>();
                wrapper.eq("username",username);
                wrapper.eq("exam_id",examId);
                Contest exam = contestService.getOne(wrapper);
                JSONObject examInfo = getExamInfo(examId);
                // 没有参加考试
                if(exam==null) {
                    contestService.save(new Contest(username,examId,0,"缺考"));
                    examInfo.put("score","0");
                    return Result.success("absent",examInfo);
                } else if("缺考".equals(exam.getNote())) {
                    examInfo.put("score",exam.getScore());
                    return Result.success("absent",examInfo);
                }
                examInfo.put("score",exam.getScore());
                if(!"".equals(exam.getRadio()) && exam.getRadio()!=null){
                    List<String> radios = Arrays.asList(exam.getRadio().split(";"));
                    examInfo.put("radioSure",radios);
                }
                if(!"".equals(exam.getJudge()) && exam.getJudge() != null){
                    List<String> judges = Arrays.asList(exam.getJudge().split(";"));
                    examInfo.put("judgeSure",judges);
                }
                if(!"".equals(exam.getFill()) && exam.getFill()!=null){
                    List<String> fills = Arrays.asList(exam.getFill().split(";"));
                    examInfo.put("fillSure",fills);
                }
                // 没处理简答题
                return Result.success(examInfo);
            } else {
                return Result.error(Constants.CODE_401,"您访问的页面不存在 或 考试暂未结束，考试结束可查看成绩");
            }
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }



    /**
     * 判断用户是否能参加本场考试
     * @param examId
     * @param role
     * @param username
     * @return
     */
    @PostMapping("/authenticate/{examId}/{role}/{username}")
    public Result authenticate(@PathVariable String examId,@PathVariable String role,@PathVariable String username){
        try{
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // new Date()为获取当前系统时间
            String nowTime = df.format(new Date());
            QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",examId);
            queryWrapper.eq("participate",role);
            queryWrapper.gt("end_time",nowTime);
            queryWrapper.le("start_time",nowTime);
            //判断试卷是否发布
            queryWrapper.eq("state",1);
            Exam one = service.getOne(queryWrapper);
            if (one != null) {
                QueryWrapper<Contest> wrapper = new QueryWrapper<>();
                wrapper.eq("username",username);
                wrapper.eq("exam_id",examId);
                Contest exam = contestService.getOne(wrapper);
                if(exam!=null) {
                 return Result.error(Constants.CODE_409,"您已提交试卷，每人只有一次作答机会，不可重复作答");
                }
                JSONObject examInfo = getExamInfo(examId);
                return Result.success(examInfo);
            } else {
                return Result.error(Constants.CODE_401,"您访问的页面不存在");
            }
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    /**
     * 修改试卷发布状态
     * @param id
     * @param state
     * @return
     */
    @PatchMapping("/examState/{id}/{state}")
    public Result examState(@PathVariable String id,@PathVariable Boolean state){
        try {
            UpdateWrapper<Exam> examUpdateWrapper = new UpdateWrapper<>();
            examUpdateWrapper.eq("id",id);
            examUpdateWrapper.set("state",state);
            return Result.success(service.update(null,examUpdateWrapper));
        }catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    /**
     * 相应角色获取自己的考试信息
     * @return
     */
    @PostMapping("/getByRole/{role}")
    public Result getByRole(@PathVariable String role) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("participate",role);
            queryWrapper.eq("state",true);
            queryWrapper.orderByDesc("id");
            return Result.success(service.list(queryWrapper));
        }catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 编辑试卷的权限
     * @return
     */
    @PostMapping("/editExams")
    public Result editExams(){
        return Result.success();
    }

    /**
     * 获取试卷的信息
     * @param id
     * @return
     */
    @GetMapping("getExamById/{id}")
    public Result getExamById(@PathVariable String id){
        JSONObject examInfo = getExamInfo(id);
        return Result.success(examInfo);
    }


    /**
     * 获得试卷的信息
     * @param id
     * @return
     */
    private JSONObject getExamInfo(String id) {
        // 获取exam对象
        Exam exam = service.getById(id);
        // 存form
        JSONObject jsonForm = new JSONObject();
        jsonForm.put("startTime",exam.getStartTime());
        jsonForm.put("endTime",exam.getEndTime());
        jsonForm.put("participate",exam.getParticipate());
        jsonForm.put("username",exam.getCreateUsername());
        // 创建Json对象，存储数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("form",jsonForm);
        jsonObject.put("radioValue",exam.getRadioValue());
        jsonObject.put("judgeValue",exam.getJudgeValue());
        jsonObject.put("fillValue",exam.getFillValue());
        jsonObject.put("name",exam.getName());
        // 划分数据
        if(!"".equals(exam.getProgrammeValue())){
            String[] pv = exam.getProgrammeValue().split(";");
            if(pv.length>0){
                Integer[] programmeValue = new Integer[pv.length+1];
                for(int i = 0; i < pv.length; i++) {
                    programmeValue[i] = Integer.parseInt(pv[i]);
                }
                jsonObject.put("programmeValue",programmeValue);
            }
        }
        // 题目需要获取对应的
        if(!"".equals(exam.getRadio())){
            String[] radio = exam.getRadio().split(";");
            List<SingleChoice> radios = service.getRadios(radio);
            jsonObject.put("radio",radios);
        }
        if(!"".equals(exam.getJudge())){
            String[] judge = exam.getJudge().split(";");
            List<Judge> judges = service.getJudges(judge);
            jsonObject.put("judge",judges);
        }
        if(!"".equals(exam.getFill())){
            String[] fill = exam.getFill().split(";");
            List<Fill> fills = service.getFills(fill);
            jsonObject.put("fill",fills);
        }
        if(!"".equals(exam.getProgramme())){
            String[] programme = exam.getProgramme().split(";");
            List<Programme> programmes = service.getProgrammes(programme);
            jsonObject.put("programme",programmes);
            jsonObject.put("programmeId",programme);
        }
        return jsonObject;
    }

    /**
     * 根据id删除考试
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id){
        try {
            service.removeById(id);
            contestService.deleteByExamId(id);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    /**
     * mybatis-plus分页查询(降序)，管理员可以查看所有人创建已经结束的考试
     * @param username
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/getFinishedExams")
    public IPage<Exam> getFinishedExams(@RequestParam LinkedHashMap<String,String> pageNum,
                                @RequestParam LinkedHashMap<String,String> pageSize,
                                @RequestParam LinkedHashMap<String,String> name,
                                @RequestParam LinkedHashMap<String,String> username){
        IPage<Exam> page = new Page<>(Integer.parseInt(pageNum.get("pageNum")),
                Integer.parseInt(pageSize.get("pageSize")));
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        if(name.get("name") != null && !"".equals(name.get("name"))){
            queryWrapper.like("name",name.get("name"));
        }
        if(username.get("username") != null && !"".equals(username.get("username"))){
            queryWrapper.eq("create_username",username.get("username"));
        }
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        String nowTime = df.format(new Date());
        queryWrapper.lt("end_time",nowTime);
        queryWrapper.orderByDesc("start_time");
        return service.page(page,queryWrapper);
    }





    /**
     * mybatis-plus分页查询(降序)，管理员可以查看所有人创建未结束的试卷
     * @param username
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/getExams")
    public IPage<Exam> getExams(@RequestParam LinkedHashMap<String,String> pageNum,
                               @RequestParam LinkedHashMap<String,String> pageSize,
                               @RequestParam LinkedHashMap<String,String> name,
                                @RequestParam LinkedHashMap<String,String> username){
        IPage<Exam> page = new Page<>(Integer.parseInt(pageNum.get("pageNum")),
                Integer.parseInt(pageSize.get("pageSize")));
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        if(name.get("name") != null && !"".equals(name.get("name"))){
            queryWrapper.like("name",name.get("name"));
        }
        if(username.get("username") != null && !"".equals(username.get("username"))){
            queryWrapper.eq("create_username",username.get("username"));
        }
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        String nowTime = df.format(new Date());
        queryWrapper.ge("end_time",nowTime);
        queryWrapper.orderByDesc("start_time");
        return service.page(page,queryWrapper);
    }


    /**
     * 修改试卷
     * @param exam
     * @return
     */
    @PutMapping("/save")
    public Result modify(@RequestBody Exam exam){
        try{
            service.updateById(exam);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 创建试卷
     * @param exam
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Exam exam){
        try{
            service.save(exam);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

}
