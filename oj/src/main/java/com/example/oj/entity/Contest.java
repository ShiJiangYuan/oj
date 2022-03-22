package com.example.oj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sjy
 * @date 2022/3/9
 **/
@Data
@TableName(value = "sys_user_exam")
@Getter
@Setter
@ToString
@ApiModel(value = "Contest对象",description = "")
public class Contest {
    @ApiModelProperty("用户")
    private String username;
    @ApiModelProperty("考试编号")
    @TableField(value = "exam_id")
    private String examId;
    @ApiModelProperty("选择题")
    private String radio;
    @ApiModelProperty("判断题")
    private String judge;
    @ApiModelProperty("填空题")
    private String fill;
    @ApiModelProperty("简答题")
    private String programme;
    @ApiModelProperty("分数")
    private Integer score;
    @ApiModelProperty("备注")
    private String note;
    public Contest() {
    }

    public Contest(String username, String examId, Integer score,String note) {
        this.username = username;
        this.examId = examId;
        this.score = score;
        this.note = note;
    }

    public Contest(String username, String examId, String radio, String judge, String fill, String programme,
                   Integer score) {
        this.username = username;
        this.examId = examId;
        this.radio = radio;
        this.judge = judge;
        this.fill = fill;
        this.programme = programme;
        this.score = score;
    }
}
