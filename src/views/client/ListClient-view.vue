<template>
  <div class="vente-list">
    <h2>Liste des Ventes</h2>

    <!-- Tableau des ventes -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Client</th>
            <th>Article</th>
            <th>Date</th>
            <th>Quantité Vendue</th>
            <th>Prix Total</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des ventes -->
          <tr v-for="vente in ventes" :key="vente.venteId">
            <td>{{ vente.client }}</td>
            <td>{{ vente.article }}</td>
            <td>{{ formatDate(vente.date) }}</td>
            <td>{{ vente.quantiteVendue }}</td>
            <td>{{ vente.prixTotal }}</td>
          </tr>
          <!-- Si aucune vente n'est trouvée -->
          <tr v-if="ventes.length === 0">
            <td colspan="5" class="no-data">Aucune vente trouvée.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
// Importez votre service ClientService ici
import ClientService from '@/services/ClientService';

export default {
  name: 'ListVente',
  data() {
    return {
      ventes: []
    };
  },
  created() {
    this.fetchVentes();
  },
  methods: {
    async fetchVentes() {
      try {
        // Utilisez ClientService pour récupérer les ventes
        const response = await ClientService.getVentes();
        this.ventes = response.data.map(vente => ({
          ...vente,
          date: new Date(vente.date) // Assurez-vous que la date est correctement interprétée
        }));
      } catch (error) {
        console.error('Erreur lors du chargement des ventes :', error);
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('fr-FR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    }
  }
};
</script>


<style scoped>
.vente-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(118, 122, 122, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: rgb(60, 67, 67);
}

h2 {
  color: #444;
  text-align: center;
}

.table-responsive {
  margin-top: 20px;
  overflow-x: auto;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th, .table td {
  padding: 12px;
  text-align: center;
  border: 1px solid #ddd;
}

.table th {
  background-color: #b6c7d9;
  color: #fff;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 0, 0, 0.05);
}

.no-data {
  text-align: center;
  color: #444;
}

.table td {
  color: rgb(15, 16, 16);
}
</style>
