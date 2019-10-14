package com.lanswon.contentcenter.controller.content;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {
//    private final ShareService shareService;
//    @PutMapping("/audit/{id}")
//    @CheckAuthorization("admin")
//    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO) {
//        return this.shareService.auditById(id, auditDTO);
//    }

}
