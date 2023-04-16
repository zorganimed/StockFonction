package com.ste.mzo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ste.mzo.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

	@Query("select a from Article a where a.label like :x")
	List<Article> findArticleByLabel(@Param("x")String Label);
}
