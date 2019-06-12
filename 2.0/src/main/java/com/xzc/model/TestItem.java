package com.xzc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestItem {
    private Long testItemId;
    private Long testId;
    private Long questionId;
    private String myAnswer;
}
