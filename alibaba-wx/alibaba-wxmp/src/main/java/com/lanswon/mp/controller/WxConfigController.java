package com.lanswon.mp.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.mp.api.WxMpMassMessageService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpCurrentAutoReplyInfo;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/28 13:02
 */
@RestController
@Slf4j
@RequestMapping("/wxconfig")
public class WxConfigController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 微信连接
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @param response
     * @throws IOException
     */
    @GetMapping(value="/access")
    public void weChatAccess(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) throws IOException {
        boolean f=wxMpService.checkSignature(timestamp,nonce,signature);
        log.debug("微信连接签名验证结果[{}]",f);
        if(f){
            response.getWriter().write(echostr);
            response.getWriter().close();
        }

    }

    /**
     * 回复消息
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping(value="/access")
    public void responseMessage(HttpServletRequest request, HttpServletResponse response) throws WxErrorException, IOException {
        WxMpXmlMessage mpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        log.debug("微信事件消息[{}]",mpXmlMessage);
        if(mpXmlMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)){
            if(mpXmlMessage.getEvent().equals(WxConsts.EventType.SUBSCRIBE)){
                TextBuilder textBuilder = new TextBuilder();
                WxMpXmlOutTextMessage outTextMessage = textBuilder
                        .content("谢谢关注,精彩进献").build();
                outTextMessage.setToUserName(mpXmlMessage.getFromUser());
                outTextMessage.setFromUserName(mpXmlMessage.getToUser());
                log.debug("关注事件回复[{}]",outTextMessage.toXml());
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(outTextMessage.toXml());
            }
        }

    }

    /**
     *   构造网页授权
     */
    @GetMapping(value="/authorize")
    public ModelAndView authorize(HttpServletRequest request) {
        //回掉地址,可以在配置文件里注入
        String url="http://guyawei.top/wxconfig/openid";
        String redirectURI=wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO,null);
        return new ModelAndView("redirect:" + redirectURI);

    }

    /**
     *      通过请求参数code获取当前微信用户信息并跳转页面
     * @param code
     * @throws WxErrorException
     */
    @GetMapping(value="/openid")
    public ModelAndView weiChatIndex(String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken =wxMpService.oauth2getAccessToken(code);
        wxMpOAuth2AccessToken.getAccessToken();
        wxMpOAuth2AccessToken.getOpenId();//获取openId在微信充值的时候用
        log.debug("当前微信用户信息[{}]",wxMpOAuth2AccessToken);
        return new ModelAndView("redirect:" + "https://guyawei.top");
    }

    @GetMapping("/massMsg")
    public void massMsg() throws WxErrorException {
        WxMpUserService userService = wxMpService.getUserService();
        WxMpUserList wxMpUserList = userService.userList(null);

        WxMpMassMessageService massMessageService = wxMpService.getMassMessageService();
        WxMpMassOpenIdsMessage mpMassOpenIdsMessage = new WxMpMassOpenIdsMessage();
        mpMassOpenIdsMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        mpMassOpenIdsMessage.setContent("群发消息test Msg");
        if(!CollectionUtils.isEmpty(wxMpUserList.getOpenids())){
            for(String str:wxMpUserList.getOpenids()){
                mpMassOpenIdsMessage.addUser(str);
            }
            WxMpMassSendResult wxMpMassSendResult = massMessageService.massOpenIdsMessageSend(mpMassOpenIdsMessage);
            log.debug("群发消息结果[{}]",wxMpMassSendResult);
        }

    }

}
