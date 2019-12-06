package com.lanswon.management.service;

import com.lanswon.management.domain.dto.ItemTypeDto;
import com.lanswon.management.domain.entity.JlItemType;
import com.lanswon.management.domain.vo.SimpleResponse;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/4 9:34
 */
public interface ItemTypeService {

    /**
     * 新增物资类别
     * @param itemType
     * @return
     */
    SimpleResponse addItemType(JlItemType itemType);

    /**
     * 更新物资类别
     * @param itemType
     * @return
     */
    SimpleResponse updateItemType(JlItemType itemType);

    /**
     * 查询所有物资类别
     * @return
     */
    SimpleResponse itemTypes(ItemTypeDto itemTypeDto);

    /**
     * 查询指定物资类别
     * @param typeCode
     * @return
     */
    SimpleResponse itemType(int typeCode);
}
