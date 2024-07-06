import axios from 'axios';

const API_URL = 'http://localhost:8080/api/clients';

const axiosConfig = {
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true 
};

const ClientService = {
  getAll() {
    return axios.get(API_URL, axiosConfig);
  },

  getById(id) {
    return axios.get(`${API_URL}/${id}`, axiosConfig);
  },

  create(clientData) {
    return axios.post(API_URL, clientData, axiosConfig);
  },

  update(id, clientData) {
    return axios.put(`${API_URL}/${id}`, clientData, axiosConfig);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`, axiosConfig);
  }
};

export default ClientService;
