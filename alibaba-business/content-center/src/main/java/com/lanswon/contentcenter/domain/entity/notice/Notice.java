package com.lanswon.contentcenter.domain.entity.notice;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "notice")
public class Notice {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否显示 0:否 1:是
     */
    @Column(name = "show_flag")
    private Boolean showFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}