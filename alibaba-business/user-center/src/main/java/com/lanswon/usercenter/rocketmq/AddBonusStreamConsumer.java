package com.lanswon.usercenter.rocketmq;

import com.lanswon.userfeign.domain.dto.messaging.UserAddBonusMsgDTO;
import com.lanswon.usercenter.service.user.UserCenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AddBonusStreamConsumer {

    private final UserCenterService userService;

    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO message) {
        message.setEvent("CONTRIBUTE");
        message.setDescription("投稿加积分..");
        this.userService.addBonus(message);
    }
}
