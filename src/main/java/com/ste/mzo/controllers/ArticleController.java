package com.ste.mzo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ste.mzo.entities.Article;
import com.ste.mzo.entities.Provider;
import com.ste.mzo.repositories.ArticleRepository;
import com.ste.mzo.repositories.ProviderRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	private final ProviderRepository providerRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleController(ProviderRepository providerRepository, ArticleRepository articleRepository) {
		super();
		this.providerRepository = providerRepository;
		this.articleRepository = articleRepository;
	}

	@GetMapping("/list")
	public String listArticles(Model model) {

		List<Article> ls = (List<Article>) articleRepository.findAll();
		if (ls.isEmpty())
			ls = null;
		model.addAttribute("articles", ls);
		return "article/listArticles";
	}

	@GetMapping("/add")
	public String showAddArticleForm(Model model) {
		Article article = new Article();

		model.addAttribute("article", article);
		model.addAttribute("providers", providerRepository.findAll());

		return "article/addArticle";
	}

	@PostMapping("/add")
	public String addArticle(Model model, @Valid Article article, BindingResult bindingResult,
			@RequestParam(name = "providerId", required = false) Long idProvider,
			@RequestParam("files") MultipartFile[] files) {
		Provider provider = providerRepository.findById(idProvider)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider id" + idProvider));
		if (bindingResult.hasErrors()) {
			model.addAttribute("providers", providerRepository.findAll());
			return "article/addArticle";
		}
		article.setProvider(provider);
		/// part upload

		/*
		 * StringBuilder fileName = new StringBuilder(); MultipartFile file = files[0];
		 * Path fileNameAndPath = Paths.get(uploadDirectory,
		 * file.getOriginalFilename());
		 * 
		 * fileName.append(file.getOriginalFilename()); try {
		 * Files.write(fileNameAndPath, file.getBytes()); } catch (IOException e) {
		 * e.printStackTrace(); } article.setPicture(fileName.toString());
		 */
		/// part upload
		articleRepository.save(article);

		return "redirect:list";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateArticleForm(Model model, @PathVariable("id") Long idArticle) {
		Article article = articleRepository.findById(idArticle)
				.orElseThrow(() -> new IllegalArgumentException("Invalid article id" + idArticle));
		model.addAttribute("article", article);
		model.addAttribute("providers", providerRepository.findAll());
		model.addAttribute("idProvider", article.getProvider().getId());

		return "article/updateArticle";
	}

	@PostMapping("/update")
	public String updateArticle(Model model, @Valid Article article, BindingResult bindingResult,
			@RequestParam(name = "providerId", required = false) Long idProvider,
			@RequestParam("files") MultipartFile[] files) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("providers", providerRepository.findAll());
			return "article/updateArticle";
		}

		Provider provider = providerRepository.findById(idProvider)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider id " + idProvider));
		article.setProvider(provider);
		/// part upload

		/*
		 * StringBuilder fileName = new StringBuilder(); MultipartFile file = files[0];
		 * Path fileNameAndPath = Paths.get(uploadDirectory,
		 * file.getOriginalFilename());
		 * 
		 * fileName.append(file.getOriginalFilename()); try {
		 * Files.write(fileNameAndPath, file.getBytes()); } catch (IOException e) {
		 * e.printStackTrace(); } article.setPicture(fileName.toString());
		 */
		/// part upload
		articleRepository.save(article);
		return "redirect:list";
	}

	@GetMapping("/delete/{id}")
	public String deleteArticle(@PathVariable(name = "id") Long idArticle) {
		Article article = articleRepository.findById(idArticle)
				.orElseThrow(() -> new IllegalArgumentException("Invalid article id " + idArticle));
		articleRepository.delete(article);
		return "redirect:../list";
	}

	@GetMapping("show/{id}")
	public String showArticleDetails(@PathVariable("id") Long id, Model model) {
		Article article = articleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

		model.addAttribute("article", article);
         System.out.println("show details method");
		return "article/showArticle";
	}
	
	@PostMapping("/searchLabel")
	public String findArticlesByLabel(@RequestParam("label")String label, Model model) {

		List<Article> ls = (List<Article>) articleRepository.findArticleByLabel("%"+label+"%");
		if (ls.isEmpty())
			ls = null;
		model.addAttribute("label", label);
		model.addAttribute("articles", ls);
		return "article/listArticles";
	}

}
