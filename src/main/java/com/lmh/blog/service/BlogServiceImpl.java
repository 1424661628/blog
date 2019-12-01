package com.lmh.blog.service;

import com.lmh.blog.dao.BlogRepository;
import com.lmh.blog.exception.NotFoundException;
import com.lmh.blog.po.Blog;
import com.lmh.blog.util.MarkdownUtils;
import com.lmh.blog.util.MyBeanUtils;
import com.lmh.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by lvmen on 2019/11/19
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;


    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Blog getAndConvert(Long id) {

        Blog blog = blogRepository.findById(id).get();
        if (blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        String s = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
        BeanUtils.copyProperties(blog,b);
        b.setContent(s); //改动blog时 hibernate Session 会操作数据库

        // 更新views
        blogRepository.updateViews(id);

        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!= null){
                    predicates.add(cb.equal(root.get("type").get("id"),blog.getTypeId()));
                }
                if (blog.getRecommend() != null){
                    predicates.add(cb.equal(root.get("recommend"),blog.getRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        List<String> years= blogRepository.findGroupByYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String  year : years){
            map.put(year,blogRepository.findByYear(year));
        }

        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) { // TODO 非空校验

        if (blog.getId() == null){ // 第一次保存
            blog.setCreateTime(new Date());
            blog.setUpdateTime(blog.getCreateTime());
            blog.setViews(0);
            if(blog.getAppreciation() == null){
                blog.setAppreciation(false);
            }
            if (blog.getRecommend() == null){
                blog.setRecommend(false);
            }
            if (blog.getCommentabled() == null){
                blog.setCommentabled(false);
            }
            if (blog.getShareStatement() == null){
                blog.setShareStatement(false);
            }

        }else {
            blog.setUpdateTime(new Date()); // 修改
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if (b == null){
            throw new NotFoundException("该博客不存在");
        }
        b.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
