<template>
  <div class="add-vente">
    <h2>Ajouter une Vente</h2>
    <form @submit.prevent="createVente">
      <div class="form-group">
        <input type="text" v-model="newVente.numero" class="form-control" placeholder="Numéro de vente" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newVente.client" class="form-control" placeholder="Client" required>
      </div>
      <div class="form-group">
        <input type="date" v-model="newVente.date" class="form-control" placeholder="Date de vente" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newVente.quantiteVendue" class="form-control" placeholder="Quantité Vendue (ex: 100 unités)" required>
      </div>
      <div class="form-group">
        <input type="text" v-model="newVente.prixTotal" class="form-control" placeholder="Prix Total (ex: 500 EUR)" required>
      </div>
      <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>
  </div>
</template>

<script>
import VenteService from '@/services/VenteService';

export default {
  name: 'AddVente',
  data() {
    return {
      newVente: {
        numero: '',
        client: '',
        date: '',
        quantiteVendue: '',
        prixTotal: ''
      }
    };
  },
  methods: {
    async createVente() {
      try {
        const response = await VenteService.create(this.newVente);
        console.log('Nouvelle vente ajoutée :', response.data);

        // Réinitialisation du formulaire après ajout
        this.newVente = {
          numero: '',
          client: '',
          date: '',
          quantiteVendue: '',
          prixTotal: ''
        };

        // Redirection vers la liste des ventes après ajout
        this.$router.push('/vente/liste');
      } catch (error) {
        console.error('Erreur lors de l\'ajout de la vente :', error);
      }
    }
  }
};
</script>

<style scoped>
.add-vente {
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
