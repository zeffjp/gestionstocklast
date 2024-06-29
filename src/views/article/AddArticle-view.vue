<template>
  <div class="add-article">
    <h2>Ajouter un Article</h2>
    <form @submit.prevent="createArticle">
      <div class="form-group">
        <input type="text" v-model="newArticle.nom" class="form-control" placeholder="Nom" required>
      </div>
      <div class="form-group">
        <textarea type="text" v-model="newArticle.description" class="form-control" placeholder="Description" required></textarea>  
      </div>
      <div class="form-group">
        <input type="text" v-model="newArticle.categorie" class="form-control" placeholder="Categorie" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newArticle.prix" class="form-control" placeholder="Prix" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newArticle.quantiteenstock" class="form-control" placeholder="Quantite en Stock" required>
      </div>
      <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>
  </div>
</template>

<script>
import ArticleService from '@/services/ArticleService';

export default {
  name: 'AddArticle',
  data() {
    return {
      newArticle: {
        nom: '',
        description: '',
        categorie: '',
        prix: null,
        quantiteenstock: null
      }
    };
  },
  methods: {
    async createArticle() {
      try {
        const response = await ArticleService.create(this.newArticle);
        console.log('Nouvel article ajouté :', response.data);
        this.newArticle = {
          nom: '',
          description: '',
          prix: null
        };
        this.$router.push('/articles');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'article :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-article {
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
input[type="number"] {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc; /* Bordure légère */
  border-radius: 4px;
  background-color: #fff; /* Fond blanc */
  color: #333; /* Couleur de texte principale */
}

input[type="text"]:focus,
input[type="date"]:focus,
input[type="number"]:focus {
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
