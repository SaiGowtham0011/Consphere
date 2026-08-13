import api from './api';

export const authService = {
  login: async (username, password) => {
    const response = await api.post('/auth/login', { username, password });
    if (response.data.token) {
      localStorage.setItem('consphere_token', response.data.token);
      localStorage.setItem('consphere_user', JSON.stringify({
        userId: response.data.userId,
        username: response.data.username,
        profilePicURL: response.data.profilePicURL
      }));
    }
    return response.data;
  },

  register: async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('consphere_token');
    localStorage.removeItem('consphere_user');
  },

  getCurrentUser: () => {
    const userStr = localStorage.getItem('consphere_user');
    return userStr ? JSON.parse(userStr) : null;
  },

  getToken: () => {
    return localStorage.getItem('consphere_token');
  },

  fetchUserProfile: async () => {
    const response = await api.get('/users/me');
    return response.data;
  },

  updateUserProfile: async (updateData) => {
    const response = await api.patch('/users/updateUser', updateData);
    // Update cached user info
    const currentUser = authService.getCurrentUser() || {};
    localStorage.setItem('consphere_user', JSON.stringify({
      ...currentUser,
      username: response.data.username,
      profilePicURL: response.data.profilePicURL
    }));
    return response.data;
  },

  getUserByUsername: async (username) => {
    const response = await api.get(`/users/${username}`);
    return response.data;
  }
};
