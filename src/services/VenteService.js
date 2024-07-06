import axios from 'axios';

const API_URL = 'http://localhost:8080/api/ventes';

const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const VenteService = {
  getAll() {
    return axios.get(API_URL, axiosConfig);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`, axiosConfig);
  },

  create(venteData) {
    return axios.post(API_URL, venteData, axiosConfig);
  },

  update(venteId, venteData) {
    return axios.put(`${API_URL}/${venteId}`, venteData, axiosConfig);
  },

  delete(venteId) {
    return axios.delete(`${API_URL}/${venteId}`, axiosConfig);
  }
};

export default VenteService;
