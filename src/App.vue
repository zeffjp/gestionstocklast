<template>
  <div id="app">
    <AppHeader />
    <div class="main-content">
      <Login v-if="!isLoggedIn" @login-success="handleLoginSuccess" />
      <div class="content" v-else>
        <router-view />
      </div>
    </div>
    <AppFooter />
  </div>
</template>

<script>
import AppFooter from './components/Footer-component.vue';
import AppHeader from './components/Header-component.vue';
import Login from './components/Login-component.vue'; // Importez le composant de connexion

export default {
  name: 'App',
  components: {
    AppFooter,
    AppHeader,
    Login // Ajoutez le composant Login aux composants utilisés
  },
  data() {
    return {
      isLoggedIn: false // État initial de la connexion
    };
  },
  methods: {
    handleLoginSuccess() {
      this.isLoggedIn = true; // Met à jour l'état de connexion après une connexion réussie
    }
  }
};
</script>

<style scoped>
/* Styles spécifiques au composant App.vue */
#app {
  font-family: 'Avenir', 'Helvetica', 'Arial', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #414346;
  background-color: #757c83;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.main-content {
  display: flex;
  flex: 1;
  justify-content: center; /* Centrer horizontalement */
  align-items: center; /* Centrer verticalement */
  margin-top: 63px;
}

.content {
  flex-grow: 1;
  padding: 20px;
  margin-left: 230px;
  background-color: #f5f5f5;
  border-radius: 10px;
}

footer {
  padding-bottom: 50px;
}
</style>
