package com.lmh.blog.dao;

import com.lmh.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lvmen on 2019/11/21
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);


}
