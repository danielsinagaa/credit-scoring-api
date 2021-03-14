//package com.enigma.creditscoringapi.security.service;
//
//import com.enigma.creditscoringapi.entity.Role;
//import com.enigma.creditscoringapi.entity.Users;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserDetailsImpl implements UserDetails {
//
//    private String id;
//
//    private String username;
//
//    private String email;
//
//    private LocalDate dateRegister;
//
//    @JsonIgnore
//    private String password;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsImpl(String id, String username, String email, LocalDate dateRegister, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.dateRegister = dateRegister;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static UserDetailsImpl build(Users user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//                .collect(Collectors.toList());
//
//        return new UserDetailsImpl(
//                user.getId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getDateRegister(),
//                user.getPassword(),
//                authorities);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
