import axios from 'axios';

const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const CategorieService = {
  getAll() {
    return axios.get('http://localhost:8080/api/categories', axiosConfig);
  },

  getById(id) {
    return axios.get(`http://localhost:8080/api/categories/${id}`, axiosConfig);
  },

  create(data) {
    return axios.post('http://localhost:8080/api/categories', data, axiosConfig);
  },

  update(id, data) {
    return axios.put(`http://localhost:8080/api/categories/${id}`, data, axiosConfig);
  },

  delete(id) {
    return axios.delete(`http://localhost:8080/api/categories/${id}`, axiosConfig);
  }
};

export default CategorieService;
