package com.woo.blog.keyword.infra;

import com.woo.blog.keyword.domain.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, String> {

    List<Keyword> findTop10ByOrderBySearchCountDesc();
}
