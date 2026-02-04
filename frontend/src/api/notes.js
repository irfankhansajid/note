export const fetchMyNotes = async () => {
    const token = localStorage.getItem('token');

    const response = await fetch("http://localhost:8080/api/notes", {
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

    const response = await fetch("http://localhost:8080/api/notes", {
        method: "POST",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noteData)
    });
    if (!response) throw new Error("Failed to create note");
    return await response.json();
}

export const deleteNote = async (id) => {
    const token = localStorage.getItem('token');
    await fetch(`http://localhost:8080/api/notes/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${token}` }
    });
};

export const updateNote = async (id, noteData) => {
    const token = localStorage.getItem('token');
    const response = await fetch(`http://localhost:8080/api/notes/${id}`, {
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