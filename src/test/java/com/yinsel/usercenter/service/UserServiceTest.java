package com.yinsel.usercenter.service;

import com.yinsel.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 用户服务测试
 * @author yinsel
 */

@SpringBootTest
class UserServiceTest {

    @Test
    void testDigest() {
        String passwd = "dasiojfsail";
        String passwdOfDigest = DigestUtils.md5DigestAsHex(("123" + passwd).getBytes());
        System.out.println(passwdOfDigest);
    }

    @Test
    void testRegister() {
        userService.userRegister("yinse l888","xxxxxxxx","xxxxxxxx","12341 ");
    }

    @Autowired
    private UserService userService;
    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("yinsel");
        user.setUserAccount("admin");
        user.setUserPassword("123456789");
        user.setAvatarUrl("https://pic.code-nav.cn/user_avatar/1628290566401896450/4tJFqUG0-WechatIMG14.jpeg");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        user.setIsDelete(0);
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

}