package com.xzc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionVo {
    private Long questionId;
    private Long paperId;
    private String questionName;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String questionAnswer;
    private float score;
    private Integer type;
    private String note;
    private String myAnswer;
    private String sign;
}
