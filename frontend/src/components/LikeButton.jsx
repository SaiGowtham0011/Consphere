import React, { useState } from 'react';
import { Heart } from 'lucide-react';
import { likeService } from '../services/likeService';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const LikeButton = ({ postId, initialLiked = false, initialCount = 0 }) => {
  const [liked, setLiked] = useState(initialLiked);
  const [count, setCount] = useState(initialCount);
  const [loading, setLoading] = useState(false);
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleLikeToggle = async () => {
    if (!user) {
      navigate('/login');
      return;
    }

    if (loading) return;

    // Optimistic UI update
    const previousLiked = liked;
    const previousCount = count;
    setLiked(!previousLiked);
    setCount(previousLiked ? previousCount - 1 : previousCount + 1);
    setLoading(true);

    try {
      if (previousLiked) {
        const res = await likeService.unlikePost(postId);
        setLiked(res.liked);
        setCount(res.likeCount);
      } else {
        const res = await likeService.likePost(postId);
        setLiked(res.liked);
        setCount(res.likeCount);
      }
    } catch (error) {
      // Revert optimistic update on failure
      setLiked(previousLiked);
      setCount(previousCount);
      console.error("Failed to toggle like:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      onClick={handleLikeToggle}
      disabled={loading}
      className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-sm font-medium transition-all duration-200 ${
        liked
          ? 'bg-rose-500/15 text-rose-400 border border-rose-500/30'
          : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60 border border-transparent'
      }`}
    >
      <Heart
        className={`w-4 h-4 transition-transform duration-200 ${
          liked ? 'fill-rose-500 text-rose-500 scale-110' : 'text-slate-400'
        }`}
      />
      <span>{count}</span>
    </button>
  );
};

export default LikeButton;
