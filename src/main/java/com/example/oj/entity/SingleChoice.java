package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.oj.dto.RadioDTO;
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
@TableName(value = "sys_radio")
@Getter
@Setter
@ToString
@ApiModel(value = "SingleChoice对象",description = "")
public class SingleChoice {
    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("题目")
    private String title;
    @ApiModelProperty("选项A")
    @TableField(value = "plan_a")
    private String planA;
    @ApiModelProperty("选项B")
    @TableField(value = "plan_b")
    private String planB;
    @ApiModelProperty("选项C")
    @TableField(value = "plan_c")
    private String planC;
    @ApiModelProperty("选项D")
    @TableField(value = "plan_d")
    private String planD;
    @ApiModelProperty("正确答案")
    private String sure;
    @ApiModelProperty("来源")
    private String source;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;

    public SingleChoice() {
    }

    public SingleChoice(String title, String planA, String planB, String planC, String planD, String sure,
                        String source) {
        this.title = title;
        this.planA = planA;
        this.planB = planB;
        this.planC = planC;
        this.planD = planD;
        this.sure = sure;
        this.source = source;
    }
}
