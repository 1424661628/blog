package com.lmh.blog.service;

import com.lmh.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lvmen on 2019/11/19
 */
public interface TagService {

    /**
     * 保存标签
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 根据id获得标签
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 根据名称获得标签
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 根据分页对象获得标签
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 获得标签列表
     * @return
     */
    List<Tag> listTag();

    /**
     * 获得指定ids的标签列表
     * @param ids
     * @return
     */
    List<Tag> listTag(String ids);

    /**
     * 获得博客数最多的size个标签
     * @param size
     * @return
     */
    List<Tag> listTagTop(Integer size);

    /**
     * 更新指定id的标签
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 删除标签
     * @param id
     */
    void deleteTag(Long id);


}
