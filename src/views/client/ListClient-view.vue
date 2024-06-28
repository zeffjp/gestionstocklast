<template>
  <div class="client-list">
    <h2>Liste des Clients</h2>

    <!-- Champ de recherche -->
    <input type="text" v-model="searchQuery" placeholder="Rechercher" @input="filterClients" class="form-control mb-3">

    <!-- Tableau des clients -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
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
          <!-- Affichage des clients filtrés -->
          <tr v-for="client in filteredClients" :key="client.id">
            <td>
              <template v-if="!client.editing">{{ client.nom }}</template>
              <input v-model="client.nom" v-else class="form-control">
            </td>
            <td>
              <template v-if="!client.editing">{{ client.prenom }}</template>
              <input v-model="client.prenom" v-else class="form-control">
            </td>
            <td>
              <template v-if="!client.editing">{{ client.email }}</template>
              <input v-model="client.email" v-else class="form-control">
            </td>
            <td>
              <template v-if="!client.editing">{{ client.telephone }}</template>
              <input v-model="client.telephone" v-else class="form-control">
            </td>
            <td>
              <template v-if="!client.editing">
                <button @click="editClient(client)" class="btn btn-sm btn-primary">Modifier</button>
                <button @click="confirmDelete(client)" class="btn btn-sm btn-danger">Supprimer</button>
                <span v-if="client.confirmDelete">
                  Confirmer ? 
                  <button @click="deleteClient(client)" class="btn btn-sm btn-danger">Oui</button> 
                  <button @click="cancelDelete(client)" class="btn btn-sm btn-secondary">Non</button>
                </span>
              </template>
              <template v-else>
                <button @click="saveClient(client)" class="btn btn-sm btn-success">Enregistrer</button>
                <button @click="cancelEdit(client)" class="btn btn-sm btn-secondary">Annuler</button>
              </template>
            </td>
          </tr>
          <!-- Si aucun résultat ne correspond à la recherche -->
          <tr v-if="filteredClients.length === 0 && searchQuery !== ''">
            <td colspan="5" style="text-align: center;">Aucun résultat trouvé.</td>
          </tr>
          <!-- Afficher un message si tous les clients sont visibles -->
          <tr v-if="!searchQuery && clients.length > 0 && filteredClients.length === clients.length">
            <td colspan="5" style="text-align: center;">Affichage de tous les clients.</td>
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
      clients: [],
      searchQuery: '',
      filteredClients: []
    };
  },
  methods: {
    async fetchClients() {
      try {
        const response = await ClientService.getAll();
        this.clients = response.data.map(client => ({
          ...client,
          editing: false,
          confirmDelete: false
        }));
        this.filteredClients = [...this.clients];
      } catch (error) {
        console.error('Erreur lors de la récupération des clients :', error);
      }
    },
    async deleteClient(client) {
      const clientId = client.id;
      try {
        await ClientService.delete(clientId);
        this.clients = this.clients.filter(c => c.id !== clientId);
        this.filteredClients = this.filteredClients.filter(c => c.id !== clientId);
      } catch (error) {
        console.error('Erreur lors de la suppression du client :', error);
      }
    },
    confirmDelete(client) {
      client.confirmDelete = true;
    },
    cancelDelete(client) {
      client.confirmDelete = false;
    },
    editClient(client) {
      client.editing = true;
    },
    async saveClient(client) {
      try {
        await ClientService.update(client.id, client);
        client.editing = false;
        const index = this.clients.findIndex(c => c.id === client.id);
        if (index !== -1) {
          this.clients[index] = { ...client };
          this.filteredClients = [...this.clients];
        }
      } catch (error) {
        console.error('Erreur lors de la sauvegarde des modifications du client :', error);
      }
    },
    cancelEdit(client) {
      client.editing = false;
    },
    filterClients() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredClients = [...this.clients];
      } else {
        this.filteredClients = this.clients.filter(client =>
          client.nom.toLowerCase().includes(query) ||
          client.prenom.toLowerCase().includes(query) ||
          client.email.toLowerCase().includes(query) ||
          client.telephone.includes(query)
        );
      }
    }
  },
  created() {
    this.fetchClients();
  }
};
</script>

<style scoped>
.client-list {
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
  padding: 0.5rem 0.75rem;
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
