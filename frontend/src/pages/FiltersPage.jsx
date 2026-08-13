import React, { useState, useEffect } from 'react';
import { filterService } from '../services/filterService';
import FilterCard from '../components/FilterCard';
import CreateFilterModal from '../components/CreateFilterModal';
import PostCard from '../components/PostCard';
import { useAuth } from '../context/AuthContext';
import { Sliders, Plus, ShieldCheck, Tag, ArrowLeft, Sparkles, Filter as FilterIcon, Compass } from 'lucide-react';
import './FiltersPage.css';

const FiltersPage = () => {
  const [filters, setFilters] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState(null);
  const [filterFeed, setFilterFeed] = useState([]);
  const [feedLoading, setFeedLoading] = useState(false);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingFilter, setEditingFilter] = useState(null);
  const [activeTab, setActiveTab] = useState('all'); // 'all', 'builtin', 'my'

  const { user } = useAuth();

  useEffect(() => {
    fetchFilters();
  }, []);

  const fetchFilters = async () => {
    try {
      setLoading(true);
      const data = await filterService.getAllFilters();
      setFilters(data);
    } catch (err) {
      console.error("Failed to load filters:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleSelectFilter = async (filter) => {
    setSelectedFilter(filter);
    try {
      setFeedLoading(true);
      const feed = await filterService.getFilterFeed(filter.id);
      setFilterFeed(feed);
    } catch (err) {
      console.error("Failed to load filter feed:", err);
      setFilterFeed([]);
    } finally {
      setFeedLoading(false);
    }
  };

  const handleCreateNew = () => {
    setEditingFilter(null);
    setIsModalOpen(true);
  };

  const handleEdit = (filter) => {
    setEditingFilter(filter);
    setIsModalOpen(true);
  };

  const handleDelete = async (filterId) => {
    if (!window.confirm("Are you sure you want to delete this custom filter?")) return;
    try {
      await filterService.deleteFilter(filterId);
      setFilters(filters.filter(f => f.id !== filterId));
      if (selectedFilter && selectedFilter.id === filterId) {
        setSelectedFilter(null);
        setFilterFeed([]);
      }
    } catch (err) {
      console.error("Failed to delete filter:", err);
    }
  };

  const handleFilterSaved = () => {
    fetchFilters();
  };

  const filteredList = filters.filter(f => {
    if (activeTab === 'builtin') return f.builtIn;
    if (activeTab === 'my') return !f.builtIn && user && f.ownerUsername === user.username;
    return true;
  });

  return (
    <div className="filters-page-container">
      
      {/* Header Banner */}
      <div className="filters-header-banner">
        <div>
          <div className="filters-title-group">
            <div className="filters-icon-badge">
              <Sliders size={22} />
            </div>
            <h1 className="filters-main-title">
              Content Filter Hub
            </h1>
          </div>
          <p className="filters-subtitle">
            Customize your feed by grouping between 3 and 10 hashtags into specialized environments.
          </p>
        </div>

        {user && (
          <button onClick={handleCreateNew} className="create-custom-filter-btn">
            <Plus size={16} />
            <span>Create Custom Filter</span>
          </button>
        )}
      </div>

      {/* Selected Filter View (Feed Inspector) */}
      {selectedFilter ? (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <div className="inspector-card">
            <div className="inspector-top">
              <button
                onClick={() => setSelectedFilter(null)}
                className="inspector-back-btn"
              >
                <ArrowLeft size={16} />
                <span>Back to All Filters</span>
              </button>

              {selectedFilter.builtIn ? (
                <span className="badge-builtin">Built-in Filter</span>
              ) : (
                <span className="badge-custom">Custom Filter by @{selectedFilter.ownerUsername}</span>
              )}
            </div>

            <h2 className="inspector-name">{selectedFilter.name}</h2>
            <p className="inspector-desc">{selectedFilter.description}</p>

            <div className="inspector-tags-row">
              <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', fontWeight: 700, marginRight: '8px' }}>
                Filter Hashtags:
              </span>
              {selectedFilter.hashtags.map((tag, idx) => (
                <span key={idx} className="filter-hashtag-chip">
                  <Tag size={12} style={{ color: 'var(--primary-purple)' }} />
                  <span>#{tag}</span>
                </span>
              ))}
            </div>
          </div>

          {/* Filter Feed Header */}
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px', fontSize: '1rem', fontWeight: 700, color: 'var(--text-main)' }}>
            <Compass size={20} style={{ color: 'var(--primary-purple)' }} />
            <span>Feed Results ({filterFeed.length} posts found)</span>
          </div>

          {feedLoading ? (
            <div style={{ textAlign: 'center', padding: '48px 0', color: 'var(--text-muted)' }}>
              <span>Fetching posts matching filter hashtags...</span>
            </div>
          ) : filterFeed.length === 0 ? (
            <div className="empty-state-card">
              <Sparkles size={36} style={{ margin: '0 auto 12px auto', opacity: 0.5 }} />
              <p style={{ fontWeight: 700, color: 'var(--text-main)', marginBottom: '4px' }}>No matching posts found</p>
              <p style={{ fontSize: '0.75rem' }}>There are currently no posts containing any of this filter's hashtags.</p>
            </div>
          ) : (
            <div style={{ maxWidth: '680px', margin: '0 auto', width: '100%', display: 'flex', flexDirection: 'column', gap: '16px' }}>
              {filterFeed.map(post => (
                <PostCard key={post.id} post={post} />
              ))}
            </div>
          )}
        </div>
      ) : (
        /* Filters Explorer Tabs & Grid */
        <div>
          {/* Tab Selector */}
          <div className="filters-tabs-row">
            <button
              onClick={() => setActiveTab('all')}
              className={`filter-tab-btn ${activeTab === 'all' ? 'active' : ''}`}
            >
              All Filters ({filters.length})
            </button>
            <button
              onClick={() => setActiveTab('builtin')}
              className={`filter-tab-btn ${activeTab === 'builtin' ? 'active' : ''}`}
            >
              Built-in Filters ({filters.filter(f => f.builtIn).length})
            </button>
            {user && (
              <button
                onClick={() => setActiveTab('my')}
                className={`filter-tab-btn ${activeTab === 'my' ? 'active' : ''}`}
              >
                My Custom Filters ({filters.filter(f => !f.builtIn && f.ownerUsername === user.username).length})
              </button>
            )}
          </div>

          {/* Filters Grid */}
          {loading ? (
            <div style={{ textAlign: 'center', padding: '64px 0', color: 'var(--text-muted)' }}>
              <span>Loading filters catalog...</span>
            </div>
          ) : filteredList.length === 0 ? (
            <div className="empty-state-card">
              <FilterIcon size={36} style={{ margin: '0 auto 12px auto', opacity: 0.5 }} />
              <p style={{ fontWeight: 700, color: 'var(--text-main)', marginBottom: '4px' }}>No filters found</p>
              <p style={{ fontSize: '0.75rem', marginBottom: '16px' }}>You haven't created any custom filters yet.</p>
              {user && (
                <button onClick={handleCreateNew} className="create-custom-filter-btn" style={{ margin: '0 auto' }}>
                  Create Your First Filter
                </button>
              )}
            </div>
          ) : (
            <div className="filters-grid">
              {filteredList.map(filter => (
                <FilterCard
                  key={filter.id}
                  filter={filter}
                  onSelectFilter={handleSelectFilter}
                  isSelected={selectedFilter?.id === filter.id}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                />
              ))}
            </div>
          )}
        </div>
      )}

      {/* Modal */}
      <CreateFilterModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onFilterSaved={handleFilterSaved}
        editFilter={editingFilter}
      />
    </div>
  );
};

export default FiltersPage;
