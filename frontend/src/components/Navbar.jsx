import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Search, Home, Compass, User as UserIcon } from 'lucide-react';
import './Navbar.css';

const Navbar = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [searchQuery, setSearchQuery] = useState('');

  const isActive = (path) => location.pathname === path;

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/filters?search=${encodeURIComponent(searchQuery.trim())}`);
    }
  };

  return (
    <header className="navbar-header">
      <div className="navbar-container">
        
        {/* Brand Logo & Title */}
        <Link to="/" className="navbar-logo">
          <img
            src="/consphere_logo.png"
            alt="Consphere Logo"
            className="navbar-logo-img"
            onError={(e) => {
              e.target.onerror = null;
              e.target.style.display = 'none';
            }}
          />
          <span className="navbar-brand-title">CONSPHERE</span>
        </Link>

        {/* Centered Search Bar */}
        <form onSubmit={handleSearchSubmit} className="navbar-search-form">
          <div className="navbar-search-wrapper">
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="Search people, posts or #hashtags..."
              className="navbar-search-input"
            />
            <button type="submit" className="navbar-search-btn">
              <Search size={16} />
            </button>
          </div>
        </form>

        {/* Right Navigation Actions */}
        <div className="navbar-actions">
          <Link
            to="/"
            className={`nav-icon-btn ${isActive('/') ? 'active' : ''}`}
            title="Home"
          >
            <Home size={20} />
          </Link>

          <Link
            to="/filters"
            className={`nav-icon-btn ${isActive('/filters') ? 'active' : ''}`}
            title="Explore Filters"
          >
            <Compass size={20} />
          </Link>

          {/* User Profile Avatar */}
          {user ? (
            <Link to={`/profile/${user.username}`}>
              {user.profilePicURL ? (
                <img
                  src={user.profilePicURL}
                  alt={user.username}
                  className="nav-user-avatar"
                />
              ) : (
                <div className="nav-user-initial">
                  {user.username.charAt(0).toUpperCase()}
                </div>
              )}
            </Link>
          ) : (
            <Link to="/login" className="nav-icon-btn" title="Sign In">
              <UserIcon size={20} />
            </Link>
          )}
        </div>
      </div>
    </header>
  );
};

export default Navbar;
