import { useState } from "react";
import { loginUser } from "../api/auth";

export default function Login({ onLoginSuccess }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await loginUser(email, password);
            localStorage.setItem('token', data.data.token);
            if (onLoginSuccess) onLoginSuccess(data.data.token);
        } catch (err) {
            alert(err.message);
        }
    }

    return (
        <div className="min-h-screen flex justify-center items-center bg-gray-100 text-black">
            
            <div className="bg-white p-8 rounded w-96">
                <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>
                
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        type="email"
                        placeholder="Email"
                        className="border border-gray-300 p-2 rounded w-full"
                        onChange={e => setEmail(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        className="border border-gray-300 p-2 rounded w-full"
                        onChange={e => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit" className="bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
                        Sign In
                    </button>
                </form>
            </div>
        </div>
    );
}