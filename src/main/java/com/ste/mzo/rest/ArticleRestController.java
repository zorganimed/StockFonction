package com.ste.mzo.rest;


import java.io.File;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ste.mzo.entities.Article;
import com.ste.mzo.repositories.ArticleRepository;
import com.ste.mzo.repositories.ProviderRepository;


 
@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/articles"})
public class ArticleRestController {
	private final ProviderRepository providerRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleRestController(ProviderRepository providerRepository, ArticleRepository articleRepository) {
		super();
		this.providerRepository = providerRepository;
		this.articleRepository = articleRepository;
	}
	
	@GetMapping("/list")
	public List<Article> list() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Article> la = (List<Article>) articleRepository.findAll();
		return (List<Article>) articleRepository.findAll();
	}
	
	@PostMapping("/add/{providerId}")
	Article createArticle(@PathVariable Long providerId,@Valid @RequestBody Article article) {

		return providerRepository.findById(providerId).map(
				provider->{
					article.setProvider(provider);
					return articleRepository.save(article);
				}
				).orElseThrow(()-> new ResourceNotFoundException("ProviderId "+providerId+" not found "));
	}
	
	@PutMapping("update/{providerId}/{articleId}")
	public Article updateArticle(@PathVariable(value = "providerId") Long providerId
			,@PathVariable(value = "articleId") Long articleId
			,@Valid @RequestBody Article articleRequest) {
		if(!providerRepository.existsById(providerId)) {
			throw new IllegalArgumentException("Provider Id++ "+providerId+" not found");
		}
	 
		return articleRepository.findById(articleId).map(
				article->{
					article.setLabel(articleRequest.getLabel());
					article.setProvider(providerRepository.findById(providerId)
							.orElseThrow(() -> new IllegalArgumentException("Invalid provider id " + providerId)));
					article.setPrice(articleRequest.getPrice());
					article.setPicture(articleRequest.getPicture());
					return articleRepository.save(article);
				}
				).orElseThrow(()-> new ResourceNotFoundException("ArticleId "+articleId+" not found "));
	}
	
	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteProvider(@PathVariable Long articleId)
	{
		return articleRepository.findById(articleId).map(
				article->{
					articleRepository.delete(article);
					return ResponseEntity.ok().build();
				}
				).orElseThrow(()-> new ResourceNotFoundException("ProviderId "+articleId+" not found "));
	}
	
	@GetMapping("/{articleId}")
	public Optional<Article> getProvider(@PathVariable Long articleId) {
		return articleRepository.findById(articleId);

	}
}
