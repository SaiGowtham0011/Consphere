import api from './api';

export const followService = {
  followUser: async (username) => {
    const response = await api.post(`/follow/${username}`);
    return response.data;
  },

  unfollowUser: async (username) => {
    const response = await api.delete(`/follow/${username}`);
    return response.data;
  },

  getFollowers: async () => {
    const response = await api.get('/follow/followers');
    return response.data;
  },

  getFollowing: async () => {
    const response = await api.get('/follow/following');
    return response.data;
  }
};
