import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor to add Bearer token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('consphere_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor to handle global authorization errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Clear token if expired or invalid
      if (localStorage.getItem('consphere_token')) {
        localStorage.removeItem('consphere_token');
        localStorage.removeItem('consphere_user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api;
