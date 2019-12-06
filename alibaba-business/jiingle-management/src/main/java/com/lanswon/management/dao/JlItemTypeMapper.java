package com.lanswon.management.dao;

import com.lanswon.management.domain.dto.ItemTypeDto;
import com.lanswon.management.domain.entity.JlItemType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JlItemTypeMapper extends Mapper<JlItemType> {

    List<JlItemType> selectList(ItemTypeDto itemTypeDto);

    int count();
}