package com.bitc.securitytest.service;

import com.bitc.securitytest.dto.ClubAuthMemberDto;
import com.bitc.securitytest.dto.ClubMemberDto;
import com.bitc.securitytest.dto.ClubMemberRole;
import com.bitc.securitytest.mapper.ClubMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// UserDetailService 사용자 정보를 DB에서 가져올 경우 UserDetailService를 상속받아 loadUserByUsername() 메서드를 구현해야 함
@Service
public class ClubUserDetailsService implements UserDetailsService {
    @Autowired
    private ClubMemberMapper clubmemberMapper;


//    DB에서 사용자 정보를 가져올 경우 반드시 UserDetailService의 loadUserByUsername() 메서드를 구현해야 함
//    loadUserByUsername() 메서드는 UserDetails 인터페이스를 구현한 클래스 객체를 반환해야 함
//    UserDetails 인터페이스를 상속받아 구현한 클래스의 객체를 스프링 시큐리티에서 확인하여 인증된 사용자인지 아닌지 판단함
//    사용자가 로그인 페이지에서 로그인 시 스프링 시큐리티가 먼저 데이터를 받아서 loadUserByUsername() 메서드에 사용자 id를 매개변수로 사용해서 실행
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Mybatis의 mapper를 이용하여 사용자 정보를 가져옴
//        Optional : DB 사용 시 데이터 조회 후 데이터가 null이 들어올 경우 오류가 발생할 수 있는 부분을 안전하게 사용하기 위한 데이터타입
        Optional<ClubMemberDto> result = clubmemberMapper.findByEmail(username, false);

//        DB에 해당 사용자 정보가 있는지 확인
        if(result.isEmpty()) {
            throw new UsernameNotFoundException("이메일 및 비밀번호를 확인하세요");
        }

//        Optional 타입에 저장된 정보 가져옴
        ClubMemberDto member = result.get();
//        데이터베이스에 권한 등급 정보 컬럼이 있으면 필요없는 내용
//        사용자 정보에 가용 등급 권한 설정
        member.addMemberRole(ClubMemberRole.USER);

//        로그인 인증 정보를 가지고 있는 ClubAuthMemberDto 클래스 타입의 객체 생성
//        매개변수로 db에서 가져온 정보를 넘겨서 가사용자가 입력한 사용자 id를 가지고 로그인 인증된 객체가 생성됨
        ClubAuthMemberDto clubAuthMember = new ClubAuthMemberDto(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
//                스프링 시큐리티에서 사용하는 권한 정보는 모두 ROLE_권한 형태로 되어 있음
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        clubAuthMember.setName(member.getName());
        clubAuthMember.setFromSocial(member.isFromSocial());
//        로그인 인증 정보를 가지고 있는 객체를 반환 시 스프링 시큐리티가 처리해 줌
        return clubAuthMember;
    }
}
