package com.lanswon.uumfeign.domain.entity;


import lombok.*;

/**
 * 角色实体类
 *
 * @author Jaswine
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRole  {

    private long id;

    private String roleName;
}
