package com.example.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.common.Constants;
import com.example.oj.entity.Right;
import com.example.oj.entity.RoleRight;
import com.example.oj.exception.ServiceException;
import com.example.oj.mapper.RightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/17
 **/
@Service
public class RightService extends ServiceImpl<RightMapper, Right> {

    @Autowired(required = false)
    private RightMapper rightMapper;

    @Transactional(rollbackFor = Exception.class)
    public List<Right> getGrade() {
        List<Right> firstMenu;
        try {
            //获取一级菜单
            firstMenu = rightMapper.getFirstMenu();
            //遍历一级菜单
            for (Right menu : firstMenu) {
                // 获取二级菜单
                List<Right> secondMenu = rightMapper.getSonMenu(menu.getId());
                //遍历二级菜单
                for (Right right : secondMenu) {
                    //得到三级菜单
                    List<Right> thirdMenu = rightMapper.getSonMenu(right.getId());
                    //将三级菜单绑定到二级菜单
                    right.setChildren(thirdMenu);
                }
                //将二级菜单绑定到一级菜单
                menu.setChildren(secondMenu);
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return firstMenu;
    }

    @Transactional
    public Right getIdByUrlAndMethod(String url, String method) {
        QueryWrapper<Right> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return this.getOne(queryWrapper);
    }

    public Boolean judgeByRoleIdAndRightId(Integer roleId, Integer rightId) {
        return rightMapper.judgeByRoleIdAndRightId(roleId,rightId) == null ? false : true;
    }
}
