package com.yinsel.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinsel.usercenter.common.BaseResponse;
import com.yinsel.usercenter.common.ErrorCode;
import com.yinsel.usercenter.common.ResultUtlis;
import com.yinsel.usercenter.exception.BusinessException;
import com.yinsel.usercenter.mapper.UserMapper;
import com.yinsel.usercenter.model.domain.User;
import com.yinsel.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yinsel.usercenter.constant.UserConstant.USER_LOGIN_STATE;

;

/**
 * @author yinsel
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-04-18 21:34:59
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * 盐值-混淆密码
     */
    private final String SALT = "yupi";

    @Resource
    private UserMapper userMapper;
    // todo 修改为自定义异常
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号过短");
        }

        if (userPassword.length() < 8 || !userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户密码过短");
        }

        //星球编号校验
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"星球编号过长");
        }

        //账号不能包含特殊字符
        String regx = "\\W";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(userAccount);
        while (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号不能包含特殊字符");
        }

        //账号不能重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号已存在");
        }

        //星球编号不能重复
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"星球编号已存在");
        }

        //加密
        final String SALT = "yupi";
        String passwordOfDigest = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(passwordOfDigest);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库异常");
        }
        return user.getId();

    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户账号过短");
        }

        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用密码过短");
        }

        //账户不能包含特殊字符
        String regx = "\\W";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(userAccount);
        while (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账户不能包含特殊字符");
        }

        //加密
        String passwordOfDigest = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", passwordOfDigest);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"用户名或密码错误");
        }

        User safetyUser = getSafetyUser(user);
        //记录用户的登录态，并返回脱敏用户信息
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param oringinUser
     * @return
     */
    @Override
    public User getSafetyUser(User oringinUser) {
        if (oringinUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(oringinUser.getId());
        safetyUser.setUsername(oringinUser.getUsername());
        safetyUser.setUserAccount(oringinUser.getUserAccount());
        safetyUser.setAvatarUrl(oringinUser.getAvatarUrl());
        safetyUser.setGender(oringinUser.getGender());
        safetyUser.setPhone(oringinUser.getPhone());
        safetyUser.setEmail(oringinUser.getEmail());
        safetyUser.setUserRole(oringinUser.getUserRole());
        safetyUser.setPlanetCode(oringinUser.getPlanetCode());
        safetyUser.setUserStatus(oringinUser.getUserStatus());
        safetyUser.setCreateTime(oringinUser.getCreateTime());
        safetyUser.setUpdateTime(oringinUser.getUpdateTime());
        return safetyUser;
    }

    @Override
    public boolean delete(long id) {
        int result = userMapper.deleteById(id);
        return result == 1 ? true : false;
    }
}




