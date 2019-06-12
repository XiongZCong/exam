package com.xzc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzc.config.redis.RedisUtil;
import com.xzc.mapper.UserMapper;
import com.xzc.model.User;
import com.xzc.result.CodeMsg;
import com.xzc.result.exception.GlobalException;
import com.xzc.util.EmailHtmlTemplate;
import com.xzc.util.EmailUtil;
import com.xzc.util.UUIDUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    EmailUtil emailUtil;

    public boolean login(User user) {
        System.out.println("UserService.login");
        System.out.println(user.toString());
        User loginUser = userMapper.login(user);
        return loginUser != null;
    }

    /**
     * 根据用户Id查询用户信息（准确查询）
     *
     * @param userId 用户Id
     * @return 查询出一条或没有记录，封装成一个列表
     */
    public List<User> selectUserById(Long userId) {
        System.out.println("UserService.selectUserById");
        System.out.println(userId);
        return userMapper.selectUserById(userId);
    }

    /**
     * 根据用户名称查询该用户的所有信息，包括角色和权限（准确查询）
     *
     * @param username 用户名称
     * @return 该用户所有信息
     */
    public User selectUserByName(String username) {
        System.out.println("UserService.selectUserByName");
        System.out.println(username);
        return userMapper.selectUserByName(username);
    }

    /**
     * 根据用户名称查询用户列表
     *
     * @param username 用户名称
     * @param pageNum  第xx页数
     * @param pageSize 一页的记录条数
     * @return 返回用户信息列表
     */
    public PageInfo<User> selectUsersByName(String username, int pageNum, int pageSize) {
        System.out.println("UserService.selectUsersByName");
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectUsersByName(username);
        return new PageInfo<>(userList);
    }

    /**
     * 注册用户账号第一步，将注册信息发送到注册的邮箱中
     *
     * @param user 用户对象
     */
    public void register(User user) {
        System.out.println("UserService.register");
        if (userMapper.checkUser(user.getUsername()) != null) {
            throw new GlobalException(CodeMsg.EMAIL_EXIST);
        }
        String uuid = UUIDUtil.uuid();
        RedisUtil.set(user, RedisUtil.Field_NAME, uuid, 60 * 60 * 24);
       // String text = "http://localhost:9001/user/registerCheck/" + uuid;
        String text = "http://47.98.150.142:9001/user/registerCheck/" + uuid;

        emailUtil.sendHtmlMail(EmailHtmlTemplate.registerTemplate(user.getUsername(), text));
    }

    /**
     * 注册用户账号第二步，核对注册信息并保存到数据库中
     *
     * @param user 用户对象
     * @return 返回用户注册信息
     */
    public User registerCheck(User user) {
        System.out.println("UserService.registerCheck");
        if (userMapper.checkUser(user.getUsername()) != null) {
            throw new GlobalException(CodeMsg.EMAIL_EXIST);
        }
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), "salt", 3);
        user.setPassword(md5Hash.toString());
        user.setState(true);
        user.setCreateTime(new Date());
        user.setHeadPic("");
        userMapper.insertUser(user);
        userMapper.insertUserRole(user.getUserId(), 1L);
        return userMapper.selectUserByName(user.getUsername());
    }

    /**
     * 验证码发送至邮箱
     *
     * @param username 用户名称
     */
    public void getVerification(String username) {
        System.out.println("UserService.getVerification");
        User user = userMapper.checkUser(username);
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String uuid = UUIDUtil.uuid();
        System.out.println(uuid);
        RedisUtil.set(user, RedisUtil.VerificationCode, uuid, 60 * 60);
        emailUtil.sendHtmlMail(EmailHtmlTemplate.getVerificationTemplate(username, uuid));
    }

    /**
     * 用户忘记密码时，修改密码
     *
     * @param username 用户名称
     * @param password 新密码
     * @param uuid     UUID
     * @return 是否修改成功
     */
    public Long changePassword(String username, String password, String uuid) {
        System.out.println("UserService.changePassword");
        if (!RedisUtil.exists(User.class, RedisUtil.VerificationCode, uuid)) {
            throw new GlobalException(CodeMsg.EMAIL_CODE_ERROR);
        }
        User user = RedisUtil.get(User.class, RedisUtil.VerificationCode, uuid);
        if (!username.equals(user.getUsername())) {
            throw new GlobalException(CodeMsg.USERNAME_ERROR);
        }
        Md5Hash md5Hash = new Md5Hash(password, "salt", 3);
        user.setPassword(md5Hash.toString());
        return userMapper.updateUserByName(user);
    }

    /**
     * 根据userId删除用户账号
     *
     * @param userId 用户Id
     * @return 返回是否删除成功
     */
    public Long deleteUserById(Long userId) {
        System.out.println("UserService.deleteUserById");
        return userMapper.deleteUserById(userId);
    }

    /**
     * 修改用户资料
     *
     * @param user 用户对象
     * @return 返回是否修改成功
     */
    public Long updateUserById(User user) {
        System.out.println("UserService.updateUserById");
        return userMapper.updateUserById(user);
    }
}
