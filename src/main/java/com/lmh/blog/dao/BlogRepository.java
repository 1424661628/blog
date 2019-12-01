package com.lmh.blog.dao;

import com.lmh.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lvmen on 2019/11/19
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 分页查询推荐的blog
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

    /**
     * 分页查询title或content中有query的blog
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    /**
     * 使blog的阅读量view + 1
     * @param id
     * @return
     */
    @Transactional
    @Modifying // JPQL不支持insert， update和delete时需要加@Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);


    /**
     * 返回年份year的集合
     * @return
     */
    @Query("select function('date_format',b.updateTime, '%Y') as year from Blog b group by function('date_format',b.updateTime, '%Y') order by year desc")
    List<String> findGroupByYear();


    /**
     * 查询指定年份的blog的集合
     * @param year
     * @return
     */
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
