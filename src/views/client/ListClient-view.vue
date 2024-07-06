<template>
  <div class="client-list">
    <h2>Liste des Clients</h2>

    <!-- Tableau des clients -->
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="clients.length === 0">
            <td colspan="5" class="no-data">Aucun client trouvé.</td>
          </tr>
          <tr v-else v-for="client in clients" :key="client.clientId" class="client-row">
            <td>{{ client.clientNom }}</td>
            <td>{{ client.clientPrenom }}</td>
            <td>{{ client.clientEmail }}</td>
            <td>{{ client.clientTelephone }}</td>
            <td>
              <!-- Actions à ajouter ici si nécessaire -->
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import ClientService from '@/services/ClientService';

export default {
  name: 'ListClient',
  data() {
    return {
      clients: []
    };
  },
  created() {
    this.fetchClients();
  },
  methods: {
    async fetchClients() {
      try {
        const response = await ClientService.getAllClients();
        this.clients = response.data;
      } catch (error) {
        console.error('Erreur lors du chargement des clients :', error);
        alert('Une erreur s\'est produite lors du chargement des clients.');
      }
    }
  }
};
</script>

<style scoped>
.client-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 0 20px rgba(118, 122, 122, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: rgb(60, 67, 67);
}

h2 {
  color: #444;
  text-align: center;
  margin-bottom: 20px;
}

.table-responsive {
  margin-top: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.table th {
  background-color: #b6c7d9;
  color: #fff;
  font-weight: bold;
  text-align: center;
  padding: 12px;
}

.table td {
  text-align: center;
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 0, 0, 0.05);
}

.no-data {
  text-align: center;
  font-style: italic;
  color: #888;
  padding: 20px; /* Ajout d'un padding pour améliorer la lisibilité */
  font-size: 1.2rem; /* Taille de police légèrement augmentée */
}
</style>
