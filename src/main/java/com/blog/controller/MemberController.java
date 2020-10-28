package com.blog.controller;


import com.blog.pojo.Member;
import com.blog.service.MailService;
import com.blog.service.MemberService;

import com.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    @GetMapping()
    public String loginPage(HttpServletRequest request,
                            HttpSession session){
        String referer = request.getHeader("referer");
        StringBuffer requestUrl = request.getRequestURL();
        String myUrl = requestUrl.toString().replace("/member","");
        if (referer == null){
            session.setAttribute("referer", "/");
            return "login";
        }
        String Referer = referer.replace(myUrl,"");
        if (!Referer.equals("/member")) {
            session.setAttribute("referer", Referer);
        }
        return "login";


    }
    @GetMapping("/addMember")
    public String logonPage(){
        return "logon"; }
    @GetMapping("/join")
    public String joinPage(){
        return "join"; }
    @GetMapping("/updateMember")
    public String updatePage(Model model,HttpSession session){
        Member member = (Member) session.getAttribute("member");
        if (member.getBuytime()!=null){
            Date deadtime = member.getBuytime();
            deadtime.setYear(deadtime.getYear() + 1);
            if (deadtime.after(new Date())){
                String day =  String.valueOf((deadtime.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
                model.addAttribute("day",day+"天过期");
            }else {
                model.addAttribute("day","已过期");
            }
        }
        model.addAttribute("member",member);
        return "update";
    }
    //登录业务
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes
                       ){
        if(memberService.login(username, password)){
            Member member = memberService.findMember(username);
            session.setAttribute("member", member);
            String referer = (String) session.getAttribute("referer");
            int n = (int)(Math.random()*9+1);
            String avatar = "/images/h"+ n +".jpg";
            session.setAttribute("avatar", avatar);
            return "redirect:"+referer;
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/member";
        }

    }
    //登出业务
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("member");
        return "redirect:/member";
    }
    //注册业务
    @PostMapping("/logon")

    public String logon(@RequestParam String nickname,
                        @RequestParam String phone,
                        @RequestParam String email,
                        @RequestParam String code,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        Member member = memberService.findMember(email);
        String vercode = (String) session.getAttribute("code");
        if (member != null){
            attributes.addFlashAttribute("msg", "账号已注册");
            return "redirect:/member/addMember";
        }else if(!vercode.equals(code)){
            attributes.addFlashAttribute("msg", "验证码错误");
            return "redirect:/member/addMember";
        }else{
            password = MD5Utils.code(password);
            Member newmember = new Member(0,nickname,phone,email,password,new Date(),"普通会员",null);
            memberService.saveMember(newmember);
            session.setAttribute("referer", "/");
            return "login";
        }
    }
    //发送邮件
    @RequestMapping("/sentMail")
    @ResponseBody
    public String  getCode(String email, HttpSession session) {
        String THEME = "这是一封验证邮件";
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String CONTENT = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>"
                +"<p style='font-size:20px;font-weight:blod;'>尊敬的"+ email +",您好</p>"
                +"<p style='text-indent:2em;font-size:20px'>您本次的验证码："
                +"<span style='font-size:30px; font-weight:blod; color:#44cef6;'>"+ verifyCode +"</span>"
                +",请尽快使用!</p>"
                +"<span style='font-size:18px; float:right; margin-right:60px;'>"+ sdf.format(new Date()) +"</span></body></html>";

        session.setAttribute("code",verifyCode);
        mailService.sendMimeMessge(email, THEME, CONTENT);
        return "success";
    }

    //修改个人信息
    @PostMapping("/update")
    public String update(@RequestParam String name,
                         @RequestParam String phone,
                         HttpSession session,
                         RedirectAttributes attributes){
        Member member = (Member) session.getAttribute("member");
        member.setName(name);
        member.setPhone(phone);
        memberService.updateMember(member);
        //新建一个带提示成功的消息模块"ok"
        attributes.addFlashAttribute("ok", "修改成功");
        return "redirect:/member/updateMember";
    }

    @PostMapping("/changPsw")
    public String changePsw(@RequestParam String password,
                            @RequestParam String newpsw,
                            HttpSession session,
                            RedirectAttributes attributes){
        Member member = (Member) session.getAttribute("member");
        password = MD5Utils.code(password);

        if(member.getPassword().equals(password)){
            newpsw =MD5Utils.code(newpsw);
            member.setPassword(newpsw);
            memberService.updateMember(member);
            session.removeAttribute("member");
            session.setAttribute("member",member);
            attributes.addFlashAttribute("ok", "修改成功");
            return "redirect:/member/updateMember";
        }else{
            attributes.addFlashAttribute("msg", "原密码有误");
            return "redirect:/member/updateMember";
        }
    }



}
