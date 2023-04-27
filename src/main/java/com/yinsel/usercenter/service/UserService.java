package com.yinsel.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinsel.usercenter.common.BaseResponse;
import com.yinsel.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinsel
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-04-18 21:34:59
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 确认密码
     * @param planetCode 星球编号
     * @return 返回用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode);

    // todo 修改为自定义异常

    /**
     * @param userAccount        用户账户
     * @param userPassword       用户密码
     * @param request       Request对象
     * @return 返回脱敏的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param oringinUser
     * @return
     */
    User getSafetyUser(User oringinUser);


    boolean delete(long id);
}
