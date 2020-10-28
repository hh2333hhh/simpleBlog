package com.blog.service;


import java.util.Map;

public interface MailService {
    boolean sendMimeMessge(String to, String subject, String content) ;
    void sendMimeMessge(String to, String subject, String content, Map<String, String> rscIdMap) ;
}
