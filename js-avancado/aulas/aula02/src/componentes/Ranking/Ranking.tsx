import { useEffect, useState } from "react";
import style from "./Ranking.module.css";

export function Ranking() {
    const [users, setUsers] = useState([]);
    function showUsers() {
        return (
            <ul>
                {users.map((user: any) => (
                    <li>
                        <span className={style.alerta}>{user.name}</span> ({user.level}:{user.score})
                    </li>
                ))}
            </ul>
        );
    }
    useEffect(() => {
        fetch("https://wilton-filho.github.io/PFJS-GitHub/APIs/fetch/versao01/03/js/users.json")
            .then(response => response.json())
            .then(data => {
                console.log("Ranking atualizado:", data);
                setUsers(data.users);
            }
            )
            .catch(error => {
                console.error("Erro ao buscar ranking:", error);
            }
        );
    }, []);

    return (
        <>
        <h2>Melhores colocados (Ranking):</h2>
        {users && showUsers()}
        </>
    );
}

export default Ranking;