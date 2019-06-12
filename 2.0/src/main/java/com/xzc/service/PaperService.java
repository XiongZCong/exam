package com.xzc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzc.mapper.PaperMapper;
import com.xzc.model.*;
import com.xzc.result.CodeMsg;
import com.xzc.result.exception.GlobalException;
import com.xzc.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaperService {

    @Autowired
    PaperMapper paperMapper;

    /**
     * 试卷列表编号精确查询
     *
     * @param paperId 试卷编号
     * @return PageInfo<Paper>
     */
    public PageInfo<Paper> selectPaperById(Long paperId) {
        PageHelper.startPage(1, 10);
        List<Paper> paperList = paperMapper.selectPaperById(paperId);
        return new PageInfo<>(paperList);
    }

    /**
     * 试卷列表名称模糊查询
     *
     * @param paperName 试卷名称
     * @param pageNum   第几页
     * @param pageSize  每页记录数
     * @return PageInfo<Paper>
     */
    public PageInfo<Paper> selectPapersByName(String paperName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Paper> paperList = paperMapper.selectPapersByName(paperName);
        return new PageInfo<>(paperList);
    }

    /**
     * 试卷列表名称模糊查询
     *
     * @param paperName 试卷名称
     * @param pageNum   第几页
     * @param pageSize  每页记录数
     * @return PageInfo<Paper>
     */
    public PageInfo<Paper> selectPapersByNameUserId(Long userId, String paperName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Paper> paperList = paperMapper.selectPapersByNameUserId(userId, paperName);
        return new PageInfo<>(paperList);
    }

    /**
     * 试卷信息查询
     *
     * @param paperId 试卷编号
     * @return Paper
     */
    private Paper selectPaper(Long paperId) {
        return paperMapper.selectPaper(paperId);
    }

    /**
     * 教师添加试卷
     *
     * @param paper 试卷
     * @return Long
     */
    public Long insertPaper(Paper paper) {
        Date beginTime = paper.getBeginTime();
        Date endTime = paper.getEndTime();
        if (beginTime != null && endTime != null && beginTime.after(endTime)) {
            throw new GlobalException(CodeMsg.BEBINTIMEAFTERENDTIME);
        }
        if (endTime != null && endTime.before(new Date())) {
            throw new GlobalException(CodeMsg.BEBINTIMEAFTERENDTIME);
        }
        List<Question> questions = paper.getQuestions();
        paperMapper.insertPaper(paper);
        Long paperId = paper.getPaperId();
        for (Question question : questions) {
            question.setPaperId(paperId);
            question.setQuestionAnswer(orderAnswer(question.getQuestionAnswer()));
        }
        return paperMapper.insertQuestion(questions);
    }

    /**
     * 管理员修改试卷
     *
     * @param paper 试卷
     * @return Integer
     */
    public Integer updatePaperById(Paper paper) {
        return paperMapper.updatePaperById(paper);
    }

    /**
     * 删除试卷
     *
     * @param paperId 试卷编号
     * @return Integer
     */
    public Integer deletePaperById(Long paperId) {
        return paperMapper.deletePaperById(paperId);
    }

    /**
     * 删除测验
     *
     * @param testId 测验编号
     * @return Integer
     */
    public Integer deleteTestById(Long testId) {
        return paperMapper.deleteTestById(testId);
    }

    /**
     * 开始考试时发试卷
     *
     * @param paperId 试卷编号
     * @return Paper
     */
    public Paper selectPaperBeforeById(Long paperId) {
        Paper paper = paperMapper.selectPaperBeforeById(paperId);
        if (paper.getBeginTime().before(new Date()) && paper.getEndTime().after(new Date())) {
            return paper;
        }
        throw new GlobalException(CodeMsg.BEBINTIMETOENDTIME);
    }

    /**
     * 考试时间结束后查看正确答案
     *
     * @param paperId 试卷编号
     * @return Paper
     */
    public Paper selectPaperAfterById(Long paperId) {
        Date endTime = selectPaper(paperId).getEndTime();
        if (endTime != null && endTime.after(new Date())) {
            throw new GlobalException(CodeMsg.ENDTIME);
        }
        return paperMapper.selectPaperAfterById(paperId);
    }

    /**
     * 教师预览试卷
     *
     * @param paperId 试卷编号
     * @return Paper
     */
    public Paper previewPaper(Long paperId) {
        System.out.println(paperMapper.selectPaperAfterById(paperId));
        return paperMapper.selectPaperAfterById(paperId);
    }


    /**
     * 学生参加提交试卷
     *
     * @param putinPaper 试卷
     * @return Long
     */
    public Long insertTest(Paper putinPaper) {
        Long paperId = putinPaper.getPaperId();
        Paper paper = paperMapper.selectPaperAfterById(paperId);
        if (paper.getEndTime().before(new Date())) {
            throw new GlobalException(CodeMsg.ENDTIME);
        }
        List<TestItem> testItems = new ArrayList<>();
        List<Question> questionList = putinPaper.getQuestions();
        for (Question question : questionList) {
            TestItem testItem = new TestItem();
            testItem.setQuestionId(question.getQuestionId());
            testItem.setMyAnswer(question.getQuestionAnswer());
            testItems.add(testItem);
        }
        Test test = new Test();
        test.setPaperId(paperId);
        test.setUserId(putinPaper.getUserId());
        test.setScore(0);
        test.setPutinTime(new Date());
        paperMapper.insertTest(test);
        Long testId = test.getTestId();
        for (TestItem testItem : testItems) {
            testItem.setTestId(testId);
        }
        paperMapper.insertTestItem(testItems);
        test.setScore(markPaper(testId));
        return paperMapper.updateTestScore(test);
    }

    /**
     * 试卷改分
     * @param testId 测试编号
     * @return 测试分数（采取百分制）
     */
    private Integer markPaper(Long testId) {
        Test test = paperMapper.selectTestById(testId);
        System.out.println(test.toString());
        Paper paper = paperMapper.selectPaperAfterById(test.getPaperId());
        float score = 0;
        float full = 0;
        for (Question question : paper.getQuestions()) {
            full += question.getScore();
        }
        for (TestItem testItem : test.getTestItems()) {
            for (Question question : paper.getQuestions()) {
                if (testItem.getQuestionId().equals(question.getQuestionId())) {
                    if (orderAnswer(testItem.getMyAnswer()).equals(orderAnswer(question.getQuestionAnswer()))) {
                        score += question.getScore();
                    }
                    break;
                }
            }
        }
        return (int) Math.ceil(score / full * 100);
    }

    /**
     * 多选答案排序
     *
     * @param string 乱序的答案
     * @return 排序后的答案
     */
    private String orderAnswer(String string) {
        char[] chars = string.toUpperCase().toCharArray();
        Arrays.sort(chars);
        string = new String(chars);
        System.out.println(string);
        return string;
    }

    /**
     * 教师添加测试评语
     *
     * @param test 测试
     * @return Test
     */
    public Long updateTestNote(Test test) {
        return paperMapper.updateTestNote(test);
    }

    /**
     * 教师根据试卷编号查询测验列表
     *
     * @param paperId  试卷编号
     * @param pageNum  第几页
     * @param pageSize 每页记录数
     * @return PageInfo<Test>
     */
    public PageInfo<Test> selectTestsByPaperId(Long paperId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Test> testList = paperMapper.selectTestsByPaperId(paperId);
        return new PageInfo<>(testList);
    }

    /**
     * 学生根据用户编号查询测验列表
     *
     * @param userId   用户编号
     * @param pageNum  第几页
     * @param pageSize 每页记录数
     * @return PageInfo<Test>
     */
    public PageInfo<Test> selectTestsByUserId(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Test> testList = paperMapper.selectTestsByUserId(userId);
        return new PageInfo<>(testList);
    }

    /**
     * 根据测验编号查询测验详细情况
     *
     * @param testId 测试编号
     * @return Test
     */
    public Test selectTestById(Long testId) {
        Test test = paperMapper.selectTestById(testId);
        System.out.println(test.toString());
        Paper paper = paperMapper.selectPaperAfterById(test.getPaperId());
        List<Question> questions = paper.getQuestions();
        List<QuestionVo> questionVos = new ArrayList<>();
        for (Question question : questions) {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question, questionVo);
            questionVos.add(questionVo);
        }
        List<TestItem> testItems = test.getTestItems();
        for (TestItem testItem : testItems) {
            for (QuestionVo questionVo : questionVos) {
                if (testItem.getQuestionId().equals(questionVo.getQuestionId())) {
                    questionVo.setMyAnswer(testItem.getMyAnswer());
                    System.out.println(questionVo.toString());
                    if (testItem.getMyAnswer().equals(questionVo.getQuestionAnswer())) {
                        questionVo.setSign("✔");
                    } else {
                        questionVo.setQuestionAnswer("");
                        questionVo.setSign("✘");
                        questionVo.setNote(null);
                    }
                    break;
                }
            }
        }
        paper.setQuestions(null);
        paper.setQuestionVos(questionVos);
        test.setPaper(paper);
        return test;
    }

    /**
     * 学生成绩直方图
     *
     * @param userId 用户编号
     * @return Echart
     */
    public Echart echartStudent(Long userId) {
        Echart echart = new Echart();
        List<String> nameList = new ArrayList<>();
        List<Long> dataList;
        Long[] dataArray = {0L, 0L, 0L, 0L, 0L};
        nameList.add("[0,0]");
        nameList.add("(0,60)");
        nameList.add("[60-80)");
        nameList.add("[80,99]");
        nameList.add("[100,100]");
        List<Test> tests = paperMapper.selectTestsByUserId(userId);
        for (Test test : tests) {
            int score = (int) test.getScore();
            if (score == 0) {
                dataArray[0]++;
            } else if (score > 0 && score < 60) {
                dataArray[1]++;
            } else if (score >= 60 && score < 80) {
                dataArray[2]++;
            } else if (score >= 80 && score < 100) {
                dataArray[3]++;
            } else if (score == 100) {
                dataArray[4]++;
            }
        }
        dataList = Arrays.asList(dataArray);
        echart.setNameList(nameList);
        echart.setDataList(dataList);
        return echart;
    }

    /**
     * 教师试卷饼图
     *
     * @param paperId 用户编号
     * @return Echart
     */
    public Echart echartTeacher(Long paperId) {
        Echart echart = new Echart();
        Long[] dataArray = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        List<Test> tests = paperMapper.selectTestsByPaperId(paperId);
        for (Test test : tests) {
            int score = (int) test.getScore();
            if (score == 0) {
                dataArray[0]++;
            } else if (score > 0 && score <= 10) {
                dataArray[1]++;
            } else if (score > 10 && score <= 20) {
                dataArray[2]++;
            } else if (score > 20 && score <= 30) {
                dataArray[3]++;
            } else if (score > 30 && score <= 40) {
                dataArray[4]++;
            } else if (score > 40 && score <= 50) {
                dataArray[5]++;
            } else if (score > 50 && score < 60) {
                dataArray[6]++;
            } else if (score >= 60 && score < 70) {
                dataArray[7]++;
            } else if (score >= 70 && score < 80) {
                dataArray[8]++;
            } else if (score >= 80 && score < 90) {
                dataArray[9]++;
            } else if (score >= 90 && score < 100) {
                dataArray[10]++;
            } else if (score == 100) {
                dataArray[11]++;
            }
        }
        List<Long> dataList = Arrays.asList(dataArray);
        echart.setDataList(dataList);
        return echart;
    }
}
