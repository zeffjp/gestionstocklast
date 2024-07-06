<template>
  <div class="add-article">
    <h2>Ajouter un Article</h2>
    <form @submit.prevent="createArticle">
      <div class="form-group">
        <input type="text" v-model="newArticle.articleNom" class="form-control" placeholder="Nom de l'article" required>
      </div>
      <div class="form-group">
        <textarea v-model="newArticle.articleDescription" class="form-control" placeholder="Description de l'article" required></textarea>
      </div>
      <div class="form-group">
        <input type="number" v-model="newArticle.articlePrix" class="form-control" placeholder="Prix de l'article" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newArticle.articleQuantite" class="form-control" placeholder="Quantité en Stock" required>
      </div>
      <div class="form-group">
        <input type="number" v-model="newArticle.categorieId" class="form-control" placeholder="ID de Catégorie" required>
      </div>
      <div class="form-group">
        <label for="articleImage">Image de l'Article</label>
        <input id="articleImage" type="file" @change="handleImageUpload" accept="image/*">
        <!-- Aperçu de l'image sélectionnée -->
        <img v-if="newArticle.imagePreview" :src="newArticle.imagePreview" alt="Aperçu de l'image" style="max-width: 200px; margin-top: 10px;">
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
        articleNom: '',
        articleDescription: '',
        articlePrix: null,
        articleQuantite: null,
        categorieId: null,
        image: null, // Fichier de l'image à envoyer au serveur
        imagePreview: null // Aperçu de l'image pour l'utilisateur
      }
    };
  },
  methods: {
    async createArticle() {
      try {
        // Upload de l'image si présente
        if (this.newArticle.image) {
          // Implémenter le code pour uploader l'image vers votre serveur
          // Exemple : const imageUrl = await ArticleService.uploadImage(this.newArticle.imageFile);
          // Puis ajouter imageUrl dans this.newArticle
          this.newArticle.imageUrl = URL.createObjectURL(this.newArticle.image);
        }

        // Création de l'article
        const response = await ArticleService.createArticle(this.newArticle);
        console.log('Nouvel article ajouté :', response.data);

        // Réinitialisation du formulaire
        this.newArticle = {
          articleNom: '',
          articleDescription: '',
          articlePrix: null,
          articleQuantite: null,
          categorieId: null,
          image: null,
          imagePreview: null
        };

        // Redirection vers la liste des articles par exemple
        this.$router.push('/articles');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'article :', error);
      }
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      this.newArticle.image = file;
      this.newArticle.imagePreview = URL.createObjectURL(file); // Aperçu de l'image
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
  background-color: #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  font-family: 'Roboto', sans-serif;
  color: #333;
}

h2 {
  color: #444;
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
textarea,
input[type="number"],
input[type="file"] {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  color: #333;
}

input[type="text"]:focus,
textarea:focus,
input[type="number"]:focus,
input[type="file"]:focus {
  outline: none;
  border-color: #555;
}

button {
  align-self: flex-end;
  padding: 10px 50px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
