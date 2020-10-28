package com.blog.dao;

import com.blog.pojo.Member;
import com.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberDao {
    Member findMemberByEmail(String email);

    int saveMember(Member member);

    int updateMember(Member member);

    List<Member> getAllMember();

}
