import { useState } from "react";
import { registerUser } from "../api/auth"; // Assuming your API helper is here

export default function Register({ onRegisterSuccess, onToggleView }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await registerUser(email, password);
            alert("Registration Successful!");
            if (onRegisterSuccess) onRegisterSuccess();
        } catch (err) {
            alert(err.message);
        }
    }

    return (
        <div className="flex flex-col items-center">
            <div className="bg-white p-8 rounded shadow-md w-96">
                <h2 className="text-2xl font-bold mb-6 text-center text-black">Register</h2>

                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <input
                        type="email"
                        placeholder="Email"
                        className="border border-gray-300 p-2 rounded w-full text-black"
                        onChange={e => setEmail(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Password (Min 8 chars)"
                        className="border border-gray-300 p-2 rounded w-full text-black"
                        onChange={e => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit" className="bg-green-500 text-white p-2 rounded hover:bg-green-600 transition shadow">
                        Sign Up
                    </button>
                </form>

                <p className="mt-6 text-center text-gray-600 text-sm">
                    Already have an account?
                    <button onClick={onToggleView} className="text-blue-600 ml-1 font-semibold hover:underline">
                        Login here
                    </button>
                </p>
            </div>
        </div>
    );
}