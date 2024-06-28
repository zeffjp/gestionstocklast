<template>
  <div class="add-admin-container">
    <h2>Ajouter un Administrateur</h2>
    <form @submit.prevent="createAdmin" class="form-container">
      <div class="form-group">
        <input type="text" v-model="newAdmin.nom" class="form-control" placeholder="Nom" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newAdmin.prenom" class="form-control" placeholder="Prénom" required>
      </div>
      <div class="form-group">
        <input type="email" v-model="newAdmin.email" class="form-control" placeholder="Email" required>
      </div>
      <div class="form-group">
        <input type="password" v-model="newAdmin.mdp" class="form-control" placeholder="Mot de passe" required>
      </div>
      <button type="submit" class="btn btn-primary w-100">Ajouter</button>
    </form>
  </div>
</template>

<script>
import AdminService from '@/services/AdminService';

export default {
  name: 'AddAdmin',
  data() {
    return {
      newAdmin: {
        nom: '',
        prenom: '',
        email: '',
        mdp: ''
      }
    };
  },
  methods: {
    async createAdmin() {
      try {
        const response = await AdminService.create(this.newAdmin);
        console.log('Nouvel administrateur ajouté :', response.data);
        this.newAdmin = {
          nom: '',
          prenom: '',
          email: '',
          mdp: ''
        };
        this.$router.push('/admin/liste');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de l\'administrateur :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-admin-container {
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

.form-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group {
  margin-bottom: 10px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc; /* Bordure légère */
  border-radius: 4px;
  background-color: #fff; /* Fond blanc */
  color: #333; /* Couleur de texte principale */
}

input[type="text"]:focus,
input[type="email"]:focus,
input[type="password"]:focus {
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
