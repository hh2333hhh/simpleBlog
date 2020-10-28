package com.blog.dao;

import com.blog.pojo.Media;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MediaDao {
    int saveMedia(Media meida);

    Media getMedia(int id);

    List<Media> getAllMedia();

    int deleteMedia(int id);
}
