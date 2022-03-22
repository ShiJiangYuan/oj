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
@TableName(value = "sys_exam")
@Getter
@Setter
@ToString
@ApiModel(value = "Exam对象",description = "")
public class Exam {
    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("考试科目名称")
    private String name;
    @ApiModelProperty("选择题")
    private String radio;
    @ApiModelProperty("选择题分值")
    @TableField(value = "radio_value")
    private String radioValue;
    @ApiModelProperty("判断题")
    private String judge;
    @ApiModelProperty("判断题分值")
    @TableField(value = "judge_value")
    private String judgeValue;
    @ApiModelProperty("填空题")
    private String fill;
    @ApiModelProperty("填空题分值")
    @TableField(value = "fill_value")
    private String fillValue;
    @ApiModelProperty("简答题")
    private String programme;
    @ApiModelProperty("简答题分值")
    @TableField(value = "programme_value")
    private String programmeValue;
    @ApiModelProperty("考试开始时间")
    @TableField(value = "start_time")
    private String startTime;
    @ApiModelProperty("考试结束时间")
    @TableField(value = "end_time")
    private String endTime;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;
    @ApiModelProperty("创建考试的用户名")
    @TableField(value = "create_username")
    private String createUsername;
    @ApiModelProperty("参加考试的身份")
    private String participate;
    @ApiModelProperty("试卷是否公布")
    private Boolean state;

}
