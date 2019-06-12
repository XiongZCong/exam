package com.xzc.mapper;

import com.xzc.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaperMapper {

    @Select("select * from t_paper where paperId=#{paperId}")
    List<Paper> selectPaperById(Long paperId);

    @Select("select * from t_paper where paperName like '%${paperName}%'")
    List<Paper> selectPapersByName(@Param("paperName") String paperName);

    @Select("select * from t_paper where userId=#{userId} and paperName like '%${paperName}%'")
    List<Paper> selectPapersByNameUserId(@Param("userId") Long userId, @Param("paperName") String paperName);

    @Select("select * from t_paper where paperId=#{paperId}")
    Paper selectPaper(Long paperId);

    Long insertPaper(Paper paper);

    Long insertQuestion(List<Question> questions);

    @Update("UPDATE t_paper SET paperName=#{paperName},beginTime=#{beginTime},endTime=#{endTime},note=#{note} WHERE paperId=#{paperId}")
    Integer updatePaperById(Paper paper);

    @Delete("DELETE t_paper,t_question,t_test,t_testitem FROM t_paper inner join t_question on t_paper.paperId = t_question.paperId inner join t_test on t_paper.paperId = t_test.paperId inner join t_testitem on t_test.testId = t_testitem.testId WHERE t_paper.paperId=#{paperId}")
    Integer deletePaperById(@Param("paperId") Long paperId);

    @Delete("DELETE t_test,t_testitem FROM t_test inner join t_testitem on t_test.testId = t_testitem.testId WHERE t_test.testId=#{testId}")
    Integer deleteTestById(@Param("testId") Long testId);

    Paper selectPaperBeforeById(Long paperId);

    Paper selectPaperAfterById(Long paperId);

    Long insertTest(Test test);

    Long insertTestItem(List<TestItem> testItems);

    @Update("UPDATE t_test SET note=#{note} WHERE testId=#{testId}")
    Long updateTestNote(Test test);

    @Select("select * from t_test where paperId=#{paperId}")
    List<Test> selectTestsByPaperId(Long paperId);

    @Select("select * from t_test where userId=#{userId}")
    List<Test> selectTestsByUserId(Long userId);

    Test selectTestById(Long testId);

    @Update("UPDATE t_test SET score=#{score} WHERE testId=#{testId}")
    Long updateTestScore(Test test);

}
