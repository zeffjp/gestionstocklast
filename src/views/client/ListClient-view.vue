<template>
  <div class="client-list">
    <h2>Liste des Clients</h2>

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
            <td v-if="!client.editing">{{ client.clientNom }}</td>
            <td v-else><input v-model="client.clientNom" /></td>

            <td v-if="!client.editing">{{ client.clientPrenom }}</td>
            <td v-else><input v-model="client.clientPrenom" /></td>

            <td v-if="!client.editing">{{ client.clientEmail }}</td>
            <td v-else><input v-model="client.clientEmail" /></td>

            <td v-if="!client.editing">{{ client.clientTelephone }}</td>
            <td v-else><input v-model="client.clientTelephone" /></td>

            <td>
              <template v-if="!client.editing">
                <button class="btn btn-primary" @click="editClient(client)">Modifier</button>
                <button class="btn btn-danger" @click="confirmDelete(client)">Supprimer</button>
                <span v-if="client.confirmDelete">
                  Confirmer ?
                  <button @click="deleteClient(client)" class="btn btn-sm btn-danger">Oui</button>
                  <button @click="cancelDelete(client)" class="btn btn-sm btn-secondary">Non</button>
                </span>
              </template>
              <template v-else>
                <button class="btn btn-success" @click="saveClient(client)">Enregistrer</button>
                <button class="btn btn-secondary" @click="cancelEdit(client)">Annuler</button>
              </template>
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
    },
    editClient(client) {
      client.editing = true;
    },
    cancelEdit(client) {
      client.editing = false;
    },
    async saveClient(client) {
      try {
        await ClientService.updateClient(client.clientId, client);
        client.editing = false;
      } catch (error) {
        console.error('Erreur lors de la mise à jour du client :', error);
        alert('Une erreur s\'est produite lors de la mise à jour du client.');
      }
    },
    confirmDelete(client) {
      client.confirmDelete = true;
    },
    cancelDelete(client) {
      client.confirmDelete = false;
    },
    async deleteClient(client) {
      try {
        await ClientService.deleteClient(client.clientId);
        this.clients = this.clients.filter(c => c.clientId !== client.clientId);
      } catch (error) {
        console.error('Erreur lors de la suppression du client :', error);
        alert('Une erreur s\'est produite lors de la suppression du client.');
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
  padding: 20px;
  font-size: 1.2rem;
}
</style>
