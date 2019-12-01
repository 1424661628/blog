package com.lmh.blog.dao;

import com.lmh.blog.po.Tag;
import com.lmh.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by lvmen on 2019/11/19
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findById(Long id);


    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
