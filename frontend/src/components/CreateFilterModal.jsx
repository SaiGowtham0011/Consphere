import React, { useState, useEffect } from 'react';
import { X, Plus, Tag, AlertCircle, Sliders } from 'lucide-react';
import { filterService } from '../services/filterService';
import './CreateFilterModal.css';

const CreateFilterModal = ({ isOpen, onClose, onFilterSaved, editFilter = null }) => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [hashtagInput, setHashtagInput] = useState('');
  const [hashtags, setHashtags] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (editFilter) {
      setName(editFilter.name || '');
      setDescription(editFilter.description || '');
      setHashtags(editFilter.hashtags || []);
    } else {
      setName('');
      setDescription('');
      setHashtags([]);
    }
    setError('');
  }, [editFilter, isOpen]);

  if (!isOpen) return null;

  const handleAddHashtag = (e) => {
    e.preventDefault();
    if (!hashtagInput.trim()) return;

    const tag = hashtagInput.replace(/^#+/, '').trim().toLowerCase();
    if (!tag) return;

    if (hashtags.includes(tag)) {
      setError(`Hashtag #${tag} is already added.`);
      return;
    }

    if (hashtags.length >= 10) {
      setError('You can add a maximum of 10 hashtags.');
      return;
    }

    setHashtags([...hashtags, tag]);
    setHashtagInput('');
    setError('');
  };

  const handleRemoveHashtag = (tagToRemove) => {
    setHashtags(hashtags.filter(t => t !== tagToRemove));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!name.trim()) {
      setError('Filter name is required.');
      return;
    }

    if (hashtags.length < 3 || hashtags.length > 10) {
      setError('A filter must contain between 3 and 10 hashtags.');
      return;
    }

    setLoading(true);
    try {
      const payload = {
        name: name.trim(),
        description: description.trim(),
        hashtags: hashtags
      };

      let result;
      if (editFilter) {
        result = await filterService.updateFilter(editFilter.id, payload);
      } else {
        result = await filterService.createFilter(payload);
      }

      onFilterSaved(result);
      onClose();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save filter.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-card">
        
        {/* Header */}
        <div className="modal-header">
          <div className="modal-title-group">
            <Sliders size={20} className="text-indigo-500" />
            <h2 className="modal-title">
              {editFilter ? 'Edit Custom Filter' : 'Create Custom Filter'}
            </h2>
          </div>
          <button onClick={onClose} className="modal-close-btn">
            <X size={20} />
          </button>
        </div>

        {/* Error Alert */}
        {error && (
          <div style={{ marginTop: '12px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(239,68,68,0.1)', color: '#ef4444', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <AlertCircle size={16} />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="modal-form">
          <div className="form-field">
            <label className="form-label">Filter Name *</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="e.g. Placement Prep & Java"
              className="form-input"
              required
            />
          </div>

          <div className="form-field">
            <label className="form-label">Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Describe what kind of posts this filter aggregates..."
              rows={2}
              className="form-input"
              style={{ resize: 'none' }}
            />
          </div>

          <div className="form-field">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <label className="form-label">Hashtags (3 to 10) *</label>
              <span style={{ fontSize: '0.75rem', color: 'var(--primary-purple)', fontWeight: '700' }}>
                {hashtags.length}/10
              </span>
            </div>

            <div className="hashtag-row">
              <input
                type="text"
                value={hashtagInput}
                onChange={(e) => setHashtagInput(e.target.value)}
                onKeyDown={(e) => { if (e.key === 'Enter') { e.preventDefault(); handleAddHashtag(e); } }}
                placeholder="# e.g. java"
                className="form-input"
              />
              <button type="button" onClick={handleAddHashtag} className="btn-primary" style={{ padding: '8px 16px', fontSize: '0.75rem' }}>
                <Plus size={14} /> Add
              </button>
            </div>

            <div className="hashtag-chips-container">
              {hashtags.length === 0 ? (
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', margin: 'auto' }}>
                  No hashtags added yet. Enter a hashtag above and click Add.
                </span>
              ) : (
                hashtags.map((tag) => (
                  <span key={tag} className="chip-tag">
                    <Tag size={12} />
                    <span>#{tag}</span>
                    <button
                      type="button"
                      onClick={() => handleRemoveHashtag(tag)}
                      style={{ background: 'none', border: 'none', color: 'inherit', cursor: 'pointer', marginLeft: '4px' }}
                    >
                      &times;
                    </button>
                  </span>
                ))
              )}
            </div>
          </div>

          <div className="modal-footer">
            <button type="button" onClick={onClose} className="btn-secondary">
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading || hashtags.length < 3 || hashtags.length > 10}
              className="btn-primary"
            >
              {loading ? 'Saving...' : editFilter ? 'Update Filter' : 'Create Filter'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateFilterModal;
