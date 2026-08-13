import React from 'react';
import { Sliders, ShieldCheck, Tag, Trash2, Edit3, ArrowRight } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import './FilterCard.css';

const FilterCard = ({ filter, onSelectFilter, isSelected, onEdit, onDelete }) => {
  const { user } = useAuth();
  const isOwner = user && filter.ownerUsername === user.username;

  return (
    <div
      className={`filter-card ${isSelected ? 'selected' : ''}`}
      onClick={() => onSelectFilter(filter)}
    >
      <div>
        <div className="filter-card-header">
          <div className="filter-title-group">
            <div className={`filter-icon-box ${filter.builtIn ? 'builtin' : ''}`}>
              <Sliders size={18} />
            </div>
            <div className="filter-name-area">
              <div className="filter-name-row">
                <span className="filter-card-name">{filter.name}</span>
                {filter.builtIn ? (
                  <span className="badge-builtin">Built-in</span>
                ) : (
                  <span className="badge-custom">Custom</span>
                )}
              </div>
              <span className="filter-card-owner">
                By {filter.builtIn ? 'System' : filter.ownerUsername}
              </span>
            </div>
          </div>

          {!filter.builtIn && isOwner && (
            <div className="filter-card-actions" onClick={(e) => e.stopPropagation()}>
              {onEdit && (
                <button
                  onClick={() => onEdit(filter)}
                  className="filter-action-btn"
                  title="Edit Filter"
                >
                  <Edit3 size={14} />
                </button>
              )}
              {onDelete && (
                <button
                  onClick={() => onDelete(filter.id)}
                  className="filter-action-btn delete"
                  title="Delete Filter"
                >
                  <Trash2 size={14} />
                </button>
              )}
            </div>
          )}
        </div>

        {filter.description && (
          <p className="filter-card-desc">
            {filter.description}
          </p>
        )}

        {/* Hashtag List */}
        <div className="filter-hashtags-list">
          {filter.hashtags.map((tag, idx) => (
            <span key={idx} className="filter-hashtag-chip">
              <Tag size={12} style={{ color: 'var(--primary-purple)' }} />
              <span>#{tag}</span>
            </span>
          ))}
        </div>
      </div>

      <div className="filter-card-footer">
        <span>View Filter Feed</span>
        <ArrowRight size={16} />
      </div>
    </div>
  );
};

export default FilterCard;
