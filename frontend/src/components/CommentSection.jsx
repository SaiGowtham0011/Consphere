import React, { useState, useEffect } from 'react';
import { commentService } from '../services/commentService';
import { useAuth } from '../context/AuthContext';
import { Send, Trash2, MessageSquare, AlertCircle } from 'lucide-react';
import { Link } from 'react-router-dom';
import './CommentSection.css';

const CommentSection = ({ postId, onCommentCountChange }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const { user } = useAuth();

  useEffect(() => {
    fetchComments();
  }, [postId]);

  const fetchComments = async () => {
    try {
      setLoading(true);
      const data = await commentService.getCommentsByPost(postId);
      setComments(data);
    } catch (err) {
      console.error("Failed to load comments:", err);
      setError("Unable to load comments.");
    } finally {
      setLoading(false);
    }
  };

  const handleAddComment = async (e) => {
    e.preventDefault();
    if (!newComment.trim() || submitting) return;

    try {
      setSubmitting(true);
      setError(null);
      const created = await commentService.addComment(postId, newComment.trim());
      const updatedComments = [...comments, created];
      setComments(updatedComments);
      setNewComment('');
      if (onCommentCountChange) {
        onCommentCountChange(updatedComments.length);
      }
    } catch (err) {
      setError(err.response?.data?.message || "Failed to post comment.");
    } finally {
      setSubmitting(false);
    }
  };

  const handleDeleteComment = async (commentId) => {
    try {
      await commentService.deleteComment(commentId);
      const updatedComments = comments.filter(c => c.id !== commentId);
      setComments(updatedComments);
      if (onCommentCountChange) {
        onCommentCountChange(updatedComments.length);
      }
    } catch (err) {
      console.error("Failed to delete comment:", err);
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString(undefined, {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="mt-4 pt-4 border-t border-slate-800/80 space-y-4">
      {/* Add Comment Form */}
      {user ? (
        <form onSubmit={handleAddComment} className="flex items-center space-x-2">
          <input
            type="text"
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Add a comment..."
            className="flex-1 bg-slate-900/80 border border-slate-800 focus:border-indigo-500 rounded-xl px-4 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none transition-all"
          />
          <button
            type="submit"
            disabled={!newComment.trim() || submitting}
            className="p-2.5 bg-indigo-600 hover:bg-indigo-500 disabled:bg-slate-800 text-white rounded-xl shadow-lg transition-all disabled:opacity-50"
          >
            <Send className="w-4 h-4" />
          </button>
        </form>
      ) : (
        <p className="text-xs text-slate-400 bg-slate-900/50 p-3 rounded-xl border border-slate-800 text-center">
          Please <Link to="/login" className="text-indigo-400 hover:underline font-semibold">login</Link> to join the conversation.
        </p>
      )}

      {error && (
        <div className="flex items-center space-x-2 text-xs text-red-400 bg-red-950/30 p-2.5 rounded-lg border border-red-900/40">
          <AlertCircle className="w-3.5 h-3.5 flex-shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {/* Comments List */}
      <div className="space-y-3 max-h-60 overflow-y-auto pr-1">
        {loading ? (
          <p className="text-xs text-slate-500 text-center py-2">Loading comments...</p>
        ) : comments.length === 0 ? (
          <p className="text-xs text-slate-500 text-center py-2">No comments yet. Be the first to comment!</p>
        ) : (
          comments.map((comment) => (
            <div key={comment.id} className="flex items-start justify-between bg-slate-900/40 p-3 rounded-xl border border-slate-800/50 group">
              <div className="flex items-start space-x-3">
                <Link to={`/profile/${comment.username}`}>
                  {comment.profilePicURL ? (
                    <img
                      src={comment.profilePicURL}
                      alt={comment.username}
                      className="w-7 h-7 rounded-full object-cover border border-slate-700"
                    />
                  ) : (
                    <div className="w-7 h-7 rounded-full bg-indigo-950 text-indigo-300 border border-indigo-800/50 flex items-center justify-center text-xs font-bold">
                      {comment.username.charAt(0).toUpperCase()}
                    </div>
                  )}
                </Link>
                <div>
                  <div className="flex items-center space-x-2">
                    <Link to={`/profile/${comment.username}`} className="text-xs font-semibold text-slate-200 hover:text-indigo-400 transition-colors">
                      {comment.username}
                    </Link>
                    <span className="text-[10px] text-slate-500">{formatDate(comment.createdAt)}</span>
                  </div>
                  <p className="text-xs text-slate-300 mt-1 leading-relaxed">{comment.content}</p>
                </div>
              </div>

              {user && user.username === comment.username && (
                <button
                  onClick={() => handleDeleteComment(comment.id)}
                  className="opacity-0 group-hover:opacity-100 text-slate-500 hover:text-red-400 p-1 rounded transition-all"
                  title="Delete Comment"
                >
                  <Trash2 className="w-3.5 h-3.5" />
                </button>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default CommentSection;
