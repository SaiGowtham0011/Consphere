import api from './api';

export const commentService = {
  getCommentsByPost: async (postId) => {
    const response = await api.get(`/comments/post/${postId}`);
    return response.data;
  },

  addComment: async (postId, content) => {
    const response = await api.post(`/comments/post/${postId}`, { content });
    return response.data;
  },

  updateComment: async (commentId, content) => {
    const response = await api.put(`/comments/${commentId}`, { content });
    return response.data;
  },

  deleteComment: async (commentId) => {
    const response = await api.delete(`/comments/${commentId}`);
    return response.data;
  }
};
