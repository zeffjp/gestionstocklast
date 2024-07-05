<template>
  <div class="categorie-list">
    <h2>Liste des Catégories d'Articles</h2>

    <!-- Champ de recherche -->
    <input type="text" v-model="searchQuery" placeholder="Rechercher" @input="filterCategories" class="form-control mb-3">

    <!-- Tableau des catégories -->
    <table>
      <thead>
        <tr>
          <th>Nom</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="categorie in filteredCategories" :key="categorie.categorieId">
          <td>
            <template v-if="!categorie.editing">{{ categorie.categorieNom }}</template>
            <input v-model="categorie.categorieNom" v-else class="form-control">
          </td>
          <td>
            <template v-if="!categorie.editing">{{ categorie.categorieDescription }}</template>
            <input v-model="categorie.categorieDescription" v-else class="form-control">
          </td>
          <td>
            <template v-if="!categorie.editing">
              <button @click="editCategorie(categorie)" class="btn-edit">Modifier</button>
              <button @click="confirmDelete(categorie)" class="btn-delete">Supprimer</button>
              <span v-if="categorie.confirmDelete">
                Confirmer ? <button @click="deleteCategorie(categorie.categorieId)" class="btn-confirm">Oui</button> <button @click="cancelDelete(categorie)" class="btn-cancel">Non</button>
              </span>
            </template>
            <template v-else>
              <button @click="saveCategorie(categorie)" class="btn-save">Enregistrer</button>
              <button @click="cancelEdit(categorie)" class="btn-cancel">Annuler</button>
            </template>
          </td>
        </tr>
        <!-- Si aucun résultat ne correspond à la recherche -->
        <tr v-if="filteredCategories.length === 0 && searchQuery !== ''">
          <td colspan="3" style="text-align: center;">Aucun résultat trouvé.</td>
        </tr>
        <!-- Afficher un message si toutes les catégories sont visibles -->
        <tr v-if="!searchQuery && categories.length > 0 && filteredCategories.length === categories.length">
          <td colspan="3" style="text-align: center;">Affichage de toutes les catégories.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CategorieService from '@/services/CategorieService';

export default {
  name: 'ListCategorie',
  data() {
  return {
    categories: [
      {
        categorieId: 1,
        categorieNom: 'Catégorie A',
        categorieDescription: 'Description de la catégorie A',
        editing: false,
        confirmDelete: false
      },
      {
        categorieId: 2,
        categorieNom: 'Catégorie B',
        categorieDescription: 'Description de la catégorie B',
        editing: false,
        confirmDelete: false
      },
      {
        categorieId: 3,
        categorieNom: 'Catégorie C',
        categorieDescription: 'Description de la catégorie C',
        editing: false,
        confirmDelete: false
      }
    ],
    filteredCategories: [],
    searchQuery: ''
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
      }
    },
    async deleteCategorie(categorieId) {
      try {
        await CategorieService.delete(categorieId);
        this.categories = this.categories.filter(categorie => categorie.categorieId !== categorieId);
        this.filteredCategories = this.filteredCategories.filter(categorie => categorie.categorieId !== categorieId);
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
        console.error('Erreur lors de la sauvegarde des modifications :', error);
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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 8px;
  text-align: left;
}

th {
  background-color: #b6c7d9;
  color: #fff;
}

tr:nth-child(even) {
  background-color: rgba(0, 0, 0, 0.5);
}

td {
  color: rgb(15, 16, 16);
}

.btn-edit, .btn-delete, .btn-save, .btn-cancel, .btn-confirm {
  padding: 5px 10px;
  background-color: #3498db; /* Couleur bleue */
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

.btn-edit:hover, .btn-delete:hover, .btn-save:hover, .btn-cancel:hover, .btn-confirm:hover {
  background-color: #2980b9; /* Couleur bleue plus sombre au survol */
}

input[type="text"] {
  padding: 0.5rem 0.75rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 0px;
  width: 100%;
}

input[type="text"]::placeholder {
  color: #888;
}

input[type="text"]:focus {
  outline: none;
  border-color: #5b9bd5;
}
</style>
