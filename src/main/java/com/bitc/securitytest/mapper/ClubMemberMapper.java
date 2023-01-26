package com.bitc.securitytest.mapper;

import com.bitc.securitytest.dto.ClubMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface ClubMemberMapper {
    public Optional<ClubMemberDto> findByEmail (@Param("email") String email, @Param("social") boolean social);
}
