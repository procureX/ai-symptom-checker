import React, { useState } from "react";

function SymptomForm({ onResult }) {
  const [symptoms, setSymptoms] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const analyzeSymptoms = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await fetch("http://localhost:8080/api/symptoms", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ symptoms }),
      });

      if (!response.ok) throw new Error("Server error");

      const data = await response.json();
      onResult(data);
    } catch (err) {
      setError("Something went wrong. Try again.");
    }

    setLoading(false);
  };

  return (
    <div style={{ marginBottom: 20 }}>
      <textarea
        rows="4"
        style={{ width: "100%", padding: 10 }}
        placeholder="Describe your symptoms..."
        value={symptoms}
        onChange={(e) => setSymptoms(e.target.value)}
      />
      <button
        onClick={analyzeSymptoms}
        style={{ marginTop: 10, padding: "10px 20px" }}
      >
        Analyze
      </button>

      {loading && <p>Analyzing symptoms…</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

export default SymptomForm;