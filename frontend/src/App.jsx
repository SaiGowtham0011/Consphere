import { useState } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import SignIn from './components/SignIn/SignInPage'
import Login from './components/Login/LoginPage'
import './App.css'

function App() {
  return (
    <>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signin" element={<SignIn />} />
      </Routes>
    </BrowserRouter>
      
    </>
  )
}

export default App
