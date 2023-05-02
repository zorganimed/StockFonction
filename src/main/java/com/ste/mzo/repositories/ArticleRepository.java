package com.ste.mzo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ste.mzo.entities.Article;

@RepositoryRestResource
@CrossOrigin("*")
public interface ArticleRepository extends CrudRepository<Article, Long> {

	@Query("select a from Article a where a.label like :x")
	List<Article> findArticleByLabel(@Param("x")String Label);
}
