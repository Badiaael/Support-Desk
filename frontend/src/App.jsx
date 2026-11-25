import { useEffect, useState } from 'react'

function App() {
  const [health, setHealth] = useState(null)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/health')
      .then((response) => {
        if (!response.ok) throw new Error('Code HTTP ' + response.status)
        return response.json()
      })
      .then((data) => setHealth(data))
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main>
      <h1>Support Desk</h1>
      {error && <p>Erreur : {error}</p>}
      {!error && !health && <p>Chargement…</p>}
      {health && <p>État de l'API : {health.status}</p>}
    </main>
  )
}

export default App