package com.example.spingboot.POJO;

import lombok.Data;

@Data
public class Q {
    private Integer currentPage;
    private Integer pageSize;
    private String username;
    private String phone;
    private String email;
    private Integer pageNum;
}
