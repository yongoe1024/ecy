package com.yongoe.ecy.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yongoe.ecy.system.entity.Menu;
import com.yongoe.ecy.system.entity.Role;
import com.yongoe.ecy.system.entity.User;
import com.yongoe.ecy.system.service.MenuService;
import com.yongoe.ecy.utils.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private MenuService menuService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        User user = UserThreadLocal.get();
        for (Role role : user.getRoleList()) {
            if (role.getId() == 1)
                return true;
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();
        for (Menu menu : menuService.list()) {
            if (!StringUtils.isEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), requestURI)) {
                // 返回 该菜单的 所需权限
                List<Role> rolesByMenuId = menuService.getRolesByMenuId(menu.getId());
                for (Role role : rolesByMenuId) {
                    for (Role userRole : user.getRoleList()) {
                        if (role.getId().equals(userRole.getId()))
                            return true;
                    }
                }
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                R error = R.error(403, "权限不足，请联系管理员！");
                writer.write(new ObjectMapper().writeValueAsString(error));
                writer.flush();
                writer.close();
                return false;
            }
        }
        //没有匹配的，就放行
        return true;

    }

    //防止内存溢出
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserThreadLocal.remove();
    }
}