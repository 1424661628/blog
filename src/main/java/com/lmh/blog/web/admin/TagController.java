package com.lmh.blog.web.admin;

import com.lmh.blog.po.Tag;
import com.lmh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by lvmen on 2019/11/19
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 返回标签列表页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    private String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 跳转到 新增标签页面
     * @return
     */
    @GetMapping("/tags/input")
    private String input(Model model){

        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 调转到 修改标签页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    private String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

         Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","该标签不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null){ // 保存失败
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    /**
     * 修改分类
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    private String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","该分类不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/Tags-input";
        }

        Tag t = tagService.updateTag(id,tag);
        if (t == null){ // 保存失败
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }


    /**
     * 删除标签
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
