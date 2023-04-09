package com.ste.mzo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ste.mzo.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
