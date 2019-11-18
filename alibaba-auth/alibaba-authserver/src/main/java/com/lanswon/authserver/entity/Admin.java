package com.lanswon.authserver.entity;

import com.lanswon.uumfeign.domain.entity.AuthUrl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;
import java.util.Set;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/18 14:10
 */
@Setter
@Getter
public class Admin extends SocialUser {

    private Set<AuthUrl> urls;

    public Admin(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Set<AuthUrl> urls) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.urls=urls;
    }
}
