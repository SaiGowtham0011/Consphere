import React, { useState } from 'react';
import { UserPlus, UserCheck } from 'lucide-react';
import { followService } from '../services/followService';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const FollowButton = ({ username, initialFollowing = false, onToggleSuccess }) => {
  const [isFollowing, setIsFollowing] = useState(initialFollowing);
  const [loading, setLoading] = useState(false);
  const { user } = useAuth();
  const navigate = useNavigate();

  // Don't render follow button if viewing own profile
  if (user && user.username === username) {
    return null;
  }

  const handleFollowToggle = async () => {
    if (!user) {
      navigate('/login');
      return;
    }

    if (loading) return;

    setLoading(true);
    try {
      if (isFollowing) {
        await followService.unfollowUser(username);
        setIsFollowing(false);
        if (onToggleSuccess) onToggleSuccess(false);
      } else {
        await followService.followUser(username);
        setIsFollowing(true);
        if (onToggleSuccess) onToggleSuccess(true);
      }
    } catch (error) {
      console.error("Failed to toggle follow status:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      onClick={handleFollowToggle}
      disabled={loading}
      className={`flex items-center space-x-2 px-4 py-2 rounded-xl text-sm font-semibold transition-all duration-200 shadow-md ${
        isFollowing
          ? 'bg-slate-800 hover:bg-red-950/40 text-slate-300 hover:text-red-400 border border-slate-700 hover:border-red-800/50'
          : 'bg-indigo-600 hover:bg-indigo-500 text-white shadow-indigo-600/25 hover:scale-105'
      }`}
    >
      {isFollowing ? (
        <>
          <UserCheck className="w-4 h-4 text-emerald-400" />
          <span>Following</span>
        </>
      ) : (
        <>
          <UserPlus className="w-4 h-4" />
          <span>Follow</span>
        </>
      )}
    </button>
  );
};

export default FollowButton;
