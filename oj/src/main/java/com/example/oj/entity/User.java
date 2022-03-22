package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author sjy
 * @date 2022/2/10
 **/
@Data
@TableName(value = "sys_user")
@Getter
@Setter
@ToString
@ApiModel(value = "User对象",description = "")
public class User {
    @TableId
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("角色")
    private String role;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("登陆状态")
    private Boolean state;
    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("确认码")
    @TableField(value = "confirm_code")
    private String confirmCode;
}
