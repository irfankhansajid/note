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