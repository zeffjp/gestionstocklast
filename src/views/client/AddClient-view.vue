<template>
  <div class="add-client-container">
    <h2>Ajouter un Client</h2>
    <form @submit.prevent="createClient" class="form-container">
      <div class="form-group">
        <input type="text" v-model="newClient.clientNom" class="form-control" placeholder="Nom" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newClient.clientPrenom" class="form-control" placeholder="Prénom" required>
      </div>
      <div class="form-group">
        <input type="email" v-model="newClient.clientEmail" class="form-control" placeholder="Email" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newClient.clientAdresse" class="form-control" placeholder="Adresse" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newClient.clientTelephone" class="form-control" placeholder="Téléphone" required>
      </div>
      <button type="submit" class="btn btn-primary w-100">Ajouter</button>
    </form>
  </div>
</template>

<script>
import ClientService from '@/services/ClientService';

export default {
  name: 'AddClient',
  data() {
    return {
      newClient: {
        clientNom: '',
        clientPrenom: '',
        clientEmail: '',
        clientAdresse: '',
        clientTelephone: ''
      }
    };
  },
  methods: {
    async createClient() {
      try {
        const response = await ClientService.create(this.newClient);
        if (response && response.data) {
          console.log('Nouveau client ajouté :', response.data);
          this.newClient = {
            clientNom: '',
            clientPrenom: '',
            clientEmail: '',
            clientAdresse: '',
            clientTelephone: ''
          };
          this.$router.push('/clients/liste');
        } else {
          console.error('Réponse inattendue lors de l\'ajout du client :', response);
        }
      } catch (error) {
        console.error('Erreur lors de l\'ajout du client :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-client-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding-top: 50px; /* Espace en haut pour éloigner légèrement du haut */
}

h2 {
  font-family: 'Orbitron', sans-serif;
  font-size: 24px;
  color: rgb(58, 69, 69);
  text-align: center;
  margin-top: 0; /* Supprime la marge par défaut du titre */
}

.form-container {
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

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

input[type="text"],
input[type="email"] {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc; /* Bordure légère */
  border-radius: 4px;
  background-color: #fff; /* Fond blanc */
  color: #333; /* Couleur de texte principale */
}

input[type="text"]:focus,
input[type="email"]:focus {
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
