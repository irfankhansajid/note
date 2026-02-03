import { useEffect, useState } from "react";
import { fetchMyNotes } from "../api/notes";
import Login from "./Login";


export default function Note() {
    const [notes, setNotes] = useState([]);
    const token = localStorage.getItem('token');

    useEffect(() => {
        if (token) {
            fetchMyNotes()
                .then(data => 
                {
                 setNotes(data.content);
                }
                   )
                .catch(err => {
                    console.log(err);
                    localStorage.removeItem('token');
                    window.location.reload();
                }
                   
            )
        }
    }, [token])

    if (!token) return <Login/>

    return (
        <div>
            <h1>My notes from (cloud)</h1>
            <ul>
                {notes && notes.length > 0 ? (
                    notes.map(note => (
                    <li key={note.id}>
                        <strong>{note.title}</strong>
                        <p>{note.content}</p>
                    </li>
                ))
            ) : (
                <p>No notes found in your cloud account.</p>
            )}
            </ul>
            <button onClick={() => {localStorage.removeItem('token'); window.location.reload(); }}>Logout</button>
        </div>
    );
}