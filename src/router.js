import { createRouter, createWebHistory } from 'vue-router';

import ListClient from './views/client/ListClient-view.vue';
import AddClient from './views/client/AddClient-view.vue';
import ListArticle from './views/article/ListArticle-view.vue';
import AddArticle from './views/article/AddArticle-view.vue';
import ListCategorie from './views/categorie/ListCategorie-view.vue';
import AddCategorie from './views/categorie/AddCategorie-view.vue';
import ListCommande from './views/commande/ListCommande-view.vue';
import AddCommande from './views/commande/AddCommande-view.vue';
import ListVente from './views/vente/ListVente-view.vue';
import AddVente from './views/vente/AddVente-view.vue';
import Home from './components/Home-component.vue';
import WelcoMemessage from './views/Acceuil/Acceuil-view.vue';
import ListeCategorie from './views/categorie/ListCategorie-view.vue';
import ListeArticle from './views/article/ListArticle-view.vue';
import ListeCommande from './views/commande/ListCommande-view.vue';
import ListeVente from './views/vente/ListVente-view.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },

  {
    path: '/acceuil',
    name: 'WelcoMemessage',
    component: WelcoMemessage
  },
  {
    path: '/clients',
    name: 'ListClient',
    component: ListClient,
    meta: { requiresAuth: true }
  },
  {
    path: '/clients/add',
    name: 'AddClient',
    component: AddClient,
    meta: { requiresAuth: true }
  },
  {
    path: '/articles',
    name: 'ListArticle',
    component: ListArticle,
    meta: { requiresAuth: true }
  },
  {
    path: '/articles/add',
    name: 'AddArticle',
    component: AddArticle,
    meta: { requiresAuth: true }
  },
  {
    path: '/categories',
    name: 'ListCategorie',
    component: ListCategorie,
    meta: { requiresAuth: true }
  },
  {
    path: '/categories/add',
    name: 'AddCategorie',
    component: AddCategorie,
    meta: { requiresAuth: true }
  },
  {
    path: '/commandes',
    name: 'ListCommande',
    component: ListCommande,
    meta: { requiresAuth: true }
  },
  {
    path: '/commandes/add',
    name: 'AddCommande',
    component: AddCommande,
    meta: { requiresAuth: true }
  },
  {
    path: '/ventes',
    name: 'ListVente',
    component: ListVente,
    meta: { requiresAuth: true }
  },
  {
    path: '/ventes/add',
    name: 'AddVente',
    component: AddVente,
    meta: { requiresAuth: true }
  },
  {
    path: '/articles/ListeArticle-view',
    name: 'ListeArticle',
    component: ListeArticle,
    meta: { requiresAuth: true }
  },
  {
    path: '/commandes/ListeCommande-view',
    name: 'ListeCommande',
    component: ListeCommande,
    meta: { requiresAuth: true }
  },
  {
    path: '/ventes/ListeVente-view',
    name: 'ListeVente',
    component: ListeVente,
    meta: { requiresAuth: true }
  },
  {
    path: '/categories/ListeCategorie-view',
    name: 'ListeCategorie',
    component: ListeCategorie,
    meta: { requiresAuth: true }
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  // Exemple de vérification factice d'authentification
  const isLoggedIn = true; // Vous devez implémenter la vraie logique de vérification d'authentification ici

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isLoggedIn) {
      next({
        path: '/login',
        query: { redirect: to.fullPath } // Redirige vers la page de connexion avec redirection après connexion
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
