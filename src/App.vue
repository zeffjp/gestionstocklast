<template>
  <div id="app">
    <AppHeader />
    <div class="main-content">
      <Dashboard />
      <div class="content">
        <router-view />
      </div>
    </div>
    <AppFooter />
  </div>
</template>

<script>
import AppFooter from './components/Footer-component.vue';
import AppHeader from './components/Header-component.vue';
import Dashboard from './components/dashboard-component.vue';

export default {
  name: 'App',
  components: {
    AppFooter,
    AppHeader,
    Dashboard
  }
};
</script>

<style scoped>
/* Styles personnalisés spécifiques à App.vue */
#app {
  font-family: 'Avenir', 'Helvetica', 'Arial', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #414346;
  background-color: #757c83; /* Couleur de fond gris foncé */
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.main-content {
  display: flex;
  flex: 1;
  margin-top: 63px;
}

.content {
  flex-grow: 1;
  padding: 20px;
  margin-left: 230px; /* Espacement à gauche pour le dashboard */
  background-color: #f5f5f5; /* Couleur de fond claire pour le contenu */
  border-radius: 10px;
}

footer {
  padding-bottom: 50px; /* Espacement du pied de page */
}
</style>
