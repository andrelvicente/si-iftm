import React from 'react';
import Descricao from '../Descricao/Descricao';
import Ilustracao from '../Ilustracao/Ilustracao';
import image1 from './assets/img/image1.png';
import './PrimeiraSection.css';

export default function PrimeiraSection() {
    return (
        <section className='primeira-section'>
            <table className="full-width">
                <tr>
                    <td className="justify-content">
                        <Descricao
                            subtitulo="JetLub – Super Troca de Óleo"
                            descricao="Mais de 5.000 clientes satisfeitos confiam na JetLub para manter seus carros em perfeitas condições. Nossos profissionais altamente qualificados garantem um serviço impecável, transparente e ágil, sempre utilizando produtos das melhores marcas do mercado."
                        />
                        <button className="decorative-button">Quero Começar</button>
                    </td>
                    <td>
                        <Ilustracao
                            imagem={image1}
                            legenda="Nós cuidamos do óleo, você cuida do volante!" 
                        />
                    </td>
                </tr>
            </table>
        </section>
    );
}