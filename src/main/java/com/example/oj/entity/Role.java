package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sjy
 * @date 2022/2/17
 **/
@Data
@TableName(value = "sys_role")
@Getter
@Setter
@ToString
@ApiModel(value = "Role对象",description = "")
public class Role {
    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty("角色ID")
    private Integer id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @TableField(exist = false)
    private List<Right> children;
}
