<template>
  <div class="article-list">
    <h2>Liste des Articles</h2>

    <!-- Champ de recherche -->
    <input type="text" v-model="searchQuery" placeholder="Rechercher" @input="filterArticles" class="form-control mb-3">

    <!-- Tableau des articles -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Description</th>
            <th>Catégorie</th>
            <th>Prix</th>
            <th>Quantité en Stock</th>
            <th>Supprimé</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des articles filtrés -->
          <tr v-for="article in filteredArticles" :key="article.id">
            <td>
              <template v-if="!article.editing">{{ article.nom }}</template>
              <input v-model="article.nom" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.description }}</template>
              <input v-model="article.description" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.categorie }}</template>
              <input v-model="article.categorie" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.prix }}</template>
              <input v-model="article.prix" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.quantite }}</template>
              <input v-model="article.quantite" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.supprime }}</template>
              <input v-model="article.supprime" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">
                <button @click="editArticle(article)" class="btn btn-sm btn-primary">Modifier</button>
                <button @click="confirmDelete(article)" class="btn btn-sm btn-danger">Supprimer</button>
                <span v-if="article.confirmDelete">
                  Confirmer ? 
                  <button @click="deleteArticle(article)" class="btn btn-sm btn-danger">Oui</button> 
                  <button @click="cancelDelete(article)" class="btn btn-sm btn-secondary">Non</button>
                </span>
              </template>
              <template v-else>
                <button @click="saveArticle(article)" class="btn btn-sm btn-success">Enregistrer</button>
                <button @click="cancelEdit(article)" class="btn btn-sm btn-secondary">Annuler</button>
              </template>
            </td>
          </tr>
          <!-- Si aucun résultat ne correspond à la recherche -->
          <tr v-if="filteredArticles.length === 0 && searchQuery !== ''">
            <td colspan="7" style="text-align: center;">Aucun résultat trouvé.</td>
          </tr>
          <!-- Afficher un message si tous les articles sont visibles -->
          <tr v-if="!searchQuery && articles.length > 0 && filteredArticles.length === articles.length">
            <td colspan="7" style="text-align: center;">Affichage de tous les articles.</td>
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
      articles: [],
      searchQuery: '',
      filteredArticles: []
    };
  },
  methods: {
    async fetchArticles() {
      try {
        const response = await ArticleService.getAll();
        this.articles = response.data.map(article => ({
          ...article,
          editing: false,
          confirmDelete: false
        }));
        this.filteredArticles = [...this.articles];
      } catch (error) {
        console.error('Erreur lors de la récupération des articles :', error);
      }
    },
    async deleteArticle(article) {
      const articleId = article.id;
      try {
        await ArticleService.delete(articleId);
        this.articles = this.articles.filter(a => a.id !== articleId);
        this.filteredArticles = this.filteredArticles.filter(a => a.id !== articleId);
      } catch (error) {
        console.error('Erreur lors de la suppression de l\'article :', error);
      }
    },
    confirmDelete(article) {
      article.confirmDelete = true;
    },
    cancelDelete(article) {
      article.confirmDelete = false;
    },
    editArticle(article) {
      article.editing = true;
    },
    async saveArticle(article) {
      try {
        await ArticleService.update(article.id, article);
        article.editing = false;
        const index = this.articles.findIndex(a => a.id === article.id);
        if (index !== -1) {
          this.articles[index] = { ...article };
          this.filteredArticles = [...this.articles];
        }
      } catch (error) {
        console.error('Erreur lors de la sauvegarde des modifications de l\'article :', error);
      }
    },
    cancelEdit(article) {
      article.editing = false;
    },
    filterArticles() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredArticles = [...this.articles];
      } else {
        this.filteredArticles = this.articles.filter(article =>
          article.nom.toLowerCase().includes(query) ||
          article.description.toLowerCase().includes(query) ||
          article.categorie.toLowerCase().includes(query) ||
          article.prix.toString().includes(query) ||
          article.quantite.toString().includes(query) ||
          article.supprime.toLowerCase().includes(query)
        );
      }
    }
  },
  created() {
    this.fetchArticles();
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

.btn {
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

.btn-primary {
  background-color: rgb(0, 123, 255);
  color: #fff;
}

.btn-primary:hover {
  background-color: rgb(23, 101, 237);
}

.btn-danger {
  background-color: rgb(220, 53, 69);
  color: #fff;
}

.btn-danger:hover {
  background-color: rgb(185, 28, 49);
}

.btn-success {
  background-color: rgb(40, 167, 69);
  color: #fff;
}

.btn-success:hover {
  background-color: rgb(30, 140, 60);
}

.btn-secondary {
  background-color: rgb(108, 117, 125);
  color: #fff;
}

.btn-secondary:hover {
  background-color: rgb(80, 89, 97);
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
