package com.blog.controller.admin;

import com.blog.pojo.Member;
import com.blog.pojo.User;
import com.blog.service.MemberService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/utils")
    public String userPage() {return "admin/users";}


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    //提高会员等级
    @PostMapping("/updateGrade")
    public String updateGrade(@RequestParam String email,
                              @RequestParam String grade,
                              RedirectAttributes attributes){
        Member member = memberService.findMember(email);
        if (member==null){
            attributes.addFlashAttribute("msg", "会员不存在");
            return "redirect:/admin/utils";
        }
        System.out.println(grade);
        if (grade.equals("1")){
            member.setRole("初级会员");
            member.setBuytime(new Date());
        }else if(grade.equals("0")){
            member.setRole("高级会员");
            member.setBuytime(new Date());
        }
        memberService.updateMember(member);
        //新建一个带提示成功的消息模块"ok"
        attributes.addFlashAttribute("ok", "修改成功");
        return "redirect:/admin/utils";
    }


}
