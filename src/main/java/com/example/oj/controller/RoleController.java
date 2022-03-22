package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.oj.common.Constants;
import com.example.oj.common.Result;
import com.example.oj.entity.Role;
import com.example.oj.exception.ServiceException;
import com.example.oj.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;




    /**
     * 获取角色
     * @return
     */
    @PostMapping("/roles")
    public Result roles(){
        try{
            List<Role> roles = roleService.list();
            return Result.success(roles);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    /**
     * 修改角色信息
     * @param data
     * @return
     */
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody Map<String,Map<String,String>> data){
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",data.get("data").get("roleId"));
        updateWrapper.set("name",data.get("data").get("roleName"));
        updateWrapper.set("description",data.get("data").get("roleDesc"));
        try{
            roleService.update(null,updateWrapper);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
    }

    /**
     * 添加角色
     * @param data
     * @return
     */
    @PostMapping("/updateRole")
    public Result insertRole(@RequestBody Map<String,Map<String,String>> data){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("name",data.get("data").get("roleName"));
        if(roleService.getOne(roleQueryWrapper)!=null) {
            throw new ServiceException(Constants.CODE_601,"角色名称已存在");
        }
        Role role = new Role();
        role.setDescription(data.get("data").get("roleDesc"));
        role.setName(data.get("data").get("roleName"));
        try{
            roleService.save(role);
            return Result.success();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"服务器错误");
        }
    }


    /**
     * 返回当前登录角色的权限
     * @param username
     * @return  权限信息
     */
    @GetMapping("/getMenu/{username}")
    public Result getMenu(@PathVariable String username){
        Role role = roleService.getMenu(username);
        return Result.success(role.getChildren());
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/deleteRole/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId){
        roleService.deleteRoleById(roleId);
        return Result.success();
    }


    /**
     * 删除单个权限
     * @param roleId
     * @param rightId
     * @return 权限
     */
    @DeleteMapping("/role/{roleId}/right/{rightId}")
    public Result deleteRight(@PathVariable Integer roleId,@PathVariable Integer rightId){
        //防止删除一级或者二级目录，数据库会留下脏数据，采用sql递归查找所有子权限
        Boolean delete = roleService.deleteByRightAndRole(roleId, rightId);
        if(delete) {
            Role role = roleService.getRightsByRoleId(roleId);
            return Result.success(role.getChildren());
        }else {
            return Result.error(Constants.CODE_500,"服务器错误");
        }
    }

    /**
     * 获取角色信息，包括权限信息
     * @return
     */
    @GetMapping("/roleRights")
    public Result getRoleRights() {
        //获取角色信息
        List<Role> roles = roleService.list();
        //获取角色信息的权限
        List<Role> roleRights = roleService.getRights(roles);
        return Result.success(roleRights);
    }
    /**
     * 绑定角色和权限的关系
     * @param roleId    角色id
     * @param rightIds  权限id数组
     * @return
     */
    @PostMapping("/setRoleRight/{roleId}")
    public Result setRoleRight(@PathVariable Integer roleId,@RequestBody List<Integer> rightIds){
        Role role = roleService.setRoleRight(roleId, rightIds);
        return Result.success(role);
    }
}
