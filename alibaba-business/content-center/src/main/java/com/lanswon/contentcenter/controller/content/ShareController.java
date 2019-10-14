package com.lanswon.contentcenter.controller.content;

import com.lanswon.contentcenter.domain.dto.content.ShareDTO;
import com.lanswon.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {

    private final ShareService shareService;

    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }

//    @GetMapping("/q")
//    public PageInfo<Share> q(
//        @RequestParam(required = false) String title,
//        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
//        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
//        @RequestHeader(value = "X-Token", required = false) String token) {
//        if (pageSize > 100) {
//            pageSize = 100;
//        }
//        Integer userId = null;
//        if (StringUtils.isNotBlank(token)) {
//            Claims claims = this.jwtOperator.getClaimsFromToken(token);
//            userId = (Integer) claims.get("id");
//        }
//        return this.shareService.q(title, pageNo, pageSize, userId);
//    }
//
//    @GetMapping("/exchange/{id}")
//    @CheckLogin
//    public Share exchangeById(@PathVariable Integer id, HttpServletRequest request) {
//        return this.shareService.exchangeById(id, request);
//    }
}
