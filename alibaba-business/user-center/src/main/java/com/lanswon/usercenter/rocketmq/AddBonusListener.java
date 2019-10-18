package com.lanswon.usercenter.rocketmq;

import com.lanswon.usercenter.dao.bonus.BonusEventLogMapper;
import com.lanswon.usercenter.dao.user.UserMapper;
import com.lanswon.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.lanswon.usercenter.domain.entity.bonus.BonusEventLog;
import com.lanswon.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @Author GU-YW
 * @Date 2019/10/17 17:12
 */
@Component
@RocketMQMessageListener(consumerGroup = "usr-group",topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        Integer userId=userAddBonusMsgDTO.getUserId();
        User user = this.userMapper.selectByPrimaryKey(userId);
        if(Objects.nonNull(user)){
            user.setBonus(user.getBonus()+userAddBonusMsgDTO.getBonus());
            user.setUpdateTime(new Date());
            this.userMapper.updateByPrimaryKey(user);
            BonusEventLog build = BonusEventLog.builder()
                    .userId(userId)
                    .value(user.getBonus())
                    .createTime(new Date())
                    .description("增加积分")
                    .event("增加积分")
                    .build();
            this.bonusEventLogMapper.insert(build);
        }

    }
}
