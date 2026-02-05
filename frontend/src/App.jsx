import { useState } from 'react';
import Note from './pages/Note';
import Login from './pages/Login';
import Register from './pages/Register';


function App() {
  const [userToken, setUserToken] = useState(localStorage.getItem('token'));
  const [isRegistering, setIsRegistering] = useState(false);

  const logout = () => {
    localStorage.removeItem('token');
    setUserToken(null);
  }


  if (userToken) {
    return (
        <div className="min-h-screen bg-[#FBFBFD] text-[#1D1D1F]">
          <nav className="bg-white border-b p-4 flex justify-between items-center shadow-sm">
            <span className="font-bold text-xl">Notebook AI</span>
            <button onClick={logout} className="text-red-600 border border-red-600 px-4 py-1 rounded-full hover:bg-red-50 transition">
              Logout
            </button>
          </nav>
          <div className="max-w-4xl mx-auto p-4">
            <Note />
          </div>
        </div>
    );
  }

  return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        {isRegistering ? (
            <Register
                onRegisterSuccess={() => setIsRegistering(false)}
                onToggleView={() => setIsRegistering(false)}
            />
        ) : (
            <div className="flex flex-col items-center">
              <Login onLoginSuccess={(token) => setUserToken(token)} />
              <p className="mt-[-100px] text-gray-600 text-sm z-10">
                Don't have an account?
                <button onClick={() => setIsRegistering(true)} className="text-blue-600 ml-1 font-semibold hover:underline">
                  Register here
                </button>
              </p>
            </div>
        )}
      </div>
  );
}
export default App;