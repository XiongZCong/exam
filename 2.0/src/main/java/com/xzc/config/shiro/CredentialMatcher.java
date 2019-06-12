package com.xzc.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CredentialMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("CredentialMatcher.doCredentialsMatch");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());//用户填写
        String dbPassword = (String) info.getCredentials();//数据库
        Md5Hash md5Hash = new Md5Hash(password,"salt",3);
        System.out.println(dbPassword);
        System.out.println(md5Hash.toString());
        return this.equals(md5Hash.toString(), dbPassword);
    }
}
