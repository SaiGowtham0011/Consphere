import api from './api';

export const filterService = {
  getAllFilters: async () => {
    const response = await api.get('/filters');
    return response.data;
  },

  getMyFilters: async () => {
    const response = await api.get('/filters/my-filters');
    return response.data;
  },

  getBuiltInFilters: async () => {
    const response = await api.get('/filters/built-in');
    return response.data;
  },

  getFilterById: async (id) => {
    const response = await api.get(`/filters/${id}`);
    return response.data;
  },

  createFilter: async (filterData) => {
    const response = await api.post('/filters', filterData);
    return response.data;
  },

  updateFilter: async (id, filterData) => {
    const response = await api.put(`/filters/${id}`, filterData);
    return response.data;
  },

  deleteFilter: async (id) => {
    const response = await api.delete(`/filters/${id}`);
    return response.data;
  },

  getFilterFeed: async (id) => {
    const response = await api.get(`/filters/${id}/feed`);
    return response.data;
  }
};
