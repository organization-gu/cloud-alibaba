package com.lanswon.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.lanswon.contentcenter.domain.dto.content.ShareAuditDTO;
import com.lanswon.contentcenter.dao.mqlog.RocketmqTransactionLogMapper;
import com.lanswon.contentcenter.domain.entity.mqlog.RocketmqTransactionLog;
import com.lanswon.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * 分布式事务处理
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@RocketMQTransactionListener(txProducerGroup = "tx-content-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {
    public static  int flag = 0;

    private final ShareService shareService;

    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
        RocketMQLocalTransactionState state = null;
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));
        ShareAuditDTO auditDTO = null;
        if(flag==1){
             auditDTO= (ShareAuditDTO) arg;
        }else if(flag==2){
            String dtoString = (String) headers.get("dto");
             auditDTO = JSON.parseObject(dtoString, ShareAuditDTO.class);
        }
        log.debug("发送消息成功执行本地事务arg==[{}]",auditDTO);
        try {
            this.shareService.auditByIdWithRocketMqLog(shareId, auditDTO, transactionId);
            state = RocketMQLocalTransactionState.COMMIT;
            log.debug("发送消息成功执行本地事务arg==[{}],处理消息状态==[{}]",auditDTO,state);
            return state;
        } catch (Exception e) {
            log.error(e.getMessage());
            state = RocketMQLocalTransactionState.ROLLBACK;
            log.debug("处理消息状态==[{}]",state);
            return state;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        MessageHeaders headers = msg.getHeaders();
        RocketMQLocalTransactionState state = null;
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        // select * from xxx where transaction_id = xxx
        RocketmqTransactionLog transactionLog = this.rocketmqTransactionLogMapper.selectOne(
            RocketmqTransactionLog.builder()
                .transactionId(transactionId)
                .build()
        );
        if (transactionLog != null) {
            state = RocketMQLocalTransactionState.COMMIT;
            log.debug("未收到二次确认主动查询事务日志==[{}],处理消息状态==[{}]",transactionLog,state);
            return RocketMQLocalTransactionState.COMMIT;
        }
        state=RocketMQLocalTransactionState.ROLLBACK;
        log.debug("处理消息状态==[{}]",state);
        return state;
    }
}
