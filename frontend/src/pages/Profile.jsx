import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { authService } from '../services/authService';
import { useAuth } from '../context/AuthContext';
import PostCard from '../components/PostCard';
import FollowButton from '../components/FollowButton';
import { User, Settings, Grid, Sparkles } from 'lucide-react';
import './Profile.css';

const Profile = () => {
  const { username } = useParams();
  const { user: currentUser } = useAuth();

  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProfile();
  }, [username]);

  const fetchProfile = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await authService.getUserByUsername(username);
      setProfile(data);
    } catch (err) {
      console.error("Failed to fetch profile:", err);
      setError("User profile not found.");
    } finally {
      setLoading(false);
    }
  };

  const handlePostDeleted = (postId) => {
    if (profile) {
      setProfile({
        ...profile,
        postsCount: Math.max(0, profile.postsCount - 1),
        posts: profile.posts.filter(p => p.id !== postId)
      });
    }
  };

  const handleFollowToggle = (isFollowingNow) => {
    if (profile) {
      setProfile({
        ...profile,
        isFollowing: isFollowingNow,
        followersCount: isFollowingNow ? profile.followersCount + 1 : Math.max(0, profile.followersCount - 1)
      });
    }
  };

  if (loading) {
    return (
      <div style={{ textAlign: 'center', padding: '64px 0', color: 'var(--text-muted)' }}>
        <span>Loading user profile...</span>
      </div>
    );
  }

  if (error || !profile) {
    return (
      <div className="profile-page-container">
        <div className="app-card" style={{ padding: '48px', textAlign: 'center', maxWidth: '400px', margin: '0 auto' }}>
          <User size={48} style={{ margin: '0 auto 12px auto', color: 'var(--text-muted)' }} />
          <h2 style={{ fontSize: '1.25rem', fontWeight: 700, color: 'var(--text-main)', marginBottom: '4px' }}>User Not Found</h2>
          <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginBottom: '20px' }}>The user @{username} does not exist or has been removed.</p>
          <Link to="/" className="btn-primary" style={{ display: 'inline-block', textDecoration: 'none' }}>
            Return to Feed
          </Link>
        </div>
      </div>
    );
  }

  const isOwnProfile = currentUser && currentUser.username === profile.username;

  return (
    <div className="profile-page-container">
      {/* Header Profile Banner */}
      <div className="profile-header-card">
        <div className="profile-top-layout">
          
          {/* Avatar */}
          {profile.profilePicURL ? (
            <img
              src={profile.profilePicURL}
              alt={profile.username}
              className="profile-avatar-xl"
            />
          ) : (
            <div className="profile-avatar-fallback-xl">
              {profile.username.charAt(0).toUpperCase()}
            </div>
          )}

          {/* Details */}
          <div className="profile-main-details">
            <div className="profile-name-action-row">
              <div>
                <h1 className="profile-display-name">
                  {profile.firstName ? `${profile.firstName} ${profile.lastName || ''}`.trim() : profile.username}
                </h1>
                <p className="profile-handle">@{profile.username}</p>
              </div>

              {/* Action Buttons */}
              <div style={{ display: 'flex', gap: '8px' }}>
                {isOwnProfile ? (
                  <Link to="/edit-profile" className="edit-profile-btn">
                    <Settings size={14} />
                    <span>Edit Profile</span>
                  </Link>
                ) : (
                  <FollowButton
                    username={profile.username}
                    initialFollowing={profile.isFollowing}
                    onToggleSuccess={handleFollowToggle}
                  />
                )}
              </div>
            </div>

            {/* Stats Row */}
            <div className="profile-stats-bar">
              <div className="stat-box">
                <span className="stat-number">{profile.postsCount}</span>
                <span className="stat-text">Posts</span>
              </div>
              <div className="stat-box">
                <span className="stat-number">{profile.followersCount}</span>
                <span className="stat-text">Followers</span>
              </div>
              <div className="stat-box">
                <span className="stat-number">{profile.followingCount}</span>
                <span className="stat-text">Following</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* User Posts Section */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div className="profile-posts-title">
          <Grid size={18} style={{ color: 'var(--primary-purple)' }} />
          <span>Posts by @{profile.username}</span>
        </div>

        {profile.posts.length === 0 ? (
          <div className="app-card" style={{ padding: '48px', textAlign: 'center' }}>
            <Sparkles size={36} style={{ margin: '0 auto 12px auto', opacity: 0.5 }} />
            <p style={{ color: 'var(--text-muted)', fontSize: '0.875rem', fontWeight: 600 }}>No posts published yet.</p>
          </div>
        ) : (
          profile.posts.map((post) => (
            <PostCard
              key={post.id}
              post={post}
              onDeleteSuccess={handlePostDeleted}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default Profile;
