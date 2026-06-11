function ResultCard({ result }) {
  return (
    <div style={{ marginTop: "2rem", padding: "1rem", border: "1px solid #ccc" }}>
      <h3>Possible Conditions:</h3>
      <ul>
        {result.conditions.map((c, i) => (
          <li key={i}>{c}</li>
        ))}
      </ul>
      <p><strong>Urgency:</strong> {result.urgency}</p>
    </div>
  );
}

export default ResultCard;
