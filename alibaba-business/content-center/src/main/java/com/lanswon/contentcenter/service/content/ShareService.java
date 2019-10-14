package com.lanswon.contentcenter.service.content;

import com.lanswon.contentcenter.dao.midusershare.MidUserShareMapper;
import com.lanswon.contentcenter.dao.share.ShareMapper;
import com.lanswon.contentcenter.domain.dto.content.ShareDTO;
import com.lanswon.contentcenter.domain.entity.share.Share;
import com.lanswon.feign.entity.user.User;
import com.lanswon.feign.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;
    private final MidUserShareMapper midUserShareMapper;
    private final UserService userService;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人id
        Integer userId = share.getUserId();

//        User user = this.restTemplate.getForObject("http://user-center/users/{id}", User.class,userId);
        User user=userService.findById(id);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(user.getWxNickname());
        return shareDTO;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        // 用HTTP GET方法去请求，并且返回一个对象
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
            "http://localhost:8080/users/{id}",
            String.class, 2
        );

        System.out.println(forEntity.getBody());
        // 200 OK
        // 500
        // 502 bad gateway...
        System.out.println(forEntity.getStatusCode());
    }

//    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
//        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
//        Share share = this.shareMapper.selectByPrimaryKey(id);
//        if (share == null) {
//            throw new IllegalArgumentException("参数非法！该分享不存在！");
//        }
//        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
//            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
//        }
//
//        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
//        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
//            // 发送半消息。。
//            String transactionId = UUID.randomUUID().toString();
//
//            this.source.output()
//                .send(
//                    MessageBuilder
//                        .withPayload(
//                            UserAddBonusMsgDTO.builder()
//                                .userId(share.getUserId())
//                                .bonus(50)
//                                .build()
//                        )
//                        // header也有妙用...
//                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
//                        .setHeader("share_id", id)
//                        .setHeader("dto", JSON.toJSONString(auditDTO))
//                        .build()
//                );
//        } else {
//            this.auditByIdInDB(id, auditDTO);
//        }
//        return share;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
//        Share share = Share.builder()
//            .id(id)
//            .auditStatus(auditDTO.getAuditStatusEnum().toString())
//            .reason(auditDTO.getReason())
//            .build();
//        this.shareMapper.updateByPrimaryKeySelective(share);
//
//        // 4. 把share写到缓存
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
//        this.auditByIdInDB(id, auditDTO);
//
//        this.rocketmqTransactionLogMapper.insertSelective(
//            RocketmqTransactionLog.builder()
//                .transactionId(transactionId)
//                .log("审核分享...")
//                .build()
//        );
//    }
//
//    public PageInfo<Share> q(String title, Integer pageNo, Integer pageSize, Integer userId) {
//        PageHelper.startPage(pageNo, pageSize);
//        List<Share> shares = this.shareMapper.selectByParam(title);
//        List<Share> sharesDeal;
//        // 1. 如果用户未登录，那么downloadUrl全部设为null
//        if (userId == null) {
//            sharesDeal = shares.stream()
//                .peek(share -> {
//                    share.setDownloadUrl(null);
//                })
//                .collect(Collectors.toList());
//        }
//        // 2. 如果用户登录了，那么查询一下mid_user_share，如果没有数据，那么这条share的downloadUrl也设为null
//        else {
//            sharesDeal = shares.stream()
//                .peek(share -> {
//                    MidUserShare midUserShare = this.midUserShareMapper.selectOne(
//                        MidUserShare.builder()
//                            .userId(userId)
//                            .shareId(share.getId())
//                            .build()
//                    );
//                    if (midUserShare == null) {
//                        share.setDownloadUrl(null);
//                    }
//                })
//                .collect(Collectors.toList());
//        }
//        return new PageInfo<>(sharesDeal);
//    }
//
//    public Share exchangeById(Integer id, HttpServletRequest request) {
//        Object userId = request.getAttribute("id");
//        Integer integerUserId = (Integer) userId;
//
//        // 1. 根据id查询share，校验是否存在
//        Share share = this.shareMapper.selectByPrimaryKey(id);
//        if (share == null) {
//            throw new IllegalArgumentException("该分享不存在！");
//        }
//        Integer price = share.getPrice();
//
//        // 2. 如果当前用户已经兑换过该分享，则直接返回
//        MidUserShare midUserShare = this.midUserShareMapper.selectOne(
//            MidUserShare.builder()
//                .shareId(id)
//                .userId(integerUserId)
//                .build()
//        );
//        if (midUserShare != null) {
//            return share;
//        }
//
//        // 3. 根据当前登录的用户id，查询积分是否够
//        UserDTO userDTO = this.userCenterFeignClient.findById(integerUserId);
//        if (price > userDTO.getBonus()) {
//            throw new IllegalArgumentException("用户积分不够用！");
//        }
//
//        // 4. 扣减积分 & 往mid_user_share里插入一条数据
//        this.userCenterFeignClient.addBonus(
//            UserAddBonseDTO.builder()
//                .userId(integerUserId)
//                .bonus(0 - price)
//                .build()
//        );
//        this.midUserShareMapper.insert(
//            MidUserShare.builder()
//                .userId(integerUserId)
//                .shareId(id)
//                .build()
//        );
//        return share;
//    }
}

