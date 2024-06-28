<template>
  <div class="vente-list">
    <h2>Liste des Ventes</h2>

    <!-- Champ de recherche -->
    <div class="search-container">
      <input type="text" v-model="searchQuery" placeholder="Rechercher" class="filter-input" @input="filterVentes">
      <i class="fa fa-search search-icon"></i>
    </div>

    <!-- Tableau des ventes -->
    <table>
      <thead>
        <tr>
          <th>Client</th>
          <th>Date</th>
          <th>Article Vendu</th>
          <th>Quantité Vendue</th>
          <th>Prix Total</th>
          <th>Statut</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <!-- Affichage des ventes filtrées -->
        <tr v-for="vente in filteredVentes" :key="vente.id">
          <td>{{ vente.client }}</td>
          <td>{{ formatDate(vente.date) }}</td>
          <td>{{ vente.article }}</td>
          <td
            contenteditable
            @blur="updateVente(vente.id, 'quantite', $event.target.textContent)"
            :class="{ 'editable': vente.editMode && vente.id === editedVenteId }"
          >
            {{ vente.quantite }}
          </td>
          <td>{{ vente.prixTotal }}</td>
          <td>
            <select
              v-if="vente.editMode"
              v-model="vente.statut"
              @change="updateVente(vente.id, 'statut', $event.target.value)"
            >
              <option value="En cours">En cours</option>
              <option value="Terminée">Terminée</option>
              <option value="Annulée">Annulée</option>
            </select>
            <span v-else>{{ vente.statut }}</span>
          </td>
          <td>
            <button v-if="!vente.editMode" @click="enableEditMode(vente.id)">Modifier</button>
            <button v-else @click="saveVente(vente.id)">Sauvegarder</button>
            <button @click="deleteVente(vente.id)">Supprimer</button>
          </td>
        </tr>
        <!-- Afficher un message si aucune vente ne correspond à la recherche -->
        <tr v-if="filteredVentes.length === 0 && searchQuery !== ''">
          <td colspan="7" style="text-align: center;">Aucune vente trouvée.</td>
        </tr>
        <!-- Afficher un message si toutes les ventes sont visibles -->
        <tr v-if="!searchQuery && ventes.length > 0 && filteredVentes.length === ventes.length">
          <td colspan="7" style="text-align: center;">Affichage de toutes les ventes.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import VenteService from '@/services/VenteService';

export default {
  name: 'ListVente',
  data() {
    return {
      ventes: [],
      editedVenteId: null,
      searchQuery: '',
      filteredVentes: []
    };
  },
  methods: {
    async fetchVentes() {
      try {
        const response = await VenteService.getAll();
        this.ventes = response.data;
        this.filteredVentes = [...this.ventes];
      } catch (error) {
        console.error('Erreur lors de la récupération des ventes :', error);
      }
    },
    async deleteVente(venteId) {
      try {
        await VenteService.delete(venteId);
        this.ventes = this.ventes.filter(vente => vente.id !== venteId);
        this.filteredVentes = this.filteredVentes.filter(vente => vente.id !== venteId);
        console.log(`Vente ${venteId} supprimée.`);
      } catch (error) {
        console.error('Erreur lors de la suppression de la vente :', error);
      }
    },
    updateVente(venteId, field, value) {
      const vente = this.ventes.find(v => v.id === venteId);
      if (vente) {
        vente[field] = value;
      }
    },
    enableEditMode(venteId) {
      this.ventes.forEach(vente => {
        vente.editMode = vente.id === venteId;
      });
      this.editedVenteId = venteId;
    },
    async saveVente(venteId) {
      const editedVente = this.ventes.find(v => v.id === venteId);
      if (editedVente) {
        editedVente.editMode = false;
        this.editedVenteId = null;

        try {
          await VenteService.update(venteId, editedVente);
          console.log(`Vente ${venteId} mise à jour sur le serveur.`);
        } catch (error) {
          console.error('Erreur lors de la mise à jour de la vente sur le serveur :', error);
        }
      }
    },
    formatDate(date) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(date).toLocaleDateString('fr-FR', options);
    },
    filterVentes() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredVentes = [...this.ventes];
      } else {
        this.filteredVentes = this.ventes.filter(vente =>
          vente.client.toLowerCase().includes(query) ||
          vente.article.toLowerCase().includes(query) ||
          vente.numero.toLowerCase().includes(query)
        );
      }
    }
  },
  created() {
    this.fetchVentes();
  }
};
</script>

<style scoped>
.vente-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: #fff;
}

h2 {
  color: #444;
}

.search-container {
  margin-bottom: 20px;
  position: relative;
}

.filter-input {
  padding: 10px;
  width: calc(100% - 30px); /* Ajustement pour inclure l'icône de recherche */
  border: none;
  border-radius: 15px;
  font-family: 'Orbitron', sans-serif;
}

.search-icon {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  color: #fff;
  font-size: 18px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: center;
}

th {
  background-color: #b6c7d9;
  color: #fff;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: rgba(0, 0, 0, 0.5);
}

td {
  color: #fff;
}

button {
  padding: 8px 12px;
  background-color: #99abab;
  color: #000;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
  margin-right: 5px;
}

button:hover {
  background-color: #747487;
  color: #fff;
}
</style>
