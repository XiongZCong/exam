package com.xzc.mapper;

import com.xzc.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from t_user where username=#{username} and password=#{password}")
    User login(User user);

    @Select("select * from t_user where username=#{username}")
    User checkUser(String username);

    @Select("select * from t_user where userId=#{userId}")
    List<User> selectUserById(Long userId);

    User selectUserByName(String username);

    @Select("select * from t_user where username like '%${username}%'")
    List<User> selectUsersByName(@Param("username") String username);

    Long insertUser(User user);

    @Insert("insert into user_role(userId,roleId) values(#{userId},#{roleId})")
    Long insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Update("UPDATE t_user SET headPic=#{headPic},note=#{note} WHERE userId=#{userId}")
    Long updateUserById(User user);

    @Update("UPDATE t_user SET password=#{password} WHERE username=#{username}")
    Long updateUserByName(User user);

    @Delete("DELETE FROM t_user WHERE userId=#{userId}")
    Long deleteUserById(@Param("userId") Long userId);
}
