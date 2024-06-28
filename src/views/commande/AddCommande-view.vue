<template>
  <div class="add-commande">
    <h2>Ajouter une Commande</h2>
    <form @submit.prevent="createCommande">
      <div class="form-group">
        <input type="text" v-model="newCommande.numero" class="form-control" placeholder="Numéro de commande" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newCommande.client" class="form-control" placeholder="Client" required>
      </div>
      <div class="form-group">
        <input type="date" v-model="newCommande.date" class="form-control" placeholder="Date de commande" required>
      </div>
      <div class="form-group">
        <select v-model="newCommande.article" class="form-control" required>
          <option disabled value="">Sélectionner un article</option>
          <option v-for="article in articles" :key="article.id" :value="article.nom">{{ article.nom }}</option>
        </select>
      </div>
      <div class="form-group">
        <input type="number" v-model="newCommande.quantite" class="form-control" placeholder="Quantité" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newCommande.prixTotal" class="form-control" placeholder="Prix total" required>
      </div>
      <div class="form-group">
        <select v-model="newCommande.statut" class="form-control" required>
          <option disabled value="">Sélectionner un statut</option>
          <option value="En cours">En cours</option>
          <option value="Livré">Livré</option>
          <option value="Annulé">Annulé</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>
  </div>
</template>

<script>
import CommandeService from '@/services/CommandeService';

export default {
  name: 'AddCommande',
  data() {
    return {
      newCommande: {
        numero: '',
        client: '',
        date: '',
        article: '',
        quantite: '',
        prixTotal: '',
        statut: ''
      },
      articles: [] // Assurez-vous de récupérer les articles à partir d'un service ou d'un store Vuex
    };
  },
  methods: {
    async createCommande() {
      try {
        const response = await CommandeService.create(this.newCommande);
        console.log('Nouvelle commande ajoutée :', response.data);
        this.newCommande = {
          numero: '',
          client: '',
          date: '',
          article: '',
          quantite: 0,
          prixTotal: 0,
          statut: ''
        };
        this.$router.push('/commande/liste');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de la commande :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-commande {
  width: 100%;
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f0f0f0; /* Couleur de fond légère */
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* Légère ombre */
  font-family: 'Roboto', sans-serif; /* Police de caractères */
  color: #333; /* Couleur de texte principale */
}

h2 {
  color: #444; /* Couleur du titre */
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 10px;
}

input[type="text"],
input[type="date"],
input[type="number"],
select {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc; /* Bordure légère */
  border-radius: 4px;
  background-color: #fff; /* Fond blanc */
  color: #333; /* Couleur de texte principale */
}

input[type="text"]:focus,
input[type="date"]:focus,
input[type="number"]:focus,
select:focus {
  outline: none;
  border-color: #555; /* Couleur de bordure au focus */
}

button {
  align-self: flex-end;
  padding: 10px 50px;
  background-color: #007bff; /* Bleu pour le bouton */
  color: #fff; /* Texte blanc */
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

button:hover {
  background-color: #0056b3; /* Variation de bleu au survol */
}
</style>
