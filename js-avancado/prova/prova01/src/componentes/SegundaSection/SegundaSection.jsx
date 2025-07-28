import React from 'react';
import Descricao from '../Descricao/Descricao';
import Ilustracao from '../Ilustracao/Ilustracao';
import Formulario from '../Formulario/Formulario';
import image2 from './assets/img/image2.png';
import './SegundaSection.css';

export default function SegundaSection() {
    return (
        <section className='segunda-section'>
            <table className="full-width">
                <tr>
                    <td>
                        <Ilustracao
                            imagem={image2}
                            legenda="Fachada da oficina mecânica"
                        />
                    </td>
                    <td className="justify-content">
                        <Descricao
                            subtitulo="Performance máxima"
                            descricao="Troca de óleo e fluidos especializados que otimizam o desempenho do motor e câmbio, garantindo mais economia de combustível."
                        />
                        <Formulario />
                    </td>
                </tr>
            </table>
        </section>
    );
}