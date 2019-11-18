package com.lanswon.authserver.controller;

import com.lanswon.authcore.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/15 15:18
 */
@RestController
public class UserController {

    /**
     * 当使用token来获取信息时，不支持@AuthenticationPrincipal注解
     * 可以加入io.jsonwebtoken依赖来解析token里面的信息
     * @param user
     * @return
     */
    @Autowired
    SecurityProperties securityProperties;

    @GetMapping("/me")  //@AuthenticationPrincipal  UserDetails
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header,"bearer ");
        Claims claims= Jwts.parser()
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        String clientId = (String) claims.get("client_id");
        System.out.println("clientId="+clientId);
        return user;
    }
}
