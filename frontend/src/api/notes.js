
const BASE_URL = "https://note-okqk.onrender.com/api/notes";

export const fetchMyNotes = async () => {
    const token = localStorage.getItem('token');

    const response = await fetch(BASE_URL, {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    });

    if (!response.ok) throw new Error("Failed to fetch notes");
    return await response.json();
};

export const createNote = async (noteData) => {
    const token = localStorage.getItem('token');

    const response = await fetch(BASE_URL, {
        method: "POST",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noteData)
    });

    if (!response.ok) throw new Error("Failed to create note");
    return await response.json();
};

export const deleteNote = async (id) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${BASE_URL}/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${token}` }
    });
    if (!response.ok) throw new Error("Failed to delete note");
};

export const updateNote = async (id, noteData) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`${BASE_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noteData)
    });
    if (!response.ok) throw new Error("Failed to update note");
    return await response.json();
};