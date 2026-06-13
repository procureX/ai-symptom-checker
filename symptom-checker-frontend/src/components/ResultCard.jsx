import React from "react";

function ResultCard({ result }) {
  if (!result) return null;

  return (
    <div style={{ padding: 15, border: "1px solid #ccc", borderRadius: 8 }}>
      <h3>Possible Conditions:</h3>
      <ul>
        {result.conditions.map((c, i) => (
          <li key={i}>{c}</li>
        ))}
      </ul>

      <h3>Urgency:</h3>
      <p>{result.urgency}</p>
    </div>
  );
}

export default ResultCard;