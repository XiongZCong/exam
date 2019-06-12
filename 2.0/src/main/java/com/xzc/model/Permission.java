package com.xzc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Permission {

    private Long permissionId;
    private String permissionName;
    private String resource;
    private String access;
    private boolean operation;
    private Date createTime;
    private String note;
}
