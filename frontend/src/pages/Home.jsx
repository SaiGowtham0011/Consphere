import React, { useState, useEffect } from 'react';
import { postService } from '../services/postService';
import { filterService } from '../services/filterService';
import SidebarLeft from '../components/SidebarLeft';
import SidebarRight from '../components/SidebarRight';
import PostCard from '../components/PostCard';
import CreateFilterModal from '../components/CreateFilterModal';
import { useAuth } from '../context/AuthContext';
import { Image as ImageIcon, Hash, Smile, Send } from 'lucide-react';
import './Home.css';

const Home = () => {
  const [posts, setPosts] = useState([]);
  const [activeFilter, setActiveFilter] = useState(null); // null = All Posts
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newCaption, setNewCaption] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const { user } = useAuth();

  useEffect(() => {
    fetchFeed();
  }, [activeFilter]);

  const fetchFeed = async () => {
    try {
      setLoading(true);
      if (activeFilter === null) {
        const feed = await postService.getHomeFeed();
        setPosts(feed);
      } else {
        const allFilters = await filterService.getAllFilters();
        const selectedObj = allFilters.find(f => f.name.toLowerCase() === activeFilter.toLowerCase());
        if (selectedObj) {
          const filterFeed = await filterService.getFilterFeed(selectedObj.id);
          setPosts(filterFeed);
        } else {
          const feed = await postService.getHomeFeed();
          setPosts(feed);
        }
      }
    } catch (err) {
      console.error("Failed to load feed:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreatePost = async (e) => {
    e.preventDefault();
    if (!newCaption.trim() || submitting) return;

    try {
      setSubmitting(true);
      const created = await postService.createPost({ caption: newCaption.trim() });
      setPosts([created, ...posts]);
      setNewCaption('');
    } catch (err) {
      console.error("Failed to publish post:", err);
    } finally {
      setSubmitting(false);
    }
  };

  const handlePostDeleted = (deletedId) => {
    setPosts(posts.filter(p => p.id !== deletedId));
  };

  return (
    <div className="home-page-container">
      <div className="home-grid-layout">
        
        {/* Left Sidebar */}
        <div className="home-left-col">
          <SidebarLeft
            activeFilter={activeFilter}
            onSelectFilter={(filterKey) => setActiveFilter(filterKey)}
            onCreateFilterClick={() => setIsModalOpen(true)}
          />
        </div>

        {/* Center Feed Column */}
        <main className="home-center-col">
          
          {/* Quick Post Card */}
          <div className="app-card quick-post-card">
            <div className="quick-post-top">
              {user && user.profilePicURL ? (
                <img
                  src={user.profilePicURL}
                  alt={user.username}
                  className="quick-post-avatar"
                />
              ) : (
                <div className="quick-post-avatar-fallback">
                  {user ? user.username.charAt(0).toUpperCase() : 'L'}
                </div>
              )}
              
              <input
                type="text"
                value={newCaption}
                onChange={(e) => setNewCaption(e.target.value)}
                placeholder={user ? `What's on your mind, ${user.firstName || user.username}?` : "What's on your mind, Lohith?"}
                className="quick-post-input"
              />
            </div>

            {/* Quick Action Tools */}
            <div className="quick-post-tools">
              <div className="quick-tools-left">
                <button className="tool-btn image">
                  <ImageIcon size={16} />
                  <span>Image</span>
                </button>

                <button className="tool-btn hashtag">
                  <Hash size={16} />
                  <span>Hashtag</span>
                </button>

                <button className="tool-btn feeling">
                  <Smile size={16} />
                  <span>Feeling</span>
                </button>
              </div>

              <button
                onClick={handleCreatePost}
                disabled={!newCaption.trim() || submitting}
                className="quick-post-submit-btn"
              >
                <span>Post</span>
                <Send size={12} />
              </button>
            </div>
          </div>

          {/* Feed Posts */}
          {loading ? (
            <div style={{ textAlign: 'center', padding: '48px 0', color: 'var(--text-muted)' }}>
              <span>Loading feed...</span>
            </div>
          ) : posts.length === 0 ? (
            <div className="app-card" style={{ padding: '48px', textAlign: 'center' }}>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.875rem', fontWeight: 600 }}>
                No posts available for this filter.
              </p>
            </div>
          ) : (
            posts.map(post => (
              <PostCard
                key={post.id}
                post={post}
                onDeleteSuccess={handlePostDeleted}
              />
            ))
          )}
        </main>

        {/* Right Sidebar */}
        <div className="home-right-col">
          <SidebarRight
            activeFilterName={activeFilter ? activeFilter : 'All Posts'}
            onChangeFilterClick={() => setActiveFilter(null)}
            onCreateFilterClick={() => setIsModalOpen(true)}
          />
        </div>

      </div>

      {/* Filter Modal */}
      <CreateFilterModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onFilterSaved={() => fetchFeed()}
      />
    </div>
  );
};

export default Home;
