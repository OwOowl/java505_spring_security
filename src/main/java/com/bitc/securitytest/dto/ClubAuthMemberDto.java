package com.bitc.securitytest.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//User 클래스를 상속받아 인증과 관련된 정보를 저장하는 Dto
// User 클래스 : 스프링 시큐리티에서 관리하는 사용자 정보 클래스
@Getter
@Setter
@ToString
public class ClubAuthMemberDto extends User {

//    user 클래스에서 제공하는 username, password, authorities 사용해야함
//    username : 스프링 시큐리티에서 사용하는 사용자 ID
//    password : 스프링 시큐리티에서 사용하는 사용자 Pw
//    authorities : 스프링 시큐리티에서 사용하는 사용자 권한
    private String email; // 사용자 id로 사용되는 변수
    private String name; // 사용자 이름
    private boolean fromSocial;
    public ClubAuthMemberDto(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> auth) {
//        객체 생성 시 가져온 매개변수로 받은 사용자 id와 비밀번호, 인증권한을 부모 클래스의  생성자를 실행하여 데이터를 저장함
        super(username, password, auth);
//        User 클래스에서 사용자 id는 username 변수이므로 해당 변수를 사용자 id 역할을 하는 email 변수에 저장
        this.email = username;
        this.fromSocial = fromSocial;
    }
}
