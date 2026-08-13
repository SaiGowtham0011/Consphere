import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';
import { 
  Home, Compass, User, Plus, Hash, Moon, Sun, MoreHorizontal, PlusCircle, LogOut
} from 'lucide-react';
import './SidebarLeft.css';

const SidebarLeft = ({ activeFilter, onSelectFilter, onCreateFilterClick }) => {
  const { user, logout } = useAuth();
  const { darkMode, toggleDarkMode } = useTheme();
  const location = useLocation();
  const navigate = useNavigate();

  const [showMenu, setShowMenu] = useState(false);

  const isActive = (path) => location.pathname === path;

  const handleLogout = () => {
    setShowMenu(false);
    logout();
    navigate('/login');
  };

  // Filter list items matching reference design
  const filterList = [
    { name: 'All Posts', key: null, color: 'text-indigo-400' },
    { name: 'Programming', key: 'Programming', color: 'tag-green' },
    { name: 'Education', key: 'Education', color: 'tag-cyan' },
    { name: 'Entertainment', key: 'Entertainment', color: 'tag-pink' },
    { name: 'Sports', key: 'Sports', color: 'tag-orange' },
    { name: 'Fitness', key: 'Fitness', color: 'tag-blue' },
    { name: 'Technology', key: 'Technology', color: 'tag-purple' },
    { name: 'Gaming', key: 'Gaming', color: 'tag-cyan' },
    { name: 'Music', key: 'Music', color: 'tag-violet' },
    { name: 'Movies', key: 'Movies', color: 'tag-amber' }
  ];

  return (
    <aside className="sidebar-left">
      
      {/* Navigation Menu Card */}
      <div className="app-card sidebar-nav-card">
        <Link
          to="/"
          className={`sidebar-nav-item ${isActive('/') ? 'active' : ''}`}
        >
          <Home size={20} />
          <span>Home</span>
        </Link>

        <Link
          to="/filters"
          className={`sidebar-nav-item ${isActive('/filters') ? 'active' : ''}`}
        >
          <Compass size={20} />
          <span>Explore</span>
        </Link>

        <Link
          to={user ? `/profile/${user.username}` : '/login'}
          className={`sidebar-nav-item ${user && isActive(`/profile/${user.username}`) ? 'active' : ''}`}
        >
          <User size={20} />
          <span>Profile</span>
        </Link>

        <Link
          to="/create-post"
          className={`sidebar-nav-item ${isActive('/create-post') ? 'active' : ''}`}
        >
          <PlusCircle size={20} />
          <span>Create Post</span>
        </Link>
      </div>

      {/* YOUR FILTERS Card */}
      <div className="app-card" style={{ padding: '16px' }}>
        <div className="sidebar-filters-header">
          <span className="sidebar-filters-title">YOUR FILTERS</span>
          <button
            onClick={onCreateFilterClick}
            className="sidebar-add-btn"
            title="Create Custom Filter"
          >
            <Plus size={16} />
          </button>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
          {filterList.map((item) => {
            const isSelected = activeFilter === item.key;
            return (
              <button
                key={item.name}
                onClick={() => onSelectFilter(item.key)}
                className={`filter-item-btn ${isSelected ? 'active' : ''}`}
              >
                <Hash size={14} className={isSelected ? '' : item.color} />
                <span>{item.name}</span>
              </button>
            );
          })}
        </div>

        <Link to="/filters" className="sidebar-see-all">
          See all filters
        </Link>
      </div>

      {/* Profile Card & Theme Toggle */}
      <div className="app-card profile-card">
        {user ? (
          <div className="profile-info-row">
            <div className="profile-details">
              {user.profilePicURL ? (
                <img
                  src={user.profilePicURL}
                  alt={user.username}
                  className="profile-avatar"
                />
              ) : (
                <div className="profile-avatar-fallback">
                  {user.username.charAt(0).toUpperCase()}
                </div>
              )}
              <div className="profile-name-group">
                <span className="profile-full-name">
                  {user.firstName ? `${user.firstName} ${user.lastName || ''}`.trim() : user.username}
                </span>
                <span className="profile-username">@{user.username}</span>
              </div>
            </div>

            {/* 3-Dots Button with Logout Dropdown Popup */}
            <div className="profile-menu-container">
              <button
                onClick={() => setShowMenu(!showMenu)}
                className="sidebar-add-btn"
                title="Options"
              >
                <MoreHorizontal size={18} />
              </button>

              {showMenu && (
                <div className="profile-dropdown-menu">
                  <button onClick={handleLogout} className="dropdown-item">
                    <LogOut size={16} />
                    <span>Log Out</span>
                  </button>
                </div>
              )}
            </div>
          </div>
        ) : (
          <div className="profile-info-row">
            <div className="profile-details">
              <div className="profile-avatar-fallback">L</div>
              <div className="profile-name-group">
                <span className="profile-full-name">Lohith Dev</span>
                <span className="profile-username">@lohith_dev</span>
              </div>
            </div>

            <div className="profile-menu-container">
              <button
                onClick={() => setShowMenu(!showMenu)}
                className="sidebar-add-btn"
                title="Options"
              >
                <MoreHorizontal size={18} />
              </button>

              {showMenu && (
                <div className="profile-dropdown-menu">
                  <button onClick={() => navigate('/login')} className="dropdown-item" style={{ color: 'var(--primary-purple)' }}>
                    <User size={16} />
                    <span>Log In</span>
                  </button>
                </div>
              )}
            </div>
          </div>
        )}

        {/* Dark Mode Switch */}
        <div className="theme-toggle-row">
          <div className="theme-label">
            {darkMode ? <Moon size={16} /> : <Sun size={16} />}
            <span>Dark Mode</span>
          </div>

          <button
            onClick={toggleDarkMode}
            className={`theme-switch ${darkMode ? 'dark' : 'light'}`}
            title="Toggle Dark / Light Theme"
          >
            <div className="theme-switch-thumb" />
          </button>
        </div>
      </div>

    </aside>
  );
};

export default SidebarLeft;
