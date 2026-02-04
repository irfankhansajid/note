import { useState } from 'react';
import Note from './pages/Note';
import Login from './pages/Login';

function App() {
  const [userToken, setUserToken] = useState(localStorage.getItem('token'));

  const logout = () => {
    localStorage.removeItem('token');
    setUserToken(null);
  }

  return (
    <div className="min-h-screen bg-[#FBFBFD] text-[#1D1D1F]">
       {userToken ? (
        <>
          <nav className="bg-white border-b p-4 flex justify-between items-center mb-4">
            <span className="font-bold text-xl">Notebook App</span>
            <button onClick={logout} className="text-red-600 border border-red-600 px-3 py-1 rounded hover:bg-red-100">
                Logout
            </button>
          </nav>
          
          <Note />
        </>
       ) : (
        <Login onLoginSuccess={(token) => setUserToken(token)} />
       )}
    </div>
  )
}

export default App;