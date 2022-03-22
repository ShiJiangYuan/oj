package com.example.oj.dto;

import lombok.Data;

/**
 * @author sjy
 * @date 2022/3/13
 **/
@Data
public class FillDTO {
    private String title;
    private String sure;
    private Integer correctNumber;
    private Integer errorNumber;
}
