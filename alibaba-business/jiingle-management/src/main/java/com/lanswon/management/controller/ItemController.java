package com.lanswon.management.controller;

import com.lanswon.management.domain.dto.ItemDto;
import com.lanswon.management.domain.entity.JlItem;
import com.lanswon.management.domain.vo.SimpleResponse;
import com.lanswon.management.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** 物资controller
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 17:07
 */
@RestController
@RequestMapping("/item")
@Api(tags="物资基础信息---增删改查")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/list")
    @ApiOperation(value="查询所有物资信息")
    public SimpleResponse queryItems(@RequestBody ItemDto itemDto) {
        return itemService.items(itemDto);
    }

    //暂时不需要，后期删除
//    @GetMapping("/{itemId}")
//    @ApiOperation(value="查询指定物资信息")
//    public SimpleResponse item(@PathVariable int itemId){
//        return itemService.item(itemId);
//    }

    @PostMapping("/add")
    @ApiOperation(value="新增物资")
    public SimpleResponse addItem(@RequestBody @Valid JlItem item){
         return itemService.addItem(item);
    }

    @PutMapping("/update")
    @ApiOperation(value="修改物资")
    public SimpleResponse updateItem(@RequestBody @Valid JlItem item){
        return itemService.updateItem(item);
    }



}
