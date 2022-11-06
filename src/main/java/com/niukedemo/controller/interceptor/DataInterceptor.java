package com.niukedemo.controller.interceptor;

import com.niukedemo.entity.User;
import com.niukedemo.service.DataService;
import com.niukedemo.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘欢
 * @date 2022年11月06日 21:50
 */
@Component
public class DataInterceptor implements HandlerInterceptor {
    @Autowired
    private DataService dataService;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteHost();
        dataService.recordUV(ip);
        User user = hostHolder.getUsers();
        if (user!=null){
            dataService.recordDAU(user.getId());
        }
        return true;
    }
}
