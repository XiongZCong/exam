package com.xzc.controller;

import com.xzc.result.Result;
import com.xzc.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于学习
 *
 * @author 熊智聪
 * @create 2019-03-28 16:21
 **/
@RestController
public class StudyController extends BaseController {

    @Autowired
    StudyService studyService;

    @RequestMapping("/study/insertFollow")
    public Result insertFollow(Long teacherId) {
        System.out.println(getUserId());
        return Result.success(studyService.insertFollow(getUserId(), teacherId));
    }

    @RequestMapping("/study/deleteFollow")
    public Result deleteFollow(Long teacherId) {
        return Result.success(studyService.deleteFollow(getUserId(), teacherId));
    }

    @RequestMapping("/study/selectFollow")
    public Result selectFollow() {
        return Result.success(studyService.selectFollow(getUserId()));
    }

    @RequestMapping("/study/selectPaperByUserId")
    public Result selectPaperByUserId(Long userId) {
        return Result.success(studyService.selectPaperByUserId(userId));
    }

    @RequestMapping("/study/selectTeacherById")
    public Result selectTeacherById(Long userId) {
        return Result.success(studyService.selectTeacherById(userId));
    }

    @RequestMapping("/study/selectTeacherByName")
    public Result selectTeacherByName(String username, int pageNum, int pageSize) {
        return Result.success(studyService.selectTeacherByName(username, pageNum, pageSize));
    }

    @RequestMapping("/study/registerTeacher")
    public Result registerTeacher() {
        return Result.success(studyService.registerTeacher(getUserId()));
    }
}
