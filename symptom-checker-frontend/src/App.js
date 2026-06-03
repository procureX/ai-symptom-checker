import { useState } from "react";

function App() {
  const [symptoms, setSymptoms] = useState("");
  const [result, setResult] = useState(null);

  const handleSubmit = async () => {
    const response = await fetch("http://localhost:8080/api/symptoms", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ symptoms }),
    });

    const data = await response.json();
    setResult(data);
  };

  return (
    <div style={{ padding: "2rem", maxWidth: "600px", margin: "auto" }}>
      <h2>AI Symptom Checker</h2>

      <textarea
        rows="4"
        style={{ width: "100%" }}
        placeholder="Describe your symptoms..."
        value={symptoms}
        onChange={(e) => setSymptoms(e.target.value)}
      />

      <button onClick={handleSubmit} style={{ marginTop: "1rem" }}>
        Submit
      </button>

      {result && (
        <div style={{ marginTop: "2rem", padding: "1rem", border: "1px solid #ccc" }}>
          <h3>Possible Conditions:</h3>
          <ul>
            {result.conditions.map((c, i) => (
              <li key={i}>{c}</li>
            ))}
          </ul>
          <p><strong>Urgency:</strong> {result.urgency}</p>
        </div>
      )}
    </div>
  );
}

export default App;