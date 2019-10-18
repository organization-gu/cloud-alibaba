package com.lanswon.contentcenter.controller.content;

import com.lanswon.contentcenter.domain.dto.content.ShareAuditDTO;
import com.lanswon.contentcenter.domain.entity.share.Share;
import com.lanswon.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {

    private final ShareService shareService;
    @PutMapping("/audit/{id}")
//    @CheckAuthorization("admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO) {
        return this.shareService.auditById(id, auditDTO);
    }

}
