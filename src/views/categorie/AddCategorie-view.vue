<template>
  <div class="add-categorie">
    <h2>Ajouter une Catégorie d'Articles</h2>
    <form @submit.prevent="createCategorie" class="form-container">
      <div class="form-group">
        <input type="text" id="nom" v-model="newCategorie.nom" class="form-control" placeholder="Nom" required>
      </div>
      <div class="form-group">
        <textarea id="description" v-model="newCategorie.description" class="form-control" placeholder="Description"></textarea>
      </div>
      <div class="form-group">
        <input type="number" id="Quantitearticle" v-model="newCategorie.Quantitearticle" class="form-control" placeholder="Quantité d'articles" required>
      </div>
      <button type="submit" class="btn btn-primary w-100">Ajouter</button>
    </form>
  </div>
</template>

<script>
import CategorieService from '@/services/CategorieService';

export default {
  name: 'AddCategorie',
  data() {
    return {
      newCategorie: {
        nom: '',
        description: '',
        Quantitearticle: ''
      }
    };
  },
  methods: {
    async createCategorie() {
      try {
        const response = await CategorieService.create(this.newCategorie);
        console.log('Nouvelle catégorie ajoutée :', response.data);
        this.newCategorie = {
          nom: '',
          description: '',
          Quantitearticle: ''
        };
        this.$router.push('/categorie-article/liste');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de la catégorie :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-categorie {
  width: 100%;
  max-width: 600px;
  margin: 50px auto;
  padding: 30px;
  background-color: #f0f0f0; /* Couleur de fond sombre */
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: #fff; /* Couleur de texte clair */
}

h2 {
  color: #3a4545; /* Couleur du titre */
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

input[type="text"],
textarea,
input[type="number"] {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc; /* Bordure légère */
  border-radius: 4px;
  background-color: #fff; /* Fond blanc */
  color: #333; /* Couleur de texte principale */
}

input[type="text"]:focus,
textarea:focus,
input[type="number"]:focus {
  outline: none;
  border-color: #555; /* Couleur de bordure au focus */
}

button[type="submit"] {
  background-color: #007bff; /* Bleu pour le bouton */
  color: #fff; /* Texte blanc */
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

button[type="submit"]:hover {
  background-color: #0056b3; /* Variation de bleu au survol */
}
</style>
