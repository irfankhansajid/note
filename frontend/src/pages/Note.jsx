import { useEffect, useState } from "react";
import { createNote, deleteNote, fetchMyNotes, updateNote } from "../api/notes";
import Login from "./Login";

export default function Note() {
    const [notes, setNotes] = useState([]);
    const token = localStorage.getItem('token');

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [searchTerm, setSearchTerm] = useState('');

    const [editingId, setEditingId] = useState(null);
    const [editTitle, setEditTitle] = useState('');
    const [editContent, setEditContent] = useState('');

    useEffect(() => {
        if (token) {
            fetchMyNotes()
                .then(data => setNotes(data.content))
                .catch(() => {
                    localStorage.removeItem('token');
                    window.location.reload();
                });
        }
    }, [token]);

    if (!token) return <Login />;

    const handleCreate = async (e) => {
        e.preventDefault();
        const newNote = await createNote({ title, content });
        setNotes([newNote, ...notes]);
        setTitle('');
        setContent('');
    };

    const handleDelete = async (id) => {
        if (confirm("Delete this note?")) {
            await deleteNote(id);
            setNotes(notes.filter(n => n.id !== id));
        }
    };

    const handleUpdate = async (id) => {
        const updated = await updateNote(id, { title: editTitle, content: editContent });
        setNotes(notes.map(n => n.id === id ? updated : n));
        setEditingId(null);
    };

    const startEdit = (note) => {
        setEditingId(note.id);
        setEditTitle(note.title);
        setEditContent(note.content);
    };

    const filteredNotes = notes.filter(n =>
        n.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        n.content.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="min-h-screen bg-[#f5f5f7] py-12 px-4 text-[#1d1d1f]">
            <div className="max-w-2xl mx-auto">
                <h1 className="text-4xl font-bold mb-8 tracking-tight">My Notes</h1>

                {/* CREATE FORM*/}
                <form onSubmit={handleCreate} className="bg-white p-6 rounded-2xl border border-gray-200 mb-8">
                    <input
                        className="w-full text-xl font-semibold mb-2 outline-none"
                        placeholder="Title"
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                        required
                    />
                    <textarea
                        className="w-full text-gray-600 outline-none resize-none"
                        placeholder="Write something..."
                        rows="3"
                        value={content}
                        onChange={e => setContent(e.target.value)}
                        required
                    />
                    <div className="flex justify-end mt-4">
                        <button className="bg-[#0071e3] text-white px-5 py-2 rounded-full font-medium hover:bg-[#0077ed]">
                            Add Note
                        </button>
                    </div>
                </form>

                {/* SEARCH - Light gray bar */}
                <input
                    className="w-full bg-[#e8e8ed] p-3 px-5 mb-8 rounded-xl outline-none focus:ring-1 focus:ring-blue-400"
                    placeholder="Search notes..."
                    value={searchTerm}
                    onChange={e => setSearchTerm(e.target.value)}
                />

                {/* LIST - Using Grid for clean alignment */}
                <div className="space-y-4">
                    {filteredNotes.map(note => (
                        <div key={note.id} className="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm hover:shadow-xl">
                            {editingId === note.id ? (

                                <div className="flex flex-col gap-2">
                                    <input className="font-bold outline-none"
                                        value={editTitle}
                                        onChange={e => setEditTitle(e.target.value)}
                                    />

                                    <textarea
                                        className="text-gray-600 outline-none"
                                        value={editContent}
                                        onChange={e => setEditContent(e.target.value)}
                                    />
                                    <div className="flex gap-4 mt-2">
                                        <button
                                         onClick={() => handleUpdate(note.id)}
                                         className="text-[#0071e3] font-bold">
                                            Save
                                        </button>

                                        <button 
                                        onClick={() => setEditingId(null)} className="text-gray-400">Cancel</button>
                                    </div>
                                </div>
                            ) : (
                                <div className="flex justify-between items-start">
                                    <div className="pr-4">
                                        <h3 className="text-[21px] font-bold tracking-tight mb-1">
        {note.title}
    </h3>
    <p className="text-[17px] text-[#6E6E73] leading-relaxed font-medium">
        {note.content}
    </p>
                                    </div>
                                    <div className="flex gap-4 text-sm font-medium">
                                        <button
                                         onClick={() => startEdit(note)}
                                        className="text-[#0071e3] hover:underline">Edit</button>

                                        <button 
                                        onClick={() => handleDelete(note.id)}
                                        className="text-red-500 hover:underline">Delete</button>
                                    </div>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}