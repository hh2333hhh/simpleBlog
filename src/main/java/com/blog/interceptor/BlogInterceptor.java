package com.blog.interceptor;

import com.blog.pojo.Blog;
import com.blog.pojo.Member;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class BlogInterceptor implements HandlerInterceptor {
    @Autowired
    BlogService blogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取今天的日期
        Date today = new Date();
        StringBuffer requestUrl = request.getRequestURL();
        String[] reArray = requestUrl.toString().split("/");
        String blogIdStr = reArray[reArray.length-1];
        int blogId = Integer.parseInt(blogIdStr);
        Blog blog = blogService.getBlog((long) blogId);
        int typeId = Math.toIntExact(blog.getTypeId());
        System.out.println(typeId);
        Member member = (Member) request.getSession().getAttribute("member");
        if (member == null){
            if (typeId != 1){
                response.sendRedirect("/member");
                return false;
            }else {
                return true;
            }
        }else {
            if(member.getRole().equals("普通会员") && typeId != 1){
                request.getSession().setAttribute("msg","您当前是普通会员，只能查看普通会员文章，请升级您的会员等级。");
                response.sendRedirect("/member/join");
                return false;
            }
            Date deadtime = member.getBuytime();
            deadtime.setYear(deadtime.getYear() + 1);
            if(new Date().after(deadtime)){
                request.getSession().setAttribute("msg","您的会员已过期。");
                response.sendRedirect("/member/join");
                return false;
            }
            if (member.getRole().equals("初级会员") && typeId == 3){
                request.getSession().setAttribute("msg","您当前是初级会员，还不能查看高级会员文章，请升级您的会员等级。");
                response.sendRedirect("/member/join");
                return false;
            }
            return true;
        }

    }
}
