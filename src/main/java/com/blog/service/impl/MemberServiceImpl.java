package com.blog.service.impl;

import com.blog.dao.MemberDao;
import com.blog.pojo.Member;
import com.blog.service.MemberService;
import com.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public boolean saveMember(Member member) {
        if (memberDao.saveMember(member)==1){
            return true;
        }else{return false;}

    }

    @Override
    public boolean login(String email, String psw) {
        Member item = memberDao.findMemberByEmail(email);
        String password = MD5Utils.code(psw);
        if(item == null){
            return false;
        }else if (!item.getPassword().equals(password)){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Member findMember(String email) {
        return memberDao.findMemberByEmail(email);
    }

    @Override
    public boolean updateMember(Member member) {
        if(memberDao.updateMember(member)==1) {
            return true;
        }else {return false;}
    }

    @Override
    public List<Member> getAllMember() {
            return memberDao.getAllMember();
    }
}
