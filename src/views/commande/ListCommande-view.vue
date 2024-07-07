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
        <tr v-if="commandes.length === 0">
          <td colspan="8" class="no-data">Aucune commande trouvée.</td>
        </tr>
        <tr v-else v-for="commande in commandes" :key="commande.commandeNumero" class="commande-row">
          <td v-if="!commande.editing">{{ commande.commandeNumero }}</td>
          <td v-else><input v-model="commande.commandeNumero" /></td>
          
          <td v-if="!commande.editing">{{ commande.client.nom }}</td>
          <td v-else><input v-model="commande.client.nom" /></td>

          <td v-if="!commande.editing">{{ formatDate(commande.commandeDate) }}</td>
          <td v-else><input v-model="commande.commandeDate" /></td>

          <td v-if="!commande.editing">{{ commande.article.nom }}</td>
          <td v-else><input v-model="commande.article.nom" /></td>

          <td v-if="!commande.editing">{{ commande.commandeQuantite }}</td>
          <td v-else><input v-model="commande.commandeQuantite" /></td>

          <td v-if="!commande.editing">{{ commande.commandePrixtotal }}</td>
          <td v-else><input v-model="commande.commandePrixtotal" /></td>

          <td v-if="!commande.editing">{{ commande.commandeStatut }}</td>
          <td v-else><input v-model="commande.commandeStatut" /></td>

          <td>
            <template v-if="!commande.editing">
              <button class="btn btn-primary" @click="editCommande(commande)">Modifier</button>
              <button class="btn btn-danger" @click="confirmDelete(commande)">Supprimer</button>
              <span v-if="commande.confirmDelete">
                Confirmer ?
                <button @click="deleteCommande(commande.commandeNumero)" class="btn btn-sm btn-danger">Oui</button>
                <button @click="cancelDelete(commande)" class="btn btn-sm btn-secondary">Non</button>
              </span>
            </template>
            <template v-else>
              <button class="btn btn-success" @click="saveCommande(commande)">Enregistrer</button>
              <button class="btn btn-secondary" @click="cancelEdit(commande)">Annuler</button>
            </template>
          </td>
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
    editCommande(commande) {
      commande.editing = true;
    },
    cancelEdit(commande) {
      commande.editing = false;
    },
    async saveCommande(commande) {
      try {
        await CommandeService.updateCommande(commande.commandeNumero, commande);
        commande.editing = false;
      } catch (error) {
        console.error('Erreur lors de la mise à jour de la commande :', error);
      }
    },
    confirmDelete(commande) {
      commande.confirmDelete = true;
    },
    cancelDelete(commande) {
      commande.confirmDelete = false;
    },
    async deleteCommande(commandeNumero) {
      try {
        await CommandeService.deleteCommande(commandeNumero);
        this.commandes = this.commandes.filter(commande => commande.commandeNumero !== commandeNumero);
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
  text-align: center;
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

.no-data {
  text-align: center;
  font-style: italic;
  color: #888;
  padding: 20px; 
  font-size: 1.2rem;
}

.btn {
  padding: 8px 16px;
  margin-right: 8px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  color: #fff;
}

.btn-primary {
  background-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-danger {
  background-color: #dc3545;
}

.btn-danger:hover {
  background-color: #bd2130;
}

.btn-secondary {
  background-color: #6c757d;
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.btn-success {
  background-color: #28a745;
}

.btn-success:hover {
  background-color: #218838;
}
</style>
