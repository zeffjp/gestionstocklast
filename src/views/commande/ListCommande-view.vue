<template>
  <div class="commande-list">
    <h2>Liste des Commandes</h2>
    <table>
      <thead>
        <tr>
          <th>Numéro de Commande</th>
          <th>Client</th>
          <th>Date</th>
          <th>Article</th>
          <th>Quantité</th>
          <th>Prix Total</th>
          <th>Statut</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="commande in commandes" :key="commande.commandeNumero" class="commande-row">
          <td>{{ commande.commandeNumero }}</td>
          <td>{{ commande.client.nom }}</td>
          <td>{{ formatDate(commande.commandeDate) }}</td>
          <td>{{ commande.article.nom }}</td>
          <td>{{ commande.commandeQuantite }}</td>
          <td>{{ commande.commandePrixtotal }}</td>
          <td>{{ commande.commandeStatut }}</td>
          <td>
            <router-link :to="'/commande/edit/' + commande.commandeNumero" class="btn btn-info">Modifier</router-link>
            <button @click="deleteCommande(commande.commandeNumero)" class="btn btn-danger">Supprimer</button>
          </td>
        </tr>
        <!-- Si aucune commande n'est trouvée -->
        <tr v-if="commandes.length === 0">
          <td colspan="8" style="text-align: center;">Aucune commande trouvée.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CommandeService from '@/services/CommandeService';

export default {
  name: 'ListCommande',
  data() {
    return {
      commandes: []
    };
  },
  created() {
    this.fetchCommandes();
  },
  methods: {
    async fetchCommandes() {
      try {
        const response = await CommandeService.getAllCommandes();
        this.commandes = response.data;
      } catch (error) {
        console.error('Erreur lors du chargement des commandes :', error);
      }
    },
    async deleteCommande(commandeNumero) {
      try {
        await CommandeService.deleteCommande(commandeNumero);
        this.commandes = this.commandes.filter(commande => commande.commandeNumero !== commandeNumero);
        console.log('Commande supprimée avec succès.');
      } catch (error) {
        console.error('Erreur lors de la suppression de la commande :', error);
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
.commande-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: #444;
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
  padding: 12px;
  text-align: center;
}

th {
  background-color: #b6c7d9;
  color: #fff;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: rgba(0, 0, 0, 0.1);
}

td {
  color: #444;
}

.btn {
  padding: 8px 16px;
  margin-right: 8px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  color: #fff;
}

.btn-info {
  background-color: #007bff;
}

.btn-info:hover {
  background-color: #0056b3;
}

.btn-danger {
  background-color: #dc3545;
}

.btn-danger:hover {
  background-color: #bd2130;
}
</style>
