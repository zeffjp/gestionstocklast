<template>
  <div class="client-list">
    <h2>Liste des Clients</h2>
    <div class="table-responsive">
      <table>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Téléphone</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="clients.length === 0">
            <td colspan="4" class="no-data">Aucun client trouvé.</td>
          </tr>
          <tr v-else v-for="client in clients" :key="client.clientId" class="client-row">
            <td>{{ client.clientNom }}</td>
            <td>{{ client.clientPrenom }}</td>
            <td>{{ client.clientEmail }}</td>
            <td>{{ client.clientTelephone }}</td>
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
        const response = await ClientService.getAll();
        this.clients = response.data;
      } catch (error) {
        console.error('Erreur lors du chargement des clients :', error);
        // Afficher un message d'erreur ou gérer l'erreur de manière appropriée
      }
    }
  }
};
</script>

<style scoped>
.client-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: #000;
}

h2 {
  color: #444;
  text-align: center;
  margin-bottom: 20px;
}

.table-responsive {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

th, td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #b6c7d9;
  color: #fff;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: rgba(0, 0, 0, 0.05);
}

td {
  color: #0f0d0d;
}

.no-data {
  text-align: center;
  font-style: italic;
  color: #888;
  padding: 20px; /* Ajout d'un padding pour améliorer la lisibilité */
  font-size: 1.2rem; /* Taille de police légèrement augmentée */
}
</style>
