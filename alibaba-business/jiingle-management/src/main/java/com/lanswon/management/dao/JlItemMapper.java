package com.lanswon.management.dao;

import com.lanswon.management.domain.dto.ItemDto;
import com.lanswon.management.domain.entity.JlItem;
import com.lanswon.management.domain.vo.ItemVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JlItemMapper extends Mapper<JlItem> {


    ItemVo selectByKey(int itemID);

    List<ItemVo> selectList(ItemDto itemDto);

    int count(ItemDto itemDto);

}