package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName(value = "sys_right")
@Getter
@Setter
@ToString
@ApiModel(value = "Right对象",description = "")
public class Right {
    @TableId
    @ApiModelProperty("菜单对应的ID")
    private Integer id;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("上级菜单ID")
    private String parent;
    @ApiModelProperty("icon图标")
    private String icon;
    @ApiModelProperty("web中的url路径")
    private String path;
    @ApiModelProperty("请求的url路径")
    private String url;
    @ApiModelProperty("请求的方法")
    private String method;
    @TableField(exist = false)
    private List<Right> children;
}
