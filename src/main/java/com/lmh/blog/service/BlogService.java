package com.lmh.blog.service;

import com.lmh.blog.po.Blog;
import com.lmh.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by lvmen on 2019/11/19
 */
public interface BlogService {

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 查询博客并转成HTML格式
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    /**
     * 根据分页对象，查询条件查询博客
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     * 根据分页对象查询博客
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 根据搜索关键词,分页对象查询博客
     * @param query
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(String query, Pageable pageable);

    /**
     * 归档文章
     * @return
     */
    Map<String, List<Blog>> archiveBlog();

    /**
     * 文章条数
     * @return
     */
    Long countBlog();



    /**
     * tagId，分页对象查询博客
     * @param tagId
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    /**
     * 查询被推荐的size个更新时间最新的博客
     * @param size
     * @return
     */
    List<Blog> listRecommendBlogTop(Integer size);

    /**
     * 保存博客
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 更新博客
     * @param id
     * @param blog
     * @return
     */
    Blog updateBlog(Long id, Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(Long id);
}
