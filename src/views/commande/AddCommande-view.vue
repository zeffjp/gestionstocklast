<template>
  <div class="add-commande">
    <h2>Ajouter une Commande</h2>
    <form @submit.prevent="create">
      <div class="form-group">
        <input type="text" v-model="newCommande.client.nom" class="form-control" placeholder="Client" required>
      </div>
      <div class="form-group">
        <input type="date" v-model="newCommande.commandeDate" class="form-control" placeholder="Date de commande" required>
      </div>
      <div class="form-group">
        <select v-model="newCommande.article.nom" class="form-control" required>
          <option disabled value="">Sélectionner un article</option>
          <option v-for="article in articles" :key="article.id" :value="article.nom">{{ article.nom }}</option>
        </select>
      </div>
      <div class="form-group">
        <input type="number" v-model="newCommande.commandeQuantite" class="form-control" placeholder="Quantité" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newCommande.commandePrixtotal" class="form-control" placeholder="Prix total" required>
      </div>
      <div class="form-group">
        <select v-model="newCommande.commandeStatut" class="form-control" required>
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
import ArticleService from '@/services/ArticleService';

export default {
  name: 'AddCommande',
  data() {
    return {
      newCommande: {
        client: { nom: '' },
        commandeDate: '',
        article: { nom: '' },
        commandeQuantite: 0,
        commandePrixtotal: 0,
        commandeStatut: ''
      },
      articles: []
    };
  },
  created() {
    this.fetchArticles();
  },
  methods: {
    async fetchArticles() {
      try {
        const response = await ArticleService.getAll();
        this.articles = response.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des articles :', error);
      }
    },
    async create() {
      try {
        const response = await CommandeService.create(this.newCommande);
        console.log('Nouvelle commande ajoutée :', response.data);
        this.newCommande = {
          client: { nom: '' },
          commandeDate: '',
          article: { nom: '' },
          commandeQuantite: 0,
          commandePrixtotal: 0,
          commandeStatut: ''
        };
        this.$router.push('/commande/ListCommande'); // Adjusted router path
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
  background-color: #007bff; /* Couleur de fond du bouton */
  color: #fff; /* Couleur de texte */
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease; /* Transition fluide */
}

button:hover {
  background-color: #0056b3; /* Couleur de fond au survol */
}
</style>
