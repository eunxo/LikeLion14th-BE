package com.project.likelion14thbe.global.security.userdetails;

import com.project.likelion14thbe.domain.member.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Role role;

    public CustomUserDetails(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // 해당 User 의 권한을 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String roleName = role.name();

        // ⭐️ 만약 DB에서 "USER"로 넘어왔다면 앞에 "ROLE_"를 강제로 붙여주는 안전장치!
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        authorities.add(new SimpleGrantedAuthority(roleName));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Account 가 만료되었는지?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Account 가 잠겨있는지?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credential 만료되지 않았는지?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 활성화가 되어있는지?
    @Override
    public boolean isEnabled() {
        // User Entity 에서 Status 가져온 후 true? false? 검사
        return true;
    }
}
