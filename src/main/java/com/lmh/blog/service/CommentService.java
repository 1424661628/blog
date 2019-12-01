package com.lmh.blog.service;

import com.lmh.blog.po.Comment;

import java.util.List;

/**
 * Created by lvmen on 2019/11/21
 */
public interface CommentService {

    List<Comment> listCommentByBlogIdAndParentCommentNot(Long blogId);

    Comment saveComment(Comment comment);
}
