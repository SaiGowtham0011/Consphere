import api from './api';

export const postService = {
  getHomeFeed: async () => {
    const response = await api.get('/posts/feed');
    return response.data;
  },

  getMyPosts: async () => {
    const response = await api.get('/posts/my-posts');
    return response.data;
  },

  getUserPosts: async (username) => {
    const response = await api.get(`/posts/user/${username}`);
    return response.data;
  },

  getPostById: async (id) => {
    const response = await api.get(`/posts/${id}`);
    return response.data;
  },

  createPost: async (postData) => {
    const response = await api.post('/posts', postData);
    return response.data;
  },

  updatePost: async (id, postData) => {
    const response = await api.put(`/posts/${id}`, postData);
    return response.data;
  },

  deletePost: async (id) => {
    const response = await api.delete(`/posts/${id}`);
    return response.data;
  }
};
