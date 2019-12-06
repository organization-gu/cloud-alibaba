package com.lanswon.management.service.impl;

import com.lanswon.management.dao.JlItemMapper;
import com.lanswon.management.domain.dto.ItemDto;
import com.lanswon.management.domain.entity.JlItem;
import com.lanswon.management.domain.page.Page;
import com.lanswon.management.domain.vo.ItemVo;
import com.lanswon.management.domain.vo.SimpleResponse;
import com.lanswon.management.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 15:30
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {

    private final JlItemMapper itemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse addItem(JlItem item) {
        item.setCrtDate(new Date());
        item.setLastDate(new Date());
        itemMapper.insert(item);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .description("新增成功")
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponse updateItem(JlItem item) {
        item.setLastDate(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK.value())
                .description("修改成功")
                .build();
    }

    @Override
    public SimpleResponse items(ItemDto itemDto) {
        List<ItemVo> itemVoList = itemMapper.selectList(itemDto);
        int count = itemMapper.count(itemDto);
        Page<ItemVo> page=new Page<>(itemDto.getPage(),itemDto.getLimit(),count);
        page.setList(itemVoList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK.value())
                .object(page).build();
    }

    @Override
    public SimpleResponse item(int itemID) {
        ItemVo item = itemMapper.selectByKey(itemID);
        return SimpleResponse.builder()
                .status(HttpStatus.OK.value())
                .object(item)
                .build();
    }
}
