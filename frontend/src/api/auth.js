const BASE_URL = "http://localhost:8080/api/auth"

export const loginUser = async (email, password) => {
    const response = await fetch(`${BASE_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({email, password}),
    });

    if (!response.ok) {
        throw new Error("Invalid Credentials");
    }

    const data = await response.json();
    return data;
}