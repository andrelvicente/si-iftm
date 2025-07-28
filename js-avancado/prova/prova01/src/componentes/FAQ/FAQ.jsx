import React, { useEffect, useState } from "react";
import "./FAQ.css";

function FAQItem({ pergunta, resposta }) {
    return (
        <>
            <strong>Pergunta:</strong> {pergunta}<br />
            <strong>Resposta:</strong> {resposta}<br /><br />
        </>
    );
}

export default function FAQ() {
    const [faqList, setFaqList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchFAQ = async () => {
            try {
                const response = await fetch("https://wilton-filho.github.io/PFJS-GitHub/React/projeto/json/faqprova.json");
                if (!response.ok) {
                    throw new Error(`Erro ao carregar a FAQ: ${response.statusText}`);
                }
                const data = await response.json();
                console.log("FAQ carregada:", data);
                setFaqList(data.faqs);
            } catch (err) {
                console.error(err);
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchFAQ();
    }, []);

    if (loading) return <p>Carregando perguntas...</p>;
    if (error) return <p>Erro: {error}</p>;

    return (
        <section className="faq-section">
            <div>
                <h2>Perguntas Frequentes</h2>
                {faqList.map((item, index) => (
                    <FAQItem key={index} pergunta={item.pergunta} resposta={item.resposta} />
                ))}
            </div>
        </section>
    );
}