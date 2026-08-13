import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import { useAuth } from '../context/AuthContext';
import { User, Camera, ArrowLeft, Save, AlertCircle, CheckCircle } from 'lucide-react';
import './FormPage.css';

const EditProfile = () => {
  const { user, updateUserState } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    mobileNumber: '',
    profilePicURL: ''
  });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      setLoading(true);
      const data = await authService.fetchUserProfile();
      setFormData({
        firstName: data.firstName || '',
        lastName: data.lastName || '',
        mobileNumber: data.mobileNumber || '',
        profilePicURL: data.profilePicURL || ''
      });
    } catch (err) {
      console.error("Failed to load user profile:", err);
      setError("Unable to load profile information.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess(false);

    setSaving(true);
    try {
      const updated = await authService.updateUserProfile(formData);
      updateUserState(updated);
      setSuccess(true);
      setTimeout(() => {
        if (user) {
          navigate(`/profile/${user.username}`);
        } else {
          navigate('/');
        }
      }, 1200);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update profile.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div style={{ padding: '48px', textAlign: 'center', color: 'var(--text-muted)' }}>
        Loading profile configuration...
      </div>
    );
  }

  return (
    <div className="form-page-container">
      <button onClick={() => navigate(-1)} className="back-btn">
        <ArrowLeft size={16} />
        <span>Back to profile</span>
      </button>

      <div className="app-card form-page-card">
        <div className="form-page-header">
          <div className="nav-user-initial" style={{ width: '40px', height: '40px' }}>
            <User size={20} />
          </div>
          <div>
            <h1 className="form-page-title">Edit Profile</h1>
            <p className="form-page-subtitle">Update your account information and avatar</p>
          </div>
        </div>

        {error && (
          <div style={{ marginBottom: '16px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(239,68,68,0.1)', color: '#ef4444', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <AlertCircle size={16} />
            <span>{error}</span>
          </div>
        )}

        {success && (
          <div style={{ marginBottom: '16px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(16,185,129,0.1)', color: '#10b981', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <CheckCircle size={16} />
            <span>Profile updated successfully! Redirecting...</span>
          </div>
        )}

        {/* Profile Picture Live Preview */}
        <div style={{ display: 'flex', justifyCenter: 'center', marginBottom: '24px' }}>
          <div style={{ position: 'relative', margin: '0 auto' }}>
            {formData.profilePicURL ? (
              <img
                src={formData.profilePicURL}
                alt="Avatar preview"
                className="profile-avatar"
                style={{ width: '96px', height: '96px', border: '2px solid var(--primary-purple)' }}
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = 'https://via.placeholder.com/150?text=Invalid';
                }}
              />
            ) : (
              <div className="profile-avatar-fallback" style={{ width: '96px', height: '96px', fontSize: '2rem' }}>
                {user?.username?.charAt(0).toUpperCase() || 'U'}
              </div>
            )}
          </div>
        </div>

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
              <label className="auth-label">First Name</label>
              <input
                type="text"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                placeholder="John"
                className="auth-input"
              />
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
              <label className="auth-label">Last Name</label>
              <input
                type="text"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                placeholder="Doe"
                className="auth-input"
              />
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label className="auth-label">Profile Picture URL</label>
            <input
              type="url"
              name="profilePicURL"
              value={formData.profilePicURL}
              onChange={handleChange}
              placeholder="https://images.unsplash.com/photo-..."
              className="auth-input"
            />
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            <label className="auth-label">Mobile Number</label>
            <input
              type="text"
              name="mobileNumber"
              value={formData.mobileNumber}
              onChange={handleChange}
              placeholder="+1 234 567 8900"
              className="auth-input"
            />
          </div>

          <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px', paddingTop: '12px' }}>
            <button type="button" onClick={() => navigate(-1)} className="btn-secondary">
              Cancel
            </button>
            <button type="submit" disabled={saving} className="btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Save size={16} />
              <span>{saving ? 'Saving...' : 'Save Changes'}</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditProfile;
