package com.example.oj.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.common.Constants;
import com.example.oj.entity.Right;
import com.example.oj.entity.Role;
import com.example.oj.entity.RoleRight;
import com.example.oj.exception.ServiceException;
import com.example.oj.mapper.RoleMapper;
import com.example.oj.mapper.RoleRightMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sjy
 * @date 2022/2/17
 **/
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleRightMapper roleRightMapper;

    /**
     *  设置角色的权限
     * @param roleId
     * @param rightIds
     */
    @Transactional(rollbackFor = Exception.class)
    public Role setRoleRight(Integer roleId, List<Integer> rightIds) {
        Role rightByRoleId = null;
        try{
            //先删除当前角色id所绑定的关系
            roleRightMapper.deleteByRoleId(roleId);
            //再把前端传过来的菜单id数组绑定到角色id上
            for (Integer rightId : rightIds) {
                RoleRight roleRight = new RoleRight();
                roleRight.setRoleId(roleId);
                roleRight.setRightId(rightId);
                roleRightMapper.insert(roleRight);
            }
            rightByRoleId = getRightByRoleId(roleMapper.getRoleById(roleId));
        } catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
        return rightByRoleId;
    }


    /**
     * 获取每个角色的权限
     * @param roles
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Role> getRights(List<Role> roles) {
        List<Role> resultRole = new ArrayList<>();
        try {
            //遍历每个角色
            for (Role role : roles) {
                resultRole.add(getRightByRoleId(role));
            }
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return resultRole;
    }

    /**
     * 获取每个角色的权限
     * @param role 角色
     * @return
     */
    private Role getRightByRoleId(Role role){
        //通过角色的ID获取每个角色对应的权限的ID
        List<Integer> rights = roleRightMapper.getRights(role.getId());
        //有管理权限
        if(!rights.isEmpty()){
            //获取一级菜单
            List<Right> firstMenu = roleRightMapper.getFirstMenu(rights);
            //遍历一级菜单
            for (Right menu : firstMenu) {
                // 获取二级菜单
                List<Right> secondMenu = roleRightMapper.getSonMenu(menu.getId(),rights);
                //遍历二级菜单
                for (Right right : secondMenu) {
                    //得到三级菜单
                    List<Right> thirdMenu = roleRightMapper.getSonMenu(right.getId(),rights);
                    //将三级菜单绑定到二级菜单
                    right.setChildren(thirdMenu);
                }
                //将二级菜单绑定到一级菜单
                menu.setChildren(secondMenu);
            }
            //将一级菜单绑定到role中
            role.setChildren(firstMenu);
        }else {
            //无管理权限
            //设置空的一级菜单
            List<Right> emptyRight = new ArrayList<>();
            role.setChildren(emptyRight);
        }
        return role;
    }

    /**
     * 删除角色的权限
     * @param roleId
     * @param rightId
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByRightAndRole(Integer roleId, Integer rightId) {
        //获取所有权限(利用sql递归)
        List<Integer> rightIds = roleRightMapper.getAllRightChildren(rightId);
        rightIds.add(rightId);
        System.out.println(rightIds);
        //删除所有权限
        Boolean delete = roleRightMapper.deleteByRightAndRole(roleId, rightIds);
        return delete;
    }

    /**
     * 通过roleId获取对应角色的id
     * @param roleId
     */
    public Role getRightsByRoleId(Integer roleId) {
        Role role = new Role();
        role.setId(roleId);
        return getRightByRoleId(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(Integer roleId) {
        try{
            roleRightMapper.deleteByRoleId(roleId);
            roleMapper.deleteByRoleId(roleId);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    public Role getMenu(String username) {
        Integer roleId = roleMapper.getRoleId(username);
        Role role = new Role();
        role.setId(roleId);
        return getRightByRoleId(role);
    }
}
