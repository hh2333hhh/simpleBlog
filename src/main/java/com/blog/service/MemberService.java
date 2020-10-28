package com.blog.service;

import com.blog.pojo.Member;

import java.util.List;

public interface MemberService {
    //会员注册
    boolean saveMember(Member member);

    //会员登陆
    boolean login(String email,String psw);


    //查询会员
    Member findMember(String email);


    //会员修改
    boolean updateMember(Member member);

    //查询所有会员
    List<Member> getAllMember();
}
