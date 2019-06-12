package com.xzc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Test {
    private Long testId;
    private Long userId;
    private Long paperId;
    private User user;
    private Paper paper;
    private float score;
    private Date putinTime;
    private String note;
    private List<TestItem> testItems;
}
