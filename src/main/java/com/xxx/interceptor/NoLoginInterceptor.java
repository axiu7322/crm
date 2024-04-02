package com.xxx.interceptor;


import com.xxx.dao.UserMapper;
import com.xxx.exceptions.NoLoginException;
import com.xxx.utils.LoginUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

    public class NoLoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取cookie
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if (null == userId || userMapper.selectByPrimaryKey(userId) == null) {
            throw new NoLoginException();
        }
        return true;
    }
}
