import React, { useState } from "react";

// Accept loading and setLoading as props from App.js
function SymptomForm({ onResult, loading, setLoading }) {
  const [symptoms, setSymptoms] = useState("");
  const [error, setError] = useState("");

  const analyzeSymptoms = async () => {
    if (!symptoms.trim()) return; // Prevent empty submissions

    setLoading(true); // Set the global loading state to true
    setError("");
    onResult(null);   // Clear previous results while thinking

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
      onResult({ error: "Failed to connect to local AI service." });
    } finally {
      setLoading(false); // Ensures loading turns off even if an error occurs
    }
  };

  return (
    <div style={{ marginBottom: 20 }}>
      <textarea
        rows="4"
        style={{ 
          width: "100%", 
          padding: 10, 
          borderRadius: "4px", 
          border: "1px solid #ccc",
          boxSizing: "border-box" 
        }}
        placeholder="Describe your symptoms..."
        value={symptoms}
        onChange={(e) => setSymptoms(e.target.value)}
        disabled={loading} // Lock the textarea while MedLlama2 compiles tokens
      />
      <button
        onClick={analyzeSymptoms}
        disabled={loading || !symptoms.trim()} // Disable button click while loading
        style={{ 
          marginTop: 10, 
          padding: "10px 20px",
          backgroundColor: loading ? "#cccccc" : "#007bff", // Changes to gray when loading
          color: "white",
          border: "none",
          borderRadius: "4px",
          fontWeight: "bold",
          cursor: loading ? "not-allowed" : "pointer",
          width: "100%" // Makes it clean and uniform
        }}
      >
        {loading ? "Processing Local LLM..." : "Analyze"}
      </button>

      {error && <p style={{ color: "red", marginTop: 10 }}>{error}</p>}
    </div>
  );
}

export default SymptomForm;