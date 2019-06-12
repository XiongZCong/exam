package com.xzc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzc.mapper.StudyMapper;
import com.xzc.model.Paper;
import com.xzc.model.User;
import com.xzc.result.CodeMsg;
import com.xzc.result.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关于学习Service
 *
 * @author 熊智聪
 * @create 2019-03-28 16:21
 **/
@Service
@Transactional
public class StudyService {

    @Autowired
    private StudyMapper studyMapper;

    /**
     * 关注一位教师
     *
     * @param userId    我的用户Id
     * @param teacherId 教师角色用户Id
     * @return 关注
     */
    public Integer insertFollow(Long userId, Long teacherId) {
        if (studyMapper.isFollow(userId, teacherId) > 0) {
            throw new GlobalException(CodeMsg.IS_EXIST);
        }
        return studyMapper.insertFollow(userId, teacherId);
    }

    /**
     * 取消关注
     *
     * @param userId    我的用户Id
     * @param teacherId 教师角色用户Id
     * @return 删除
     */
    public Integer deleteFollow(Long userId, Long teacherId) {
        return studyMapper.deleteFollow(userId, teacherId);
    }

    /**
     * 我关注的教师
     *
     * @param userId 我的用户Id
     * @return 教师信息列表
     */
    public List<User> selectFollow(Long userId) {
        return studyMapper.selectFollow(userId);
    }

    /**
     * 查找某位教师角色的试卷
     *
     * @param userId 教师角色用户Id
     * @return 试卷列表
     */
    public List<Paper> selectPaperByUserId(Long userId) {
        return studyMapper.selectPaperByUserId(userId);
    }

    /**
     * 根据用户Id查找教师
     *
     * @param userId 教师角色用户Id
     * @return 查找的信息
     */
    public PageInfo<User> selectTeacherById(Long userId) {
        PageHelper.startPage(1, 10);
        List<User> list = studyMapper.selectTeacherById(userId);
        return new PageInfo<>(list);
    }

    /**
     * 根据用户名称查找教师
     *
     * @param username 用户名称
     * @param pageNum  第几页
     * @param pageSize 页面记录条数
     * @return 教师角色用户信息（分页封装）
     */
    public PageInfo<User> selectTeacherByName(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = studyMapper.selectTeacherByName(username);
        return new PageInfo<>(list);
    }

    /**
     * 注册成为教师
     *
     * @param userId 用户Id
     * @return 是否注册
     */
    public Integer registerTeacher(Long userId) {
        return studyMapper.registerTeacher(userId, 10);
    }
}
