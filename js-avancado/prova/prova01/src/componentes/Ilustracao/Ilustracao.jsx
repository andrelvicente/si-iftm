import React from 'react';
import './Ilustracao.css';
export default function Ilustracao({ imagem, legenda }) {
    return (
        <div className="ilustracao">
            <img src={imagem} />
            <p>{legenda}</p>
        </div>
    );
}
