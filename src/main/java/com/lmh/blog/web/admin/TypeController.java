package com.lmh.blog.web.admin;

import com.lmh.blog.po.Type;
import com.lmh.blog.service.TypeService;
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
 * Created by lvmen on 2019/11/18
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 返回分类列表页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    private String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        if (pageable == null){
            System.out.println(".......");
        }

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 跳转到 新增分类页面
     * @return
     */
    @GetMapping("/types/input")
    private String input(Model model){

        model.addAttribute("type",new Type());
        return "admin/types-input";
    }


    /**
     * 调转到 修改分类页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    /**
     * 新增分类
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    private String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","该分类不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);
        if (t == null){ // 保存失败
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改分类
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    private String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","该分类不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.updateType(id,type);
        if (t == null){ // 保存失败
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
