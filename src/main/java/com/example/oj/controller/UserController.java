package com.example.oj.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.dto.UserDTO;
import com.example.oj.entity.Contest;
import com.example.oj.entity.User;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.ContestService;
import com.example.oj.service.MailService;
import com.example.oj.service.UserService;
import com.example.oj.utils.RandomCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "User接口")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private ContestService contestService;

    @Resource
    private MailService mailService;


    @Value("${oj.serverIp}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;

    // 文件存储路径
    @Value("${oj.avatar}")
    private String fileUploadPath;


    /**
     * 分配角色
     * @param username
     * @param role
     * @return
     */
    @PatchMapping("/allocationByUsername/{username}/{role}")
    public Result allocationByUsername(@PathVariable String username,@PathVariable String role){
        try{
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username",username);
            updateWrapper.set("role",role);
            userService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }



    /**
     * 文件上传接口（头像）
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/{username}")
    public String upload(@RequestParam MultipartFile file,@PathVariable String username) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);

        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 上传文件到磁盘
        file.transferTo(uploadFile);
        String url = serverIp + ":" + serverPort + "/user/file/" + fileUUID;
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        updateWrapper.set("avatar",url);
        userService.update(null,updateWrapper);
        return url;
    }
    /**
     * 文件下载接口（头像）   http://localhost:9090/user/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/file/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        //System.out.println(fileUploadPath);
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 修改邮箱
     * @param username
     * @param email
     * @return
     */
    @PatchMapping("/update/email/{username}/{email}")
    public Result updateEmailByUsername(@PathVariable String username,@PathVariable String email){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        updateWrapper.set("email",email);
        try {
            userService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 修改昵称
     * @param username
     * @param nickname
     * @return
     */
    @PatchMapping("/update/nickname/{username}/{nickname}")
    public Result updateNicknameByUsername(@PathVariable String username,@PathVariable String nickname){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        updateWrapper.set("nickname",nickname);
        try {
            userService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    @PatchMapping("/update/password/{username}/{password}")
    public Result updatePasswordByUsername(@PathVariable String username,@PathVariable String password){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        updateWrapper.set("password",password);
        try {
            userService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 找回密码
     * @param data
     * @param username
     * @return
     */
    @PatchMapping("/password/{username}")
    public Result updatePassword(@RequestBody  Map<String,Map<String,String>> data, @PathVariable String username){
        String password = data.get("data").get("password");
        String email = data.get("data").get("email");
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("username",username);
        updateWrapper.eq("email",email);
        updateWrapper.set("password",password);
        boolean update = userService.update(null, updateWrapper);
        if(update){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"账号或邮箱错误");
        }
    }


    @PostMapping("/sendEmail")
    public Result sendEmail(@RequestBody Map<String,Map<String,String>> info){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",info.get("data").get("username"));
        queryWrapper.eq("email",info.get("data").get("email"));
        User user = userService.getOne(queryWrapper);
        if(user == null){
            return Result.error("500","用户名或邮箱错误");
        }
        String code = RandomCode.randomCode();
        mailService.sendMailForActivationAccount(code,user.getEmail(),"find");
        return Result.success(code);
    }

    /**
     * 激活码激活
     * @param confirmCode 激活码
     * @return
     */
    @GetMapping("/activation/{confirmCode}")
    public Result activationAccount(@PathVariable String confirmCode){
        String account = userService.activationAccount(confirmCode);
        if("200".equals(account)){
            return Result.success();
        } else if("409".equals(account)){
            userService.deleteByconfirmCode(confirmCode);
            return Result.error(Constants.CODE_409,"链接失效，请重新注册");
        }
        return Result.error(Constants.CODE_410,"激活失败!您已激活，请不要重复激活！");
    }


    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        user.setState(false);
        user.setRole("普通用户");
        return Result.success(userService.register(user));
    }


    /**
     * 登录
     * @param data
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Map<String,String>> data){
        String username = data.get("data").get("username");
        String password = data.get("data").get("password");
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(username,password);
        return Result.success(dto);
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
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);
        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setRole(row.get(4).toString());
            user.setState(Boolean.parseBoolean(row.get(5).toString()));
            users.add(user);
        }
        try {
            userService.saveBatch(users);
        } catch (Exception e){
            return Result.error(Constants.CODE_500,"用户名已被占用！");
        }
        return Result.success();
    }


    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
        // ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("confirmCode", "确认码");
        writer.addHeaderAlias("role", "角色");
        writer.addHeaderAlias("state", "登录状态");
        writer.addHeaderAlias("avatar", "头像");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * 批量删除用户
     * @param usernames
     * @return
     */
    @DeleteMapping("/batchDelete")
    public Boolean deleteBatch(@RequestBody Map<String,List<String>> usernames){
        try {
            for (String s : usernames.get("usernames")) {
                contestService.deleteByUsername(s);
            }
            return userService.removeByIds(usernames.get("usernames"));
        }catch (Exception e) {
            return false;
        }
    }


    /**
     * 根据用户名删除
     * @param username
     * @return
     */
    @DeleteMapping("/delete/{username}")
    public Boolean deleteByUsername(@PathVariable String username){
        try{
            contestService.deleteByUsername(username);
            return userService.removeById(username);
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改用户登录状态
     * @param username
     * @param userState
     * @return
     */
    @PatchMapping("/state/{username}")
    public Boolean updateState(@RequestBody Map<String,Map<String,Boolean>> userState, @PathVariable String username){
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username);
        updateWrapper.set("state",userState.get("data").get("userState"));
        return userService.update(null, updateWrapper);
    }


    /**
     * 新增用户信息
     * @param user
     * @return
     */
    @PostMapping
    public Boolean insert(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User one = userService.getOne(queryWrapper);
        if(one != null) {
            return false;
        }
        return userService.save(user);
    }


    /**
     * 根据用户名修改昵称、邮箱
     * @param user
     * @return
     */
    @PutMapping
    public Boolean updateUser(@RequestBody User user){
        return userService.updateById(user);
    }


    /**
     * mybatis-plus分页查询(降序)
     * @param
     * @return
     */
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam LinkedHashMap<String,String> pageNum,
                                @RequestParam LinkedHashMap<String,String> pageSize,
                                @RequestParam LinkedHashMap<String,String> username){
        IPage<User> page = new Page<>(Integer.parseInt(pageNum.get("pageNum")),
                Integer.parseInt(pageSize.get("pageSize")));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(username.get("username") != null){
            queryWrapper.like("username",username.get("username"));
        }
        queryWrapper.orderByDesc("create_time");
        return userService.page(page,queryWrapper);
    }

}
