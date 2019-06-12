package com.xzc.mapper;

import com.xzc.model.Paper;
import com.xzc.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudyMapper {

    @Insert("insert into t_follow (userId,teacherId) values (#{userId},#{teacherId})")
    Integer insertFollow(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    @Delete("delete from t_follow where userId=#{userId} and teacherId=#{teacherId}")
    Integer deleteFollow(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    @Select("select * from t_user inner join t_follow on t_user.userId=t_follow.teacherId where t_follow.userId=#{userId}")
    List<User> selectFollow(@Param("userId") Long userId);

    @Select("select count(*) from t_follow where userId=#{userId} and teacherId=#{teacherId}")
    Integer isFollow(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    @Select("select * from t_paper where userId=#{userId}")
    List<Paper> selectPaperByUserId(@Param("userId") Long userId);

    @Select("select * from t_user inner join user_role on t_user.userId = user_role.userId where t_user.userId=#{userId} and roleId=10")
    List<User> selectTeacherById(@Param("userId") Long userId);

    @Select("select * from t_user inner join user_role on t_user.userId = user_role.userId where t_user.username like '%${username}%' and roleId=10")
    List<User> selectTeacherByName(@Param("username") String username);

    @Insert("insert into user_role (userId,roleId) values (#{userId},#{roleId})")
    Integer registerTeacher(@Param("userId") Long userId, @Param("roleId") int roleId);
}
