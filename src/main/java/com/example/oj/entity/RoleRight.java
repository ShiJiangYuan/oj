package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sjy
 * @date 2022/2/17
 **/
@Data
@TableName(value = "sys_role_right")
@Getter
@Setter
@ToString
public class RoleRight {
    @ApiModelProperty("角色ID")
    @TableField(value = "role_id")
    private Integer roleId;
    @ApiModelProperty("权限ID")
    @TableField(value = "right_id")
    private Integer rightId;
}
