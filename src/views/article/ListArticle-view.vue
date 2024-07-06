<template>
  <div class="article-list">
    <h2>Liste des Articles</h2>

    <!-- Tableau des articles -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Image</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Catégorie</th>
            <th>Prix</th>
            <th>Quantité en Stock</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des articles -->
          <tr v-for="article in articles" :key="article.articleId">
            <td>
              <img :src="article.imageUrl || require('@/assets/placeholder.jpg')" alt="Image de l'article"
                style="max-width: 100px; max-height: 100px;">
            </td>
            <td>{{ article.articleNom }}</td>
            <td>{{ article.articleDescription }}</td>
            <td>{{ article.categorie.categorieNom }}</td>
            <td>{{ article.articlePrix }}</td>
            <td>{{ article.articleQuantite }}</td>
          </tr>
          <!-- Si aucun résultat ne correspond à la recherche -->
          <tr v-if="articles.length === 0">
            <td colspan="6" style="text-align: center;">Aucun article trouvé.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import ArticleService from '@/services/ArticleService';

export default {
  name: 'ListArticle',
  data() {
    return {
      articles: []
    };
  },
  created() {
    this.fetchArticles();
  },
  methods: {
    async fetchArticles() {
      try {
        const response = await ArticleService.getAll();
        this.articles = response.data.map(article => ({
          ...article,
          imageUrl: article.imageUrl || '' // Assurez-vous que imageUrl est défini ou utilisez une valeur par défaut
        }));
      } catch (error) {
        console.error('Erreur lors de la récupération des articles :', error);
        // Gérer l'erreur ici (ex: affichage d'un message d'erreur à l'utilisateur)
      }
    }
  }
};
</script>

<style scoped>
.article-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(118, 122, 122, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: rgb(60, 67, 67);
}

h2 {
  color: #444;
}

.table-responsive {
  margin-top: 20px;
}

.table th {
  background-color: #b6c7d9;
  color: #fff;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 0, 0, 0.5);
}

.table td {
  color: rgb(15, 16, 16);
}

.form-control {
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 15px;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control:focus {
  color: #495057;
  background-color: #fff;
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
</style>
