package com.lanswon.authdemo.service.impl;

import com.lanswon.authdemo.service.RbacService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/15 10:41
 */
@Component("rbacService")
@Slf4j
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;

        if (! (principal instanceof AnonymousAuthenticationToken)) {
            //如果用户名是admin，就永远返回true
//            if (StringUtils.equals(((Admin) principal).getUsername(), "admin")) {
//                hasPermission = true;
//            }
//            else {
                // 读取用户所拥有权限的所有URL
//                Set<String> urls = ((Admin) principal).getUrls();
                Set<String>urls = new HashSet<>();
                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
//            }
        }

        return hasPermission;
    }
}
