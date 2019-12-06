package com.lanswon.management.service;

import com.lanswon.management.domain.dto.ItemDto;
import com.lanswon.management.domain.entity.JlItem;
import com.lanswon.management.domain.vo.SimpleResponse;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 15:31
 */
public interface ItemService {

    /**
     * 新增物资
     * @param item
     */
    SimpleResponse addItem (JlItem item);

    /**
     * 更新物资
     * @param item
     * @return
     */
    SimpleResponse updateItem(JlItem item);

    /**
     * 查询所有物资信息
     * @param itemDto
     * @return
     */
    SimpleResponse items(ItemDto itemDto);

    /**
     * 查询指定物资ID物资信息
     * @param itemID
     * @return
     */
    SimpleResponse item(int itemID);
}
