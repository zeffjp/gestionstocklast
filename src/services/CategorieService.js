import axios from 'axios';

const API_URL = 'http://localhost:8080/api/categories';
const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const CategorieService = {
  getAll() {
    return axios.get(API_URL, axiosConfig);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`, axiosConfig);
  },

  create(categorieData) {
    return axios.post(API_URL, categorieData, axiosConfig);
  },

  update(id, categorieData) {
    return axios.put(`${API_URL}/${id}`, categorieData, axiosConfig);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`, axiosConfig);
  }
};

export default CategorieService;
