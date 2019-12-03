package com.lanswon.mp.config;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/** MP菜单配置
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/28 14:34
 */
@Configuration
public class MenuConfig {

    @Autowired
    private WxMpService wxMpService;

    @PostConstruct
    public void creatMeun() throws WxErrorException {
        WxMpMenuService menuService = wxMpService.getMenuService();
        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> buttons = new ArrayList<>();
        WxMenuButton button_1 = new WxMenuButton();
        button_1.setType("view");
        button_1.setName("个人中心");
        button_1.setUrl("http://guyawei.top/wxconfig/authorize");
        WxMenuButton button_2 = new WxMenuButton();
        button_2.setType("view");
        button_2.setName("考勤中心");
        button_2.setUrl("http://www.baidu.com");
        buttons.add(button_1);
        buttons.add(button_2);
        wxMenu.setButtons(buttons);
        menuService.menuCreate(wxMenu);
    }
}
