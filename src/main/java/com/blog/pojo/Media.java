package com.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    private int id;
    private String url;
    private String name;
    private String type;
    private String realname;
    private String description;
}
