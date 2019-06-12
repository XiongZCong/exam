package com.xzc.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.xzc.model.Paper;
import com.xzc.model.Person;
import com.xzc.model.Test;
import com.xzc.result.Result;
import com.xzc.service.PaperService;
import com.xzc.util.FileUtil;
import com.xzc.vo.QuestionVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {

    @Autowired
    PaperService paperService;

    @RequestMapping("/paper/selectPaperById")
    public Result selectPaperById(Long paperId) {
        return Result.success(paperService.selectPaperById(paperId));
    }

    @RequestMapping("/paper/selectPapersByName")
    public Result selectPapersByName(String paperName, int pageNum, int pageSize) {
        return Result.success(paperService.selectPapersByName(paperName, pageNum, pageSize));
    }

    @RequestMapping("/paper/selectPapersByNameUserId")
    public Result selectPapersByNameUserId(Long userId, String paperName, int pageNum, int pageSize) {
        return Result.success(paperService.selectPapersByNameUserId(userId, paperName, pageNum, pageSize));
    }

    @RequestMapping("/paper/selectPaperBeforeById")
    public Result selectPaperBeforeById(Long paperId) {
        return Result.success(paperService.selectPaperBeforeById(paperId));
    }

    @RequestMapping("/paper/selectPaperAfterById")
    public Result selectPaperAfterById(Long paperId) {
        return Result.success(paperService.selectPaperAfterById(paperId));
    }

    @RequestMapping("/paper/previewPaper")
    public Result previewPaper(Long paperId) {
        return Result.success(paperService.previewPaper(paperId));
    }

    @RequestMapping("/paper/insertPaper")
    public Result insertPaper(@RequestBody @Valid Paper paper) {
        return Result.success(paperService.insertPaper(paper));
    }

    @RequestMapping("/paper/updatePaperById")
    public Result updatePaperById(@RequestBody Paper paper) {
        return Result.success(paperService.updatePaperById(paper));
    }

    @RequestMapping("/paper/insertTest")
    public Result insertTest(@RequestBody Paper paper) {
        return Result.success(paperService.insertTest(paper));
    }

    @RequestMapping("/paper/updateTestNote")
    public Result updateTestNote(@RequestBody Test test) {
        return Result.success(paperService.updateTestNote(test));
    }

    @RequestMapping("/paper/selectTestsByUserId")
    public Result selectTestByUserId(Long userId, int pageNum, int pageSize) {
        return Result.success(paperService.selectTestsByUserId(userId, pageNum, pageSize));
    }

    @RequestMapping("/paper/selectTestsByPaperId")
    public Result selectTestByPaperId(Long paperId, int pageNum, int pageSize) {
        return Result.success(paperService.selectTestsByPaperId(paperId, pageNum, pageSize));
    }

    @RequestMapping("/paper/selectTestById")
    public Result selectTestById(Long testId) {
        return Result.success(paperService.selectTestById(testId));
    }

    @RequestMapping("/paper/deletePaperById")
    public Result deletePaperById(Long paperId) {
        return Result.success(paperService.deletePaperById(paperId));
    }

    @RequestMapping("/paper/deleteTestById")
    public Result deleteTestById(Long testId) {
        return Result.success(paperService.deleteTestById(testId));
    }

    @RequestMapping("/paper/echartStudent")
    public Result echartStudent(Long userId) {
        return Result.success(paperService.echartStudent(userId));
    }

    @RequestMapping("/paper/echartTeacher")
    public Result echartTeacher(Long paperId) {
        return Result.success(paperService.echartTeacher(paperId));
    }

    /**
     * 注解导出
     *
     * @param response 响应
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        //模拟从数据库获取需要导出的数据
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person("路飞", "1");
        Person person2 = new Person("娜美", "2");
        Person person3 = new Person("索隆", "1");
        Person person4 = new Person("小狸猫", "1");
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        //导出操作
        FileUtil.exportExcel(personList, "花名册", "草帽一伙", Person.class, "海贼王.xls", response);
    }

    /**
     * map导出
     *
     * @param response 影响
     */
    @RequestMapping("/exportm")
    public void exportm(HttpServletResponse response) {
        //标题
        List<ExcelExportEntity> entityList = new ArrayList<>();
        //内容
        List<Map<String, Object>> dataResult = new ArrayList<>();
        entityList.add(new ExcelExportEntity("表头1", "table1", 15));
        entityList.add(new ExcelExportEntity("表头2", "table2", 25));
        entityList.add(new ExcelExportEntity("表头3", "table3", 35));
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("table1", "苹果" + i);
            map.put("table2", "香蕉" + i);
            map.put("table3", "鸭梨" + i);
            dataResult.add(map);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试", "测试"), entityList,
                dataResult);
        FileUtil.downLoadExcel("shuiguo.xls", response, workbook);

    }

    /**
     * 模板导出
     *
     * @param response 响应
     */
    @RequestMapping("/exportTemp")
    public void exportTemp(HttpServletResponse response) {
        TemplateExportParams params = new TemplateExportParams("excelTemp/成绩单模板.xls");
        Map<String, Object> map = new HashMap<>();
        map.put("putinTime", "2014-12-25 14:30:00");
        map.put("paperName", "java");
        map.put("userId", "java");
        map.put("score", 55);
        map.put("note", "xioaxing");
        List<Map<String, String>> listMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<>();
            lm.put("index", i + 1 + "");
            lm.put("questionName", "巴金几岁写成长篇小说《家》");
            lm.put("answerA", "嗅觉");
            lm.put("answerB", "视觉");
            lm.put("answerC", "听觉");
            lm.put("answerD", "味觉");
            lm.put("myAnswer", "A");
            lm.put("sign", "✔");
            listMap.add(lm);
        }
        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        FileUtil.downLoadExcel("exportTemp.xls", response, workbook);
    }

    /**
     * 模板导出
     *
     * @param response 响应
     */
    @RequestMapping("/printTest")
    public void printTest(Long testId, HttpServletResponse response) {
        TemplateExportParams params = new TemplateExportParams("excelTemp/成绩单模板.xls");
        Map<String, Object> map = new HashMap<>();
        Test test = paperService.selectTestById(testId);
        String paperName = test.getPaper().getPaperName();
        map.put("paperName", paperName);
        map.put("userId", test.getUserId());
        map.put("putinTime", test.getPutinTime());
        map.put("score", (int) test.getScore());
        map.put("note", test.getNote() == null ? "" : test.getNote());
        List<Map<String, String>> listMap = new ArrayList<>();
        List<QuestionVo> questionVos = test.getPaper().getQuestionVos();
        for (int i = 0; i < questionVos.size(); i++) {
            Map<String, String> lm = new HashMap<>();
            lm.put("index", i + 1 + "");
            lm.put("questionName", questionVos.get(i).getQuestionName());
            lm.put("answerA", questionVos.get(i).getAnswerA());
            lm.put("answerB", questionVos.get(i).getAnswerB());
            lm.put("answerC", questionVos.get(i).getAnswerC());
            lm.put("answerD", questionVos.get(i).getAnswerD());
            lm.put("myAnswer", questionVos.get(i).getMyAnswer());
            lm.put("sign", questionVos.get(i).getSign());
            System.out.println(questionVos.get(i).getSign());
            listMap.add(lm);
        }
        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        FileUtil.downLoadExcel(paperName + "成绩单.xls", response, workbook);
    }

}

