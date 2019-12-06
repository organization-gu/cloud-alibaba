package com.lanswon.management.service.impl;

import com.lanswon.management.dao.JlItemTypeMapper;
import com.lanswon.management.domain.dto.ItemTypeDto;
import com.lanswon.management.domain.entity.JlItemType;
import com.lanswon.management.domain.page.Page;
import com.lanswon.management.domain.vo.SimpleResponse;
import com.lanswon.management.service.ItemTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/4 9:46
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemTypeServiceImpl implements ItemTypeService {

    private final JlItemTypeMapper itemTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse addItemType(JlItemType itemType) {
        itemTypeMapper.insert(itemType);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .description("新增成功")
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse updateItemType(JlItemType itemType) {
        itemTypeMapper.updateByPrimaryKeySelective(itemType);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .description("修改成功")
                .build();
    }

    @Override
    public SimpleResponse itemTypes(ItemTypeDto itemTypeDto) {
        List<JlItemType> itemTypes = itemTypeMapper.selectList(itemTypeDto);
        int count =itemTypeMapper.count();
        Page<JlItemType> page=new Page<>(itemTypeDto.getPage(),itemTypeDto.getLimit(),count);
        page.setList(itemTypes);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .object(page)
                .build();
    }

    @Override
    public SimpleResponse itemType(int typeCode) {
        JlItemType itemType = itemTypeMapper.selectByPrimaryKey(typeCode);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .object(itemType)
                .build();
    }
}
