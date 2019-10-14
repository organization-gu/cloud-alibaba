package com.lanswon.contentcenter.domain.entity.midusershare;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "mid_user_share")
public class MidUserShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * share.id
     */
    @Column(name = "share_id")
    private Integer shareId;

    /**
     * user.id
     */
    @Column(name = "user_id")
    private Integer userId;
}