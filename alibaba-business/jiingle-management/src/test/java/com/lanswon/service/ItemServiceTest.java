package com.lanswon.service;

import com.lanswon.management.CloudJinLeApplication;
import com.lanswon.management.domain.entity.JlItem;
import com.lanswon.management.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 15:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudJinLeApplication.class )
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    public void addItem(){
        JlItem item =new JlItem();
        item.setName("草纸");
        itemService.addItem(item);
    }

    @Test
    public void updateItem(){
        JlItem item =new JlItem();
        item.setId(1);
        item.setName("草纸3");
        itemService.updateItem(item);
    }


}
