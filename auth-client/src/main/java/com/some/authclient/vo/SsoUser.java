package com.some.authclient.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-04 17:40
 */
@Data
public class SsoUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private List<Role> role;

    public SsoUser(Integer id, String username, String password, List<Role> role ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public SsoUser(String username, String password, List<Role> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public SsoUser(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : role) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    //账户是否未过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //指定用户是否解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否可用 ,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return true;
    }
}
