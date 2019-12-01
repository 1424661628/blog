package com.lmh.blog.web.admin;

import com.lmh.blog.po.Blog;
import com.lmh.blog.po.User;
import com.lmh.blog.service.BlogService;
import com.lmh.blog.service.TagService;
import com.lmh.blog.service.TypeService;
import com.lmh.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * Created by lvmen on 2019/11/18
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 返回博客页面
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog,
                        Model model){

        model.addAttribute("types", typeService.listType()); // 分类的初始化
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return LIST;
    }

    /**
     * 返回博客 片段
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
              BlogQuery blog,
              Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList"; // 返回到指定页面上的一个片段
    }

    public void setTagAndType(Model model){
        model.addAttribute("types", typeService.listType()); // 分类的初始化
        model.addAttribute("tags", tagService.listTag()); // 标签的初始化
    }


    /**
     * 返回博客新增页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){

        setTagAndType(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    /**
     * 返回博客修改页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model, @PathVariable Long id){
        Blog blog = blogService.getBlog(id);
        blog.init();
        setTagAndType(model);
        model.addAttribute("blog", blog);
        return INPUT;
    }


    /**
     * 博客提交
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){ // user is in the session

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog); // TODO 非空校验

        if (b == null){ // 保存失败
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){

        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }




}
