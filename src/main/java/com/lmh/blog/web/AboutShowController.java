package com.lmh.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lvmen on 2019/11/20
 */
@Controller
public class AboutShowController {


    @GetMapping("/about")
    public String about(){

        return "about";
    }

}
