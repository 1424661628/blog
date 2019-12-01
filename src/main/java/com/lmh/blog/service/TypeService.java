package com.lmh.blog.service;

import com.lmh.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lvmen on 2019/11/18
 */
public interface TypeService {

    /**
     * 保存类型
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id获得类型
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 根据name获得类型
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 根据分页对象获得类型
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 获得全部类型
     * @return
     */
    List<Type> listType();

    /**
     * 获得博客数最多的size个类型
     * @param size
     * @return
     */
    List<Type> listTypeTop(Integer size);

    /**
     * 根据id更新类型
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id, Type type);

    /**
     * 删除类型
     * @param id
     */
    void deleteType(Long id);

}
