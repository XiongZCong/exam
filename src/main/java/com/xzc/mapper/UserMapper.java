package com.xzc.mapper;

import com.xzc.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from T_User WHERE username=#{username} and password=#{password}")
    public User login(@Param("username") String username,
                      @Param("password") String password);

    @Select("select count(*) from T_User WHERE username=#{username} and password=#{password}")
    public Integer login2(User user);

    @Select("select * from T_User where userId =#{userId}")
    public List<User> selectUserById(@Param("userId") Long userId);

    @Select("select * from T_User where userName like '%${userName}%'")
    public List<User> selectUserByName(@Param("userName") String userName);

    @Insert("insert into T_User	(username,password)	values(#{username},#{password})")
    public Integer register(User user);

    //分布式session
}
