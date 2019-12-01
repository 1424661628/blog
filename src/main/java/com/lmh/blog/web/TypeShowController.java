package com.lmh.blog.web;

import com.lmh.blog.po.Type;
import com.lmh.blog.service.BlogService;
import com.lmh.blog.service.TypeService;
import com.lmh.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by lvmen on 2019/11/20
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    /**
     * 返回分类页面
     * @param id
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                         @PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){

        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1){
            id = types.get(0).getId();
        }
        BlogQuery query = new BlogQuery();
        query.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page", blogService.listBlog(pageable,query));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
