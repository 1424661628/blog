package com.lmh.blog.web;

import com.lmh.blog.po.Comment;
import com.lmh.blog.po.User;
import com.lmh.blog.service.BlogService;
import com.lmh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by lvmen on 2019/11/20
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;


    /**
     * 将回复数据列出来
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments",commentService.listCommentByBlogIdAndParentCommentNot(blogId));

        return "blog :: commentList";
    }


    /**
     * 处理消息回复
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }

        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
