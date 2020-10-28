package com.blog.service;

import com.blog.pojo.Media;

import java.util.List;

public interface MediaService {
    int saveMedia(Media Media);

    Media getType(int id);



    List<Media> getAllMedia();


    int deleteMedia(int id);
}
