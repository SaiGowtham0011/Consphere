import React from 'react';
import { Sliders, Plus, Hash } from 'lucide-react';
import './SidebarRight.css';

const SidebarRight = ({ activeFilterName, onChangeFilterClick, onCreateFilterClick }) => {
  return (
    <aside className="sidebar-right">
      
      {/* Current Filter Card */}
      <div className="app-card" style={{ padding: '20px' }}>
        <div className="current-filter-header">
          <Sliders size={16} className="text-indigo-500" />
          <span>Current Filter</span>
        </div>

        <h3 className="current-filter-title">
          <Hash size={16} style={{ color: 'var(--primary-purple)' }} />
          <span>{activeFilterName || 'All Posts'}</span>
        </h3>

        <p className="current-filter-desc">
          {activeFilterName && activeFilterName !== 'All Posts'
            ? `Viewing posts matching the #${activeFilterName.toLowerCase()} filter hashtags.`
            : 'Viewing posts from all around Consphere.'}
        </p>

        <button onClick={onChangeFilterClick} className="change-filter-btn">
          Change Filter
        </button>
      </div>

      {/* Create your own Filter CTA Banner */}
      <div className="create-filter-cta">
        <h3 className="cta-title">Create your own Filter</h3>
        <p className="cta-desc">
          Build a custom content environment with your favorite hashtags.
        </p>

        <div className="cta-action-row">
          <button onClick={onCreateFilterClick} className="cta-btn">
            Create Filter
          </button>
          <div className="cta-plus-icon">
            <Plus size={20} />
          </div>
        </div>
      </div>

      {/* Footer Links */}
      <div className="footer-links-group">
        <div className="footer-nav-row">
          <a href="#">About</a>
          <a href="#">Help</a>
          <a href="#">Privacy</a>
          <a href="#">Terms</a>
          <a href="#">Contact</a>
        </div>
        <p>© 2024 Consphere. All rights reserved.</p>
      </div>

    </aside>
  );
};

export default SidebarRight;
