package com.blog.config;

import com.blog.interceptor.BlogInterceptor;
import com.blog.interceptor.LoginInterceptor;
import com.blog.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getBlogInterceptor(){
        return new BlogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {   //配置拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
        registry.addInterceptor(new MemberInterceptor())
                .addPathPatterns("/member/**")
                .excludePathPatterns("/member")
                .excludePathPatterns("/member/addMember")
                .excludePathPatterns("/member/login")
                .excludePathPatterns("/member/logon")
                .excludePathPatterns("/member/sentMail");
        registry.addInterceptor(getBlogInterceptor())
                .addPathPatterns("/blog/**");
    }


}
