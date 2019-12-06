package com.lanswon.management.controller;

import com.lanswon.management.domain.dto.ItemTypeDto;
import com.lanswon.management.domain.entity.JlItemType;
import com.lanswon.management.domain.vo.SimpleResponse;
import com.lanswon.management.service.ItemTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description: 物资类别controller
 * @Author GU-YW
 * @Date 2019/12/4 8:58
 */
@RestController
@RequestMapping("/itemType")
@Api(tags="物资类别基础信息---增删改查")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class ItemTypeController {

    private final ItemTypeService itemTypeService;

    @PostMapping("/list")
    @ApiOperation(value="查询所有物资类别信息")
    public SimpleResponse queryItems(@RequestBody ItemTypeDto itemTypeDto) {
        return itemTypeService.itemTypes(itemTypeDto);
    }

    @GetMapping("/{itemTypeId}")
    @ApiOperation(value="查询指定物资信类别息")
    public SimpleResponse item(@PathVariable int itemTypeId){
        return itemTypeService.itemType(itemTypeId);
    }

    @PostMapping("/add")
    @ApiOperation(value="新增物资类别")
    public SimpleResponse addItem(@RequestBody @Valid JlItemType itemType){
        return itemTypeService.addItemType(itemType);
    }

    @PutMapping("/update")
    @ApiOperation(value="修改物资类别")
    public SimpleResponse updateItem(@RequestBody @Valid JlItemType itemType){
        return itemTypeService.updateItemType(itemType);
    }
}
