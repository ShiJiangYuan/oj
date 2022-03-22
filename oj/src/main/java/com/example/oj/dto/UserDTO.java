package com.example.oj.dto;

import lombok.Data;

/**
 * @author sjy
 * @date 2022/2/16
 **/
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String email;
    private String createTime;
    private String avatar;
    private String token;
}
