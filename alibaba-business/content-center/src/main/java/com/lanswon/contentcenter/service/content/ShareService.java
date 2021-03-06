package com.lanswon.contentcenter.service.content;

import com.alibaba.fastjson.JSONObject;
import com.lanswon.contentcenter.dao.midusershare.MidUserShareMapper;
import com.lanswon.contentcenter.dao.share.ShareMapper;
import com.lanswon.contentcenter.domain.dto.content.ShareAuditDTO;
import com.lanswon.contentcenter.domain.dto.content.ShareDTO;
import com.lanswon.contentcenter.domain.entity.mqlog.RocketmqTransactionLog;
import com.lanswon.contentcenter.domain.entity.share.Share;
import com.lanswon.contentcenter.domain.enums.AuditStatusEnum;
import com.lanswon.contentcenter.rocketmq.AddBonusTransactionListener;
import com.lanswon.contentcenter.dao.mqlog.RocketmqTransactionLogMapper;
import com.lanswon.userfeign.domain.dto.messaging.UserAddBonusMsgDTO;
import com.lanswon.userfeign.entity.user.User;
import com.lanswon.userfeign.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;
    private final MidUserShareMapper midUserShareMapper;
    private final UserService userService;
    private final RocketMQTemplate rocketMQTemplate;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    private final Source source;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人id
        //Integer userId = share.getUserId();

        //User user = this.restTemplate.getForObject("http://user-center/users/{id}", User.class,userId);
        User user=userService.findById(id);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(user.getWxNickname());
        return shareDTO;
    }

    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        //2. 审核资源，将状态设为PASS/REJECT 这里的代码不需要了 基于rocketmq分布式事务
        //share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        //this.shareMapper.updateByPrimaryKey(share);

        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {

            //通过restTemplate发送消息
            //this.sendMessageByRestemplate(share);

            //通过rocketmq发送消息
            //this.sendMessageByRocketMq(id,auditDTO);

            //通过springCloudStream发送消息
            this.sendMessageByStream(id,auditDTO);

        }else{
            this.auditByIdInDB(share.getId(),auditDTO);
        }


        return share;
    }

    private void sendMessageByRestemplate(Share share){
        this.rocketMQTemplate.convertAndSend("add-bonus",UserAddBonusMsgDTO.builder()
        .userId(share.getUserId())
        .bonus(50)
        .build());
    }

    private void sendMessageByRocketMq(Integer id, ShareAuditDTO auditDTO){
        AddBonusTransactionListener.flag=1;
        //发送半消息
        String transactionId = UUID.randomUUID().toString();
        this.rocketMQTemplate.sendMessageInTransaction(
                "tx-content-group",
                "add-bonus",
                MessageBuilder
                        .withPayload(
                                UserAddBonusMsgDTO
                                        .builder()
                                        .userId(id)
                                        .bonus(50)
                                        .build())
                        // header也有妙用...
                        .setHeader(RocketMQHeaders.TRANSACTION_ID,transactionId)
                        .setHeader("share_id", id)
                        .build(),
                        //arg有大用处
                        auditDTO);
        log.debug("sendMessageByRocketMq发送数据={}",auditDTO.toString());
    }

    private void sendMessageByStream(Integer id, ShareAuditDTO auditDTO){
        AddBonusTransactionListener.flag=2;
        //发送半消息
        String transactionId = UUID.randomUUID().toString();
        this.source
                .output()
                .send(MessageBuilder
                        .withPayload(UserAddBonusMsgDTO
                                .builder()
                                .userId(id)
                                .bonus(50)
                                .build())
                        .setHeader(RocketMQHeaders.TRANSACTION_ID,transactionId)
                        .setHeader("share_id", id)
                        //对象在传输时会变成字符串，在使用时无法解析出对象
                        .setHeader("dto", JSONObject.toJSONString(auditDTO))
                        .build());
        log.debug("sendMessageByStream发送数据={}",auditDTO.toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .updateTime(new Date())
                .reason(auditDTO.getReason())
                .build();
        this.shareMapper.updateByPrimaryKeySelective(share);

        // 4. 把share写到缓存 暂时不做
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        this.auditByIdInDB(id, auditDTO);

        this.rocketmqTransactionLogMapper.insertSelective(
            RocketmqTransactionLog.builder()
                .transactionId(transactionId)
                .log("审核分享...")
                .build()
        );
    }


























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

