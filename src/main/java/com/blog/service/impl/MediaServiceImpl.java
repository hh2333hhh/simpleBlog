package com.blog.service.impl;

import com.blog.dao.MediaDao;
import com.blog.pojo.Media;
import com.blog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaDao MediaDao;

    @Override
    public int saveMedia(Media Media) { return MediaDao.saveMedia(Media);}

    @Override
    public Media getType(int id) {
        return MediaDao.getMedia(id);
    }

    @Override
    public List<Media> getAllMedia() {
        return MediaDao.getAllMedia();
    }

    @Override
    public int deleteMedia(int id) {
        return MediaDao.deleteMedia(id);
    }


}
