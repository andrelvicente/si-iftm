
export default function Descricao({ subtitulo, descricao }) {
    return (
        <div className="descricao">
            <h2>{subtitulo}</h2>
            <p>{descricao}</p>
        </div>
    );
}
