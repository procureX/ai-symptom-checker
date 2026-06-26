import React from "react";

// Accept the loading prop from App.js
function ResultCard({ result, loading }) {
  
  // 1. Show the animated spinner while the local model compiles tokens
  if (loading) {
    return (
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: "30px" }}>
        <div className="spinner" style={{
          width: "30px",
          height: "30px",
          border: "3px solid #f3f3f3",
          borderTop: "3px solid #007bff",
          borderRadius: "50%",
          animation: "spin 1s linear infinite"
        }} />
        <p style={{ marginTop: "10px", color: "#666", fontSize: "14px" }}>MedLlama2 is analyzing...</p>
        
        {/* Inject keyframe styles for the spinner rotation */}
        <style>{`
          @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
          }
        `}</style>
      </div>
    );
  }

  // If nothing is loading and there's no result yet, keep the component hidden
  if (!result) return null;

  // Handle network or system errors gracefully if passed down
  if (result.error) {
    return <p style={{ color: "red", marginTop: "20px", fontWeight: "bold" }}>{result.error}</p>;
  }

  // 2. Render your normal results card once data arrives from Ollama
  return (
    <div style={{ padding: 15, border: "1px solid #ccc", borderRadius: 8, marginTop: "20px", backgroundColor: "#f9f9f9" }}>
      <h3>Possible Conditions:</h3>
      <ul>
        {result.conditions && result.conditions.map((c, i) => (
          <li key={i}>{c}</li>
        ))}
      </ul>

      <h3>Urgency:</h3>
      <p style={{ 
        fontWeight: "bold", 
        // Dynamically color code the urgency string
        color: result.urgency === "High" ? "#dc3545" : result.urgency === "Medium" ? "#ffc107" : "#28a745" 
      }}>
        {result.urgency}
      </p>
    </div>
  );
}

export default ResultCard;