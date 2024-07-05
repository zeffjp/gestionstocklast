import axios from 'axios';

const API_URL = 'http://localhost:8080/api/articles';

const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true
};

const ArticleService = {
  getAll() {
    return axios.get(API_URL, axiosConfig);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`, axiosConfig);
  },

  create(articleData) {
    return axios.post(API_URL, articleData, axiosConfig);
  },

  update(id, articleData) {
    return axios.put(`${API_URL}/${id}`, articleData, axiosConfig);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`, axiosConfig);
  }
};

export default ArticleService;
