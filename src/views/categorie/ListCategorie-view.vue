<template>
  <div class="categorie-list">
    <h2>Liste des Catégories d'Articles</h2>

    <!-- Tableau des catégories -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des catégories -->
          <tr v-for="categorie in categories" :key="categorie.categorieId">
            <td>{{ categorie.categorieNom }}</td>
            <td>{{ categorie.categorieDescription }}</td>
          </tr>
          <!-- Si aucune catégorie n'est trouvée -->
          <tr v-if="categories.length === 0">
            <td colspan="2" style="text-align: center;">Aucune catégorie trouvée.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import CategorieService from '@/services/CategorieService';

export default {
  name: 'ListCategorie',
  data() {
    return {
      categories: []
    };
  },
  created() {
    this.fetchCategories();
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await CategorieService.getAll();
        this.categories = response.data.map(categorie => ({
          ...categorie
        }));
      } catch (error) {
        console.error('Erreur lors de la récupération des catégories :', error);
        // Gérer l'erreur ici (ex: affichage d'un message d'erreur à l'utilisateur)
      }
    }
  }
};
</script>

<style scoped>
.categorie-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 15px;
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

.no-data {
  text-align: center;
  font-style: italic;
  color: #888;
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
