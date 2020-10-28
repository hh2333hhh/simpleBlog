package com.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Date retime;
    private String role;
    private Date buytime;
}
