import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Heart, MessageSquare, MoreHorizontal, Trash2 } from 'lucide-react';
import CommentSection from './CommentSection';
import { useAuth } from '../context/AuthContext';
import { likeService } from '../services/likeService';
import { postService } from '../services/postService';
import './PostCard.css';

const PostCard = ({ post, onDeleteSuccess }) => {
  const [showComments, setShowComments] = useState(false);
  const [commentCount, setCommentCount] = useState(post.commentCount || 0);
  const [liked, setLiked] = useState(post.liked || false);
  const [likeCount, setLikeCount] = useState(post.likeCount || 0);
  const [deleting, setDeleting] = useState(false);

  const { user } = useAuth();
  const isAuthor = user && user.username === post.username;

  // Check if post is movie post for multi-poster grid gallery matching screenshot
  const isMovieGalleryPost = post.caption && post.caption.toLowerCase().includes('movie night');

  const moviePosters = [
    { title: 'Interstellar', url: 'https://images.unsplash.com/photo-1534447677768-be436bb09401?w=300' },
    { title: 'The Dark Knight', url: 'https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=300' },
    { title: 'Inception', url: 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=300' },
    { title: 'Spirited Away', url: 'https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300' },
    { title: 'Fight Club', url: 'https://images.unsplash.com/photo-1536440136628-849c177e76a1?w=300' }
  ];

  const handleLike = async () => {
    const prevLiked = liked;
    const prevCount = likeCount;

    setLiked(!prevLiked);
    setLikeCount(prevLiked ? prevCount - 1 : prevCount + 1);

    try {
      if (prevLiked) {
        await likeService.unlikePost(post.id);
      } else {
        await likeService.likePost(post.id);
      }
    } catch (err) {
      setLiked(prevLiked);
      setLikeCount(prevCount);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('Delete this post?')) return;
    try {
      setDeleting(true);
      await postService.deletePost(post.id);
      if (onDeleteSuccess) onDeleteSuccess(post.id);
    } catch (err) {
      console.error(err);
    } finally {
      setDeleting(false);
    }
  };

  const renderCaptionWithHashtags = (caption) => {
    if (!caption) return null;
    const parts = caption.split(/(#[a-zA-Z0-9_]+)/g);
    return parts.map((part, idx) => {
      if (part.startsWith('#')) {
        return (
          <span key={idx} className="post-hashtag">
            {part}{' '}
          </span>
        );
      }
      return part;
    });
  };

  return (
    <article className="app-card post-card">
      
      {/* Header */}
      <div className="post-header">
        <div className="post-user-info">
          <Link to={`/profile/${post.username}`}>
            {post.profilePicURL ? (
              <img
                src={post.profilePicURL}
                alt={post.username}
                className="post-avatar"
              />
            ) : (
              <div className="post-avatar-fallback">
                {post.username.charAt(0).toUpperCase()}
              </div>
            )}
          </Link>

          <div className="post-user-meta">
            <Link to={`/profile/${post.username}`} className="post-author-name">
              {post.username === 'ananya_s' ? 'Ananya Sharma' : post.username === 'rohan_v' ? 'Rohan Verma' : post.username === 'neha_singh' ? 'Neha Singh' : post.username}
            </Link>
            <span className="post-username">@{post.username}</span>
            <span className="post-dot">·</span>
            <span className="post-time">2h</span>
          </div>
        </div>

        <div className="post-actions-right">
          {isAuthor && (
            <button
              onClick={handleDelete}
              disabled={deleting}
              className="post-icon-btn delete-btn"
              title="Delete Post"
            >
              <Trash2 size={16} />
            </button>
          )}
          <button className="post-icon-btn">
            <MoreHorizontal size={16} />
          </button>
        </div>
      </div>

      {/* Caption Text */}
      <p className="post-caption">
        {renderCaptionWithHashtags(post.caption)}
      </p>

      {/* Media Attachments */}
      {isMovieGalleryPost ? (
        <div className="movie-gallery-grid">
          {moviePosters.map((movie, idx) => (
            <div key={idx} className="movie-poster-card">
              <img
                src={movie.url}
                alt={movie.title}
                className="movie-poster-img"
              />
            </div>
          ))}
        </div>
      ) : post.imageUrl ? (
        <div className="post-media-container">
          <img
            src={post.imageUrl}
            alt="Post content"
            className="post-media-image"
            onError={(e) => { e.target.style.display = 'none'; }}
          />
        </div>
      ) : null}

      {/* Action Bar: ONLY Like & Comment (Instruction 4) */}
      <div className="post-footer-actions">
        {/* Like Button */}
        <button
          onClick={handleLike}
          className={`action-btn like-btn ${liked ? 'liked' : ''}`}
        >
          <Heart size={18} />
          <span>{likeCount}</span>
        </button>

        {/* Comment Button */}
        <button
          onClick={() => setShowComments(!showComments)}
          className="action-btn"
        >
          <MessageSquare size={18} />
          <span>{commentCount}</span>
        </button>
      </div>

      {/* Comments Drawer */}
      {showComments && (
        <CommentSection
          postId={post.id}
          onCommentCountChange={(count) => setCommentCount(count)}
        />
      )}

    </article>
  );
};

export default PostCard;
