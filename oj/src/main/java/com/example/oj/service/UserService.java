package com.example.oj.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.common.Constants;
import com.example.oj.dto.UserDTO;
import com.example.oj.entity.User;
import com.example.oj.exception.ServiceException;
import com.example.oj.mapper.UserMapper;
import com.example.oj.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Service
public class UserService extends ServiceImpl<UserMapper,User> {

    //private static final Log LOG = Log.get();

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private MailService mailService;


    @Value("${oj.email}")
    private String emailIp;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public UserDTO login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        queryWrapper.eq("state",true);
        User user;
        try {
            user = getOne(queryWrapper);
        }catch (Exception e){
            //LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
        if(user != null){
            UserDTO userDTO=new UserDTO();
            BeanUtil.copyProperties(user, userDTO, true);
            String token = TokenUtils.genToken(user.getUsername(), user.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误/您的账号已被禁用");
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public Object register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User queryUser;
        try {
            queryUser = getOne(queryWrapper);
        }catch (Exception e){
            //LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
        if(queryUser == null){
            try {
                // 雪花算法生成验证码
                String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();
                //  激活失效时间：24小时
                LocalDateTime ldt = LocalDateTime.now().plusDays(1);
                user.setCreateTime(ldt);
                user.setConfirmCode(confirmCode);
                //发送邮件
                String activationUrl = emailIp + "/activation?confirmCode=" + confirmCode;
                mailService.sendMailForActivationAccount(activationUrl,user.getEmail(),"register");
                userMapper.register(user);
                return null;
            } catch (Exception e) {
                throw new ServiceException(Constants.CODE_500,"服务器错误");
            }
        }else {
            boolean after = LocalDateTime.now().isAfter(queryUser.getCreateTime());
            // 激活码过期
            if(after && queryUser.getConfirmCode()!="" && queryUser.getState() == false){
                String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();
                LocalDateTime ldt = LocalDateTime.now().plusDays(1);
                user.setCreateTime(ldt);
                user.setConfirmCode(confirmCode);
                String activationUrl = emailIp + "/activation?confirmCode=" + confirmCode;
                mailService.sendMailForActivationAccount(activationUrl,user.getEmail(),"register");
                userMapper.registerUpdate(user);
                return null;
            }else {
                throw new ServiceException(Constants.CODE_601,"该用户名已被占用");
            }
        }
    }


    /**
     * 激活码激活
     * @param confirmCode
     * @return
     */
    public String activationAccount(String confirmCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("confirm_code",confirmCode);
        User user = getOne(queryWrapper);
        if(user==null){
            return Constants.CODE_410;
        }
        boolean after = LocalDateTime.now().isAfter(user.getCreateTime());
        if(after){
            return Constants.CODE_409;
        }else {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("username",user.getUsername());
            updateWrapper.set("confirm_code","");
            updateWrapper.set("state",true);
            this.update(null,updateWrapper);
            return Constants.CODE_200;
        }
    }

    public void deleteByconfirmCode(String confirmCode) {
        userMapper.deleteByconfirmCode(confirmCode);
    }
}
