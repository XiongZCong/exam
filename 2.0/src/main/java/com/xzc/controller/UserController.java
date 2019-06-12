package com.xzc.controller;

import com.xzc.config.redis.RedisUtil;
import com.xzc.model.User;
import com.xzc.result.CodeMsg;
import com.xzc.result.Result;
import com.xzc.result.exception.GlobalException;
import com.xzc.service.UserService;
import com.xzc.util.CodeImgUtil;
import com.xzc.util.NumberUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.IOException;

@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/loginOrg")
    public Result loginOrg(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println(user.toString());
        if (!userService.login(user)) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        return Result.success("/pages/home.html");
    }

    @RequestMapping("/user/login")
    public Result login() {
        getResponse().setContentType("text/html;charset=UTF-8");
        String code = getRequest().getParameter("code");
        if (!code.equalsIgnoreCase((String) getSession().getAttribute("codeImg"))) {
            return Result.error(CodeMsg.CODE_ERROR);
        }
        User user = new User();
        user.setUsername(getRequest().getParameter("username"));
        user.setPassword(getRequest().getParameter("password"));
        if (!userService.login(user)) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        if ("true".equals(getRequest().getParameter("rememberMe"))) {
            getSession().setAttribute("user", user);
            getResponse().addCookie(getCookie("username", user.getUsername()));
        }
        return Result.success("/pages/home.html");
    }

    @RequestMapping("/user/logout")
    public void logout() {
        getSession().invalidate();
    }

    @RequestMapping("/user/loginShiro")
    public Result loginShiro() {
        String code = getRequest().getParameter("code");
        printSession();
        if (code == null || !code.equalsIgnoreCase((String) getSession().getAttribute("codeImg"))) {
            return Result.error(CodeMsg.CODE_ERROR);
        }
        String rememberMe = getRequest().getParameter("rememberMe");
        UsernamePasswordToken token = new UsernamePasswordToken(getRequest().getParameter("username"), getRequest().getParameter("password"));
        if ("true".equals(rememberMe)) {
            getResponse().addCookie(getCookie("username", getRequest().getParameter("username")));
//            token.setRememberMe(true);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            getSession().setAttribute("user", user);
            return Result.success(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(CodeMsg.USER_NOT_EXIST);
    }

    @RequestMapping("/user/logoutShiro")
    public Result logoutShiro() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return Result.success("退出");
    }

    @RequestMapping("/user/register")
    public Result register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.register(user);
        return Result.success("邮件已发送，请注意查收！");
    }

    @RequestMapping("/user/registerCheck/{uuid}")
    public Result registerCheck(@PathVariable("uuid") String uuid) {
        User user = RedisUtil.get(User.class, RedisUtil.Field_NAME, uuid);
        if (user == null) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        return Result.success(userService.registerCheck(user));
    }

    @RequestMapping("/user/getVerification")
    public Result getVerification(String userEmail) {
        userService.getVerification(userEmail);
        return Result.success("邮件已发送，请注意查收！");
    }

    @RequestMapping("/user/changePassword")
    public Result changePassword(String userEmail, String newPassword, String vCode) {
        return Result.success(userService.changePassword(userEmail, newPassword, vCode));
    }

    @RequestMapping("/user/codeImg")
    public void codeImg() {
        String code = new CodeImgUtil().randomChar(4);
        getSession().setAttribute("codeImg", code);
        System.out.println((String) getSession().getAttribute("codeImg"));
        getResponse().setContentType("image/jpeg");
        try {
            ImageIO.write(new CodeImgUtil().codeImg(code), "jpg", getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/selectUserById")
    public Result selectUserById(String userId) {
        System.out.println(userId);
        Long userID = NumberUtil.toLong(userId);
        return Result.success(userService.selectUserById(userID));
    }

    @RequestMapping("/user/selectUserByName")
    public User selectUserByName(String username) {
        System.out.println(username);
        return userService.selectUserByName(username);
    }

    @RequestMapping("/user/selectUsersByName")
    public Result selectUsersByName(String username, int pageNum, int pageSize) {
        return Result.success(userService.selectUsersByName(username, pageNum, pageSize));
    }

    @RequestMapping("/user/deleteUserById")
    public Result deleteUserById(Long userId) {
        return Result.success(userService.deleteUserById(userId));
    }

    @RequestMapping("/user/updateUserById")
    public Result updateUserById(User user) {
        System.out.println(user.toString());
        return Result.success(userService.updateUserById(user));
    }
}
