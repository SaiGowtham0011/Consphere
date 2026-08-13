import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Sparkles, UserPlus, AlertCircle, CheckCircle } from 'lucide-react';
import './Auth.css';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    mobileNumber: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    if (formData.password.length < 6) {
      setError('Password must be at least 6 characters long.');
      return;
    }

    setLoading(true);
    try {
      await register(formData);
      setSuccess(true);
      setTimeout(() => {
        navigate('/login');
      }, 1500);
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please check your inputs.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page-container">
      <div className="auth-box" style={{ maxWidth: '520px' }}>
        
        {/* Header Branding */}
        <div className="auth-header">
          <div className="auth-icon-badge">
            <Sparkles size={28} />
          </div>
          <h1 className="auth-title">Create your CONSPHERE account</h1>
          <p className="auth-subtitle">Join the custom content filter social platform</p>
        </div>

        {/* Register Card */}
        <div className="app-card auth-card">
          {error && (
            <div style={{ marginBottom: '16px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(239,68,68,0.1)', color: '#ef4444', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
              <AlertCircle size={16} />
              <span>{error}</span>
            </div>
          )}

          {success && (
            <div style={{ marginBottom: '16px', padding: '10px 14px', borderRadius: '10px', backgroundColor: 'rgba(16,185,129,0.1)', color: '#10b981', fontSize: '0.8125rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
              <CheckCircle size={16} />
              <span>Account created successfully! Redirecting to login...</span>
            </div>
          )}

          <form onSubmit={handleSubmit} className="auth-form">
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
              <div className="auth-field">
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
              <div className="auth-field">
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

            <div className="auth-field">
              <label className="auth-label">Username *</label>
              <input
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                placeholder="johndoe"
                className="auth-input"
                required
              />
            </div>

            <div className="auth-field">
              <label className="auth-label">Email Address *</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="john@example.com"
                className="auth-input"
                required
              />
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
              <div className="auth-field">
                <label className="auth-label">Password *</label>
                <input
                  type="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  placeholder="••••••••"
                  className="auth-input"
                  required
                />
              </div>
              <div className="auth-field">
                <label className="auth-label">Confirm Password *</label>
                <input
                  type="password"
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  placeholder="••••••••"
                  className="auth-input"
                  required
                />
              </div>
            </div>

            <div className="auth-field">
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

            <button type="submit" disabled={loading || success} className="auth-submit-btn">
              {loading ? (
                <span>Creating account...</span>
              ) : (
                <>
                  <UserPlus size={18} />
                  <span>Register Account</span>
                </>
              )}
            </button>
          </form>

          <div className="auth-footer">
            <p>
              Already have an account?{' '}
              <Link to="/login">Sign in instead</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
