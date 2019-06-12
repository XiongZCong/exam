package com.xzc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class Question {
    private Long questionId;                 //问题Id
    private Long paperId;                    //试卷Id，属于的试卷
    @NotBlank(message = "题目不能为空")
    private String questionName;
    @NotBlank(message = "必须有选项A")
    private String answerA;
    @NotBlank(message = "必须有选项B")
    private String answerB;
    private String answerC;
    private String answerD;
    @Pattern(regexp = "[ABCD]{1,4}", message = "答案必须在ABCD中")
    private String questionAnswer;
    private float score;
    private Integer type;
    private String note;
}
