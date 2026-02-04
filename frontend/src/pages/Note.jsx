import { useEffect, useState } from "react";
import { createNote, deleteNote, fetchMyNotes, updateNote } from "../api/notes";
import Login from "./Login";


export default function Note() {
    const [notes, setNotes] = useState([]);
    const token = localStorage.getItem('token');

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const [editingId, setEditingId] = useState(null);
    const [editTitle, setEditTitle] = useState('');
    const [editContent, setEditContent] = useState('');

    const [searchTerm, setSearchTerm] = useState('');

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


    const handleCreate = async (e) => {
        e.preventDefault();
        try {
            const newNote = await createNote({title, content});
            setNotes([...notes, newNote]);
            setTitle('')
            setContent('')
        } catch(err) {
            alert(err.message);
        }
    }

    const handleDelete = async (id) => {
        if (window.confirm("Delete this note?")) {
            await deleteNote(id);
            setNotes(notes.filter(note => note.id !== id)); 
        }
    };

    const handleUpdate = async (id) => {
        try {
            const updated = await updateNote(id, {title: editTitle, content: editContent});
            setNotes(notes.map(n => n.id === id ? updated : n))
            setEditingId(null)
        } catch(err) {
            alert(err.message); 
        }
    }
    const startEdit = (note) => {
        
        setEditingId(note.id)
        setEditTitle(note.title)
        setEditContent(note.content)
    }

    const filterNotes = notes.filter(n => 
        n.title.toLowerCase().includes(searchTerm.toLowerCase()) || 
        n.content.toLowerCase().includes(searchTerm.toLowerCase())
    )

    return (
        <div>
            <h1>My notes from (cloud)</h1>

            <form onSubmit={handleCreate} style={{ marginBottom: '20px' }}>
                <input value={title} placeholder="Title" onChange={e => setTitle(e.target.value)} required />
                <textarea value={content} placeholder="Content" onChange={e => setContent(e.target.value)} required />
                <button type="submit">Add Note</button>
            </form>

            <input 
                placeholder="Search notes..." 
                onChange={e => setSearchTerm(e.target.value)} 
                style={{ width: '100%', padding: '10px', marginBottom: '20px' }}
            />

            <ul>
                {filterNotes && filterNotes.length > 0 ? (
                    filterNotes.map(note => (
                    <li key={note.id}>
                        {editingId == note.id ? (
                            <div style={{ background: '#f9f9f9', padding: '10px' }}>
                        <input 
                            value={editTitle} 
                            onChange={(e) => setEditTitle(e.target.value)} 
                            style={{ display: 'block', marginBottom: '5px' }}
                        />
                        <textarea 
                            value={editContent} 
                            onChange={(e) => setEditContent(e.target.value)} 
                            style={{ display: 'block', marginBottom: '5px' }}
                        />
                        <button onClick={() => handleUpdate(note.id)}>Save</button>
                        <button onClick={() => setEditingId(null)}>Cancel</button>
                    </div>
                        ) : (
                            <div>
                        <strong>{note.title}</strong>
                        <p>{note.content}</p>
                        <button onClick={() => startEdit(note)}>Edit</button>
                        <button 
                            onClick={() => handleDelete(note.id)} 
                            style={{ color: 'red', marginLeft: '10px' }}
                        >
                            Delete
                        </button>
                    </div>
                        )}
                        
                    
                    </li>
                ))
            ) : (
                <p>No notes found in your {searchTerm}.</p>
            )}
            </ul>
            <button onClick={() => {localStorage.removeItem('token'); window.location.reload(); }}>Logout</button>
        </div>
    );
}