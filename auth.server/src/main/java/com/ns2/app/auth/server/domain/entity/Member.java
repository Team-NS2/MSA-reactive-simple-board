package com.ns2.app.auth.server.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("member")
public class Member implements UserDetails {

    @Id
    private long id;

    private String username;

    @JsonIgnore
    private String password;

    private String name;

    private Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public static Member createMember(String username, String password, String name) {
        return new Member(username, password, name);
    }

    public static Member createMember(long id, String username, String password, String name) {
        return new Member(id, username, password, name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}
