<template>
  <div class="categorie-list">
    <h2>Liste des Catégories d'Articles</h2>

    <!-- Champ de recherche -->
    <input type="text" v-model="searchQuery" placeholder="Rechercher une catégorie" @input="filterCategories" class="form-control mb-3">

    <!-- Tableau des catégories -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des catégories -->
          <tr v-for="categorie in filteredCategories" :key="categorie.categorieId">
            <td>{{ categorie.categorieNom }}</td>
            <td>{{ categorie.categorieDescription }}</td>
            <td>
              <template v-if="!categorie.editing">
                <button @click="editCategorie(categorie)" class="btn btn-sm btn-primary">Modifier</button>
                <button @click="confirmDelete(categorie)" class="btn btn-sm btn-danger">Supprimer</button>
                <span v-if="categorie.confirmDelete">
                  Confirmer ?
                  <button @click="deleteCategorie(categorie)" class="btn btn-sm btn-danger">Oui</button>
                  <button @click="cancelDelete(categorie)" class="btn btn-sm btn-secondary">Non</button>
                </span>
              </template>
              <template v-else>
                <button @click="saveCategorie(categorie)" class="btn btn-sm btn-success">Enregistrer</button>
                <button @click="cancelEdit(categorie)" class="btn btn-sm btn-secondary">Annuler</button>
              </template>
            </td>
          </tr>
          <!-- Si aucun résultat ne correspond à la recherche -->
          <tr v-if="filteredCategories.length === 0 && searchQuery !== ''">
            <td colspan="3" style="text-align: center;">Aucune catégorie trouvée.</td>
          </tr>
          <!-- Afficher un message si toutes les catégories sont visibles -->
          <tr v-if="!searchQuery && categories.length > 0 && filteredCategories.length === categories.length">
            <td colspan="3" style="text-align: center;">Affichage de toutes les catégories.</td>
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
      categories: [],
      searchQuery: '',
      filteredCategories: []
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
          ...categorie,
          editing: false,
          confirmDelete: false
        }));
        this.filteredCategories = [...this.categories];
      } catch (error) {
        console.error('Erreur lors de la récupération des catégories :', error);
        // Gérer l'erreur ici (ex: affichage d'un message d'erreur à l'utilisateur)
      }
    },
    async deleteCategorie(categorie) {
      const categorieId = categorie.categorieId;
      try {
        await CategorieService.delete(categorieId);
        this.categories = this.categories.filter(c => c.categorieId !== categorieId);
        this.filteredCategories = this.filteredCategories.filter(c => c.categorieId !== categorieId);
      } catch (error) {
        console.error('Erreur lors de la suppression de la catégorie :', error);
      }
    },
    confirmDelete(categorie) {
      categorie.confirmDelete = true;
    },
    cancelDelete(categorie) {
      categorie.confirmDelete = false;
    },
    editCategorie(categorie) {
      categorie.editing = true;
    },
    async saveCategorie(categorie) {
      try {
        await CategorieService.update(categorie.categorieId, categorie);
        categorie.editing = false;
        const index = this.categories.findIndex(c => c.categorieId === categorie.categorieId);
        if (index !== -1) {
          this.categories[index] = { ...categorie };
          this.filteredCategories = [...this.categories];
        }
      } catch (error) {
        console.error('Erreur lors de la sauvegarde des modifications de la catégorie :', error);
      }
    },
    cancelEdit(categorie) {
      categorie.editing = false;
    },
    filterCategories() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredCategories = [...this.categories];
      } else {
        this.filteredCategories = this.categories.filter(categorie =>
          categorie.categorieNom.toLowerCase().includes(query) ||
          categorie.categorieDescription.toLowerCase().includes(query)
        );
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
