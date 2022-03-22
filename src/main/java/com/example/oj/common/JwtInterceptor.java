package com.example.oj.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.oj.entity.Right;
import com.example.oj.entity.Role;
import com.example.oj.entity.User;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.RightService;
import com.example.oj.service.RoleService;
import com.example.oj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sjy
 * @date 2022/2/16
 **/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "登录失效，请重新登录");
        }
        // 获取 token 中的 username
        String username;
        try {
            username = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "身份验证失败，请重新登录");
        }
        // 根据token中的userid查询数据库
        User user = userService.getById(username);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            // 验证token
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "身份验证失败，请重新登录");
        }

        // 验证权限

        // 获取请求的地址
        String[] requestURI = request.getRequestURI().split("/");
        String url = "/" + requestURI[1];
        // 截取两个url
        if(requestURI.length > 2) {
            url = url +  "/" +  requestURI[2];
        }
        // 通过username获取角色的id
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("name",user.getRole());
        Integer roleId = roleService.getOne(roleQueryWrapper).getId();
        // 获取是否有权限（有权限的话，返回权限的id）
        Right right= rightService.getIdByUrlAndMethod(url,request.getMethod());
        // 判断路径是否是共享的（比如首页一些路径不需要权限也可以访问），判断请求路径是否在right中
        if (right == null){
            return true;
        }
        Integer rightId = right.getId();
        Boolean hasRight = rightService.judgeByRoleIdAndRightId(roleId,rightId);
        if(hasRight == false) {
            throw new ServiceException(Constants.CODE_401,"无权限执行此操作");
        }
        return true;
    }
}
