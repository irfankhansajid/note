import { useEffect, useState } from 'react'

import './App.css'

function App() {

  const [healthStatus, setHealthStatus] = useState("Connection begun...")

  
  useEffect(() => {
    fetch("http://localhost:8080/api/health")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Server not responding...")
      }
      return response.text();
    }).then((data) => {
      console.log("Backend Response:", data);
      setHealthStatus(data);
    }).catch((error) => {
      console.error("Fetch error:", error);
      setHealthStatus("CORS Error or Server Down");
    })
  })

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
      <h1>Frontend–Backend Integration</h1>
      <p>Status: <strong style={{ color: healthStatus === "OK" ? "green" : "red" }}>{healthStatus}</strong></p>
    </div>
  )
}

export default App
