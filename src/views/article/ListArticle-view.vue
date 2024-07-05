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
            <th>Image</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Catégorie</th>
            <th>Prix</th>
            <th>Quantité en Stock</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des articles filtrés -->
          <tr v-for="article in filteredArticles" :key="article.id">
            <td>
              <img :src="article.imageUrl" alt="Image de l'article" style="max-width: 100px; max-height: 100px;">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.articleNom }}</template>
              <input v-model="article.articleNom" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.articleDescription }}</template>
              <textarea v-model="article.articleDescription" v-else class="form-control"></textarea>
            </td>
            <td>
              <template v-if="!article.editing">{{ article.categorie }}</template>
              <input v-model="article.categorie" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.articlePrix }}</template>
              <input type="number" v-model="article.articlePrix" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">{{ article.articleQuantite }}</template>
              <input type="number" v-model="article.articleQuantite" v-else class="form-control">
            </td>
            <td>
              <template v-if="!article.editing">
                <button @click="editArticle(article)" class="btn btn-sm btn-primary">Modifier</button>
                <button @click="confirmDelete(article)" class="btn btn-sm btn-danger">Supprimer</button>
                <span v-if="article.confirmDelete">
                  Confirmer ? 
                  <button @click="deleteArticle(article.id)" class="btn btn-sm btn-danger">Oui</button> 
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
      filteredArticles: [],
      newArticle: {
        articleNom: '',
        articleDescription: '',
        categorie: '',
        articlePrix: '',
        articleQuantite: '',
        imageUrl: ''
      },
      currentArticle: null
    };
  },
  created() {
    this.fetchArticles();
  },
  methods: {
    fetchArticles() {
      ArticleService.getAll()
        .then(response => {
          this.articles = response.data;
          this.filteredArticles = [...this.articles];
        })
        .catch(error => {
          console.error('Erreur lors de la récupération des articles:', error);
        });
    },
    filterArticles() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredArticles = [...this.articles];
      } else {
        this.filteredArticles = this.articles.filter(article =>
          article.articleNom.toLowerCase().includes(query) ||
          article.articleDescription.toLowerCase().includes(query) ||
          article.categorie.toLowerCase().includes(query) ||
          article.articlePrix.toString().includes(query) ||
          article.articleQuantite.toString().includes(query)
        );
      }
    },
    editArticle(article) {
      article.editing = true;
      this.currentArticle = { ...article };
    },
    cancelEdit(article) {
      article.editing = false;
    },
    saveArticle(article) {
      if (this.currentArticle) {
        ArticleService.update(article.id, this.currentArticle)
          .then(() => {
            this.fetchArticles();
            this.currentArticle = null;
          })
          .catch(error => {
            console.error('Erreur lors de la mise à jour de l\'article:', error);
          });
      } else {
        ArticleService.create(this.newArticle)
          .then(() => {
            this.fetchArticles();
            this.newArticle = {
              articleNom: '',
              articleDescription: '',
              categorie: '',
              articlePrix: '',
              articleQuantite: '',
              imageUrl: ''
            };
          })
          .catch(error => {
            console.error('Erreur lors de la création de l\'article:', error);
          });
      }
      article.editing = false;
    },
    confirmDelete(article) {
      article.confirmDelete = true;
    },
    cancelDelete(article) {
      article.confirmDelete = false;
    },
    deleteArticle(id) {
      ArticleService.delete(id)
        .then(() => {
          this.fetchArticles();
        })
        .catch(error => {
          console.error('Erreur lors de la suppression de l\'article:', error);
        });
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
