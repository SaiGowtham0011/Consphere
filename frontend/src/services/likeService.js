import api from './api';

export const likeService = {
  likePost: async (postId) => {
    const response = await api.post(`/likes/post/${postId}`);
    return response.data;
  },

  unlikePost: async (postId) => {
    const response = await api.delete(`/likes/post/${postId}`);
    return response.data;
  },

  getLikeStatus: async (postId) => {
    const response = await api.get(`/likes/post/${postId}`);
    return response.data;
  }
};
