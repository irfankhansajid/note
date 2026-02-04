
import { useState } from 'react'
import './App.css'
import Note from './pages/Note'
import Login from './pages/Login';

function App() {

  const [userToken, setUserToken] = useState(localStorage.getItem('token'));

  const logout = () => {
    localStorage.removeItem('token')
    setUserToken(null);
  }


  return (
    <div className='container'>
       {userToken ? (
        <>
          <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '10px', background: '#eee' }}>
            <span>Logged in as User</span>
            <button onClick={logout}>Logout</button>
          </nav>
          <Note />
        </>
       ) : (
        <Login onLoginSuccess={(token) => setUserToken(token)} />
       )}
    </div>
  )
}

export default App
