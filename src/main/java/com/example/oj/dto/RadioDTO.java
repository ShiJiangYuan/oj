package com.example.oj.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sjy
 * @date 2022/3/13
 **/
@Data
public class RadioDTO {
    private String title;
    private String planA;
    private String planB;
    private String planC;
    private String planD;
    private String sure;
    private Integer numberA;
    private Integer numberB;
    private Integer numberC;
    private Integer numberD;
}
