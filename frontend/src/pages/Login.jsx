import { useState } from "react";
import { loginUser } from "../api/auth";



export default function Login( {onLoginSuccess} ) {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await loginUser(email, password);
            
            localStorage.setItem('token', data.data.token);
            if (onLoginSuccess) {
                onLoginSuccess(data.data.token)
            }
            alert("Login Success");
        } catch (err) {
            alert(err.message);
        }
    }
    return(
        <form onSubmit={handleSubmit} 
        style={{display: "flex", flexDirection: "column", gap: '10px', maxWidth: '300px'}}>
            <input
             type="email"
             placeholder="Email..."
             onChange={e => setEmail(e.target.value)}
             required
             />
            <input type="password" 
             placeholder="Password..."
             onChange={e => setPassword(e.target.value)}
             required
            />
            <button type="submit">Login</button>
        </form>
    )
}