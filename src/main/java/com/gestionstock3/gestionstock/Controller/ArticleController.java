package com.gestionstock3.gestionstock.Controller;

import com.gestionstock3.gestionstock.Modele.Article;
import com.gestionstock3.gestionstock.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
//@CrossOrigin(origins = "http://localhost:8080")

public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/read")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Article createArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            Article existingArticle = article.get();
            existingArticle.setArticleNom(articleDetails.getArticleNom());
            existingArticle.setArticleDescription(articleDetails.getArticleDescription());
            existingArticle.setArticlePrix(articleDetails.getArticlePrix());
            existingArticle.setArticleQuantite(articleDetails.getArticleQuantite());
            existingArticle.setCategorie(articleDetails.getCategorie());
            Article updatedArticle = articleService.saveArticle(existingArticle);
            return ResponseEntity.ok(updatedArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
