package com.xzc.model;

import com.xzc.vo.QuestionVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Paper {
    private Long paperId;
    @NotBlank(message = "试卷名不能为空")
    private String paperName;
    private Long userId;
    private Date beginTime;
    @Future
    private Date endTime;
    private String note;
    @Valid // 嵌套验证必须用@Valid
    @NotNull(message = "试卷要有题目")
    @Size(min = 1, message = "至少要有一个题目")
    private List<Question> questions;
    private List<QuestionVo> questionVos;
}
