package com.niukedemo.aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月22日 16:59
 */
@Slf4j
@Component
@Aspect
public class ServiceAspect {
    /**
     * @Description:切点
     * @Param: []
     * @return: void
     * @Author: 刘欢
     */
//    @Pointcut("execution(* com.niukedemo.service.*.*(..))")
    @Pointcut("execution(* com.niukedemo.service.*.*(..))")
    public void pointcut() {
    }

    /**
     * @Description:记录客户的ip的ip地址，时间，以及访问了网站的何种业务层方法
     * @Param: [joinPoint]
     * @return: void
     * @Author: 刘欢
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes==null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
    }
}
