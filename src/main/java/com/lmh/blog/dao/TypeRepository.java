package com.lmh.blog.dao;

import com.lmh.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by lvmen on 2019/11/18
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findById(Long id);

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable); // 分页中也有大小

}
