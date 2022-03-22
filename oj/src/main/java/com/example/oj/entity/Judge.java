package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sjy
 * @date 2022/2/24
 **/
@Data
@TableName(value = "sys_judge")
@Getter
@Setter
@ToString
@ApiModel(value = "Judge对象",description = "")
public class Judge {
    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("题目")
    private String title;
    @ApiModelProperty("正确答案")
    private String sure;
    @ApiModelProperty("来源")
    private String source;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;


    public Judge() {
    }

    public Judge(String title, String sure, String source) {
        this.title = title;
        this.sure = sure;
        this.source = source;
    }
}
