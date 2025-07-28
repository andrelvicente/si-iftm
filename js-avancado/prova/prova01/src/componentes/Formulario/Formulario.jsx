import React, { useState } from 'react';
import './Formulario.css';

export default function Formulario() {
  const [email, setEmail] = useState('');

  const handleChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSubmit = () => {
    if (email.trim() === '') {
      alert('Email em branco. Favor informá-lo');
    } else {
      alert('Email informado. OK!');
    }
  };

  return (
    <div>
      <label>
        <strong>Email</strong>{' '}
        <input
          type="text"
          value={email}
          onChange={handleChange}
          style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #ccc' }}
        />
      </label>
      <button
        onClick={handleSubmit}
      >
        Enviar
      </button>
      <p style={{ marginTop: '1rem', fontWeight: 'bold' }}>{email}</p>
    </div>
  );
};