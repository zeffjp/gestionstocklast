import axios from 'axios';

const API_URL = 'http://localhost:8080/api/commandes';

const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const CommandeService = {
  getAll() {
    return axios.get(API_URL, axiosConfig);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`, axiosConfig);
  },

  create(commandeData) {
    return axios.post(API_URL, commandeData, axiosConfig);
  },

  update(id, commandeData) {
    return axios.put(`${API_URL}/${id}`, commandeData, axiosConfig);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`, axiosConfig);
  }
};

export default CommandeService;
