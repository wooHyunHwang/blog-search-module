package com.woo.blog.keyword.infra.repository;

import com.woo.blog.keyword.domain.Keyword;
import com.woo.blog.keyword.domain.KeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, KeywordId> {

    List<Keyword> findTop10ByOrderBySearchCountDesc();
}
