<template>
  <div class="login-container">
    <div class="login-box">
      <h2>Connexion</h2>
      <form @submit.prevent="login" class="login-form">
        <label>Email:</label>
        <input type="email" v-model="email" required>
        <label>Mot de passe:</label>
        <input type="password" v-model="password" required>
        <button type="submit">Se connecter</button>
      </form>
    </div>
  </div>
</template>

<script>
import AuthService from '../services/AuthService';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async login() {
      const loginData = {
        email: this.email,
        password: this.password
      };
      try {
        await AuthService.login(loginData);
        this.$emit('login-success'); // Émet un événement de succès de connexion
        // Redirection gérée par le composant parent (App.vue)
      } catch (error) {
        console.error('Erreur lors de la connexion :', error);
        // Gestion des erreurs ici (affichage d'un message, réinitialisation des champs, etc.)
      }
    }
  }
};
</script>

<style scoped>
/* Styles spécifiques au composant Login-component.vue */

.login-container {
  display: flex;
  justify-content: center; /* Centre horizontalement */
  align-items: center; /* Centre verticalement */
  min-height: 100vh; /* Pour centrer le formulaire verticalement sur toute la hauteur de la vue */
}

.login-box {
  max-width: 400px;
  width: 100%;
  padding: 20px;
  border: 1px solid #000000;
  border-radius: 10px; /* Augmenter la bordure arrondie */
  background-color: #fff;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* Augmenter l'ombre pour une meilleure visibilité */
  text-align: center; /* Pour centrer le titre */
}

.login-form {
  margin-top: 20px; /* Espacement entre le titre et le formulaire */
}

.login-form label {
  display: block;
  font-weight: bold;
  margin-bottom: 10px;
}

.login-form input[type="email"],
.login-form input[type="password"] {
  width: 100%;
  padding: 12px; /* Augmenter le padding pour une meilleure visibilité */
  margin-bottom: 15px;
  border: 2px solid #007bff; /* Augmenter l'épaisseur de la bordure et changer la couleur */
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
}

.login-form input[type="email"]:focus,
.login-form input[type="password"]:focus {
  border-color: #0056b3; /* Changer la couleur de bordure lorsque le champ est focalisé */
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Ajouter une ombre pour une meilleure visibilité */
}

.login-form button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: #fceded;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s; /* Ajouter une transition pour l'effet de survol */
}

.login-form button:hover {
  background-color: #0056b3;
}
</style>
