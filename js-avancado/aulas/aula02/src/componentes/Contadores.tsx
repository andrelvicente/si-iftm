import React, { use, useEffect } from "react";

function Contadores() {
    const [contador1, setContador1] = React.useState(0);
    const [contador2, setContador2] = React.useState(0);
    const incrementarContador1 = () => {
        setContador1(contador1 + 1);
    };
    const incrementarContador2 = () => {
        setContador2(contador2 + 1);
    };

    useEffect(() => {
        console.log("Contador 1 atualizado:", contador1);
        console.log("Contador 2 atualizado:", contador2);
    }, [contador1, contador2]);
    
    return (
        <>
            <form>
                <input type="button" value="Incrementar +1 no contador 1" onClick={()=> incrementarContador1()}/>
                <input type="button" value="Incrementar +1 no contador 2" onClick={()=> incrementarContador2()}/>
            </form>
            <p>Contador 1: {contador1}</p>
            <p>Contador 2: {contador2}</p>

        </>
    );
}

export default Contadores;