package com.xxx.aspect;

import com.xxx.annoation.RequiredPermission;
import com.xxx.exceptions.AuthException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;
    @Around(value = "@annotation(com.xxx.annoation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if (null == permissions || permissions.size() < 1) {
            throw new AuthException();
        }

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        RequiredPermission requiredPermission = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);
        if (!(permissions.contains(requiredPermission.code()))){
            throw new AuthException();
        }
        result = pjp.proceed();
        return result;
    }
}
