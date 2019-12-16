package com.lanswon.authserver.controller;

import com.lanswon.authserver.entity.Result;
import com.lanswon.authserver.entity.SysSignupInfo;
import com.lanswon.authserver.service.SysSignUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/12 21:12
 */
@Controller
@Api(tags="系统注册")
@CrossOrigin
@RequestMapping("/sys")
public class SysSignUpController {

    @Resource
    SysSignUpService sysSignUpService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @PostMapping("signup")
    @ApiOperation(value="第三方系统注册生成ClientId")
    @ResponseBody
    public Result signUp(@RequestBody SysSignupInfo sysSignupInfo){
        return sysSignUpService.signUp(sysSignupInfo);
    }

    @GetMapping("/client")
    @ApiOperation(value="获取当前第三方系统client信息")
    @ResponseBody
    public Result client(@RequestBody SysSignupInfo sysSignupInfo){
        return sysSignUpService.client(sysSignupInfo);
    }

    @GetMapping("/login")
    @ApiOperation(value="获取当前第三方系统登录")
    public ModelAndView login(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView("login");
        String header = request.getParameter("client");

        if (header == null){
            throw new UnapprovedClientAuthenticationException("请求中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }
        modelAndView.getModel().put("basic",header);
        return  modelAndView;
    }



    @GetMapping("/token/{id}")
    @ApiOperation(value="获取当前第三方登录Token")
    @ResponseBody
    public String getToken(@PathVariable String id) {
        return (String)redisTemplate.opsForValue().get(id);
    }

    /**
     * 从header获取Authentication信息 --- 》 clientId和clientSecret
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
