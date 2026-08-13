import React, { createContext, useState, useEffect, useContext } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(authService.getCurrentUser());
  const [token, setToken] = useState(authService.getToken());
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      const storedToken = authService.getToken();
      if (storedToken) {
        try {
          const profile = await authService.fetchUserProfile();
          setUser({
            userId: profile.id,
            username: profile.username,
            email: profile.email,
            firstName: profile.firstName,
            lastName: profile.lastName,
            profilePicURL: profile.profilePicURL
          });
        } catch (error) {
          console.error("Auth verification failed:", error);
          authService.logout();
          setUser(null);
          setToken(null);
        }
      } else {
        setUser(null);
        setToken(null);
      }
      setLoading(false);
    };

    initializeAuth();
  }, []);

  const login = async (username, password) => {
    const data = await authService.login(username, password);
    setToken(data.token);
    setUser({
      userId: data.userId,
      username: data.username,
      profilePicURL: data.profilePicURL
    });
    return data;
  };

  const register = async (userData) => {
    return await authService.register(userData);
  };

  const logout = () => {
    authService.logout();
    setUser(null);
    setToken(null);
  };

  const updateUserState = (updatedUser) => {
    setUser(prev => ({ ...prev, ...updatedUser }));
  };

  return (
    <AuthContext.Provider value={{ user, token, loading, login, register, logout, updateUserState }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
