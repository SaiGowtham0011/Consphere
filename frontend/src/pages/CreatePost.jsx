import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postService } from '../services/postService';
import { PlusSquare, Image as ImageIcon, Tag, AlertCircle, Sparkles, ArrowLeft } from 'lucide-react';
import './FormPage.css';

const CreatePost = () => {
  const [caption, setCaption] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Extract detected hashtags from current caption for interactive preview
  const detectedHashtags = (caption.match(/#[a-zA-Z0-9_]+/g) || []).map(tag => tag.toLowerCase());

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!caption.trim() && !imageUrl.trim()) {
      setError('Please provide a caption or an image URL.');
      return;
    }

    setLoading(true);
    try {
      await postService.createPost({
        caption: caption.trim(),
        imageUrl: imageUrl.trim() || null
      });
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create post.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-page-container">
      <button onClick={() => navigate(-1)} className="back-btn">
        <ArrowLeft size={16} />
        <span>Back to feed</span>
      </button>

      <div className="app-card form-page-card">
        <div className="form-page-header">
          <div className="nav-user-initial" style={{ width: '40px', height: '40px' }}>
            <PlusSquare size={20} />
          </div>
          <div>
            <h1 className="form-page-title">Create New Post</h1>
            <p className="form-page-subtitle">Share thoughts and automatic hashtags with the community</p>
          </div>
        </div>

        {error && (
          <div style={{ marginBottom: '16px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(239,68,68,0.1)', color: '#ef4444', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <AlertCircle size={16} />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label className="auth-label">Caption</label>
            <textarea
              value={caption}
              onChange={(e) => setCaption(e.target.value)}
              placeholder="What's on your mind? Use hashtags like #java, #coding, #football..."
              rows={4}
              className="auth-input"
              style={{ resize: 'none' }}
            />
          </div>

          {detectedHashtags.length > 0 && (
            <div style={{ backgroundColor: 'var(--input-bg)', padding: '12px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '6px', fontSize: '0.75rem', color: 'var(--primary-purple)', fontWeight: '700', marginBottom: '8px' }}>
                <Sparkles size={14} />
                <span>Detected Hashtags ({detectedHashtags.length}):</span>
              </div>
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: '6px' }}>
                {detectedHashtags.map((tag, idx) => (
                  <span key={idx} className="chip-tag">
                    <Tag size={12} />
                    <span>{tag}</span>
                  </span>
                ))}
              </div>
            </div>
          )}

          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label className="auth-label">Image URL (Optional)</label>
            <input
              type="url"
              value={imageUrl}
              onChange={(e) => setImageUrl(e.target.value)}
              placeholder="https://images.unsplash.com/photo-..."
              className="auth-input"
            />
          </div>

          {imageUrl.trim() && (
            <div style={{ borderRadius: '12px', overflow: 'hidden', border: '1px solid var(--border-color)', padding: '8px' }}>
              <p style={{ fontSize: '0.75rem', fontWeight: 600, color: 'var(--text-muted)', marginBottom: '6px' }}>Image Preview:</p>
              <img
                src={imageUrl}
                alt="Preview"
                style={{ width: '100%', maxHeight: '256px', objectFit: 'cover', borderRadius: '8px' }}
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = 'https://via.placeholder.com/600x300?text=Invalid+Image+URL';
                }}
              />
            </div>
          )}

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px', paddingTop: '12px' }}>
            <button type="button" onClick={() => navigate('/')} className="btn-secondary">
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading || (!caption.trim() && !imageUrl.trim())}
              className="btn-primary"
            >
              {loading ? 'Publishing...' : 'Publish Post'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreatePost;
