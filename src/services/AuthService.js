import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth'; // Endpoint pour l'authentification
const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const AuthService = {
  login(credentials) {
    return axios.post(`${API_URL}/login`, credentials, axiosConfig);
  },

  // Ajoutez d'autres méthodes d'authentification si nécessaire, comme la déconnexion, etc.
};

export default AuthService;
