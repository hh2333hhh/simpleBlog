package com.blog.controller.admin;

import com.blog.pojo.Media;
import com.blog.pojo.Type;
import com.blog.service.MediaService;
import com.blog.util.FileHandleUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class MediaController {

    @Autowired
    MediaService MediaService;

    @GetMapping("/media")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Media> allMedia = MediaService.getAllMedia();
        PageInfo<Media> pageInfo = new PageInfo<>(allMedia);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/media";
    }
    @GetMapping("/media/input")
    public String toAddMedia(){
        return "admin/media-input";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("description") String description,
                         RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("msg", "上传失败，请选择文件");
            return "admin/media";
        }
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(ext);
        String type = ext.equals(".mp4") ?"视频":"图片";
        String dir = ext.equals(".mp4") ?"video/":"image/";
        String newFileName = System.currentTimeMillis() + ext;
        try {
            String url = FileHandleUtil.upload(file.getInputStream(), dir, newFileName);
            attributes.addFlashAttribute("msg", "上传成功！url:"+url);
            Media Media = new Media(0,url,newFileName,type,fileName,description);
            MediaService.saveMedia(Media);
            return "redirect:/admin/media";
        } catch (Exception e) {
            e.printStackTrace();
        }
        attributes.addFlashAttribute("msg", "上传失败");
        return "admin/media";
    }

    @GetMapping("/media/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        FileHandleUtil.delete(MediaService.getType(id).getUrl());
        MediaService.deleteMedia(id);
        attributes.addFlashAttribute("msg", "删除成功" );
        return "redirect:/admin/media";
    }

}
