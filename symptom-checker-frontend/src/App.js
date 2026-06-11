import { useState } from "react";
import SymptomForm from "./components/SymptomForm";
import ResultCard from "./components/ResultCard";
import "./App.css";


function App() {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSymptomSubmit = async (symptoms) => {
    setLoading(true);
    setResult(null);

    try {
      const response = await fetch("http://localhost:8080/api/symptoms", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ symptoms }),
      });

      const data = await response.json();
      setResult(data);
    } catch (error) {
      alert("Error connecting to backend");
    }

    setLoading(false);
  };

  return (
    <div style={{ padding: "2rem", maxWidth: "600px", margin: "auto" }}>
      <h2>AI Symptom Checker</h2>

      <SymptomForm onSubmit={handleSymptomSubmit} />

      {loading && <p>Analyzing symptoms…</p>}

      {result && <ResultCard result={result} />}
    </div>
  );
}

export default App;
