package com.lmh.blog.web;

import com.lmh.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lvmen on 2019/11/20
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("archiveMap",blogService.archiveBlog());
        return "archives";
    }
}
