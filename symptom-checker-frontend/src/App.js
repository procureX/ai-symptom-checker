import React, { useState } from "react";
import SymptomForm from "./components/SymptomForm";
import ResultCard from "./components/ResultCard";
import "./App.css";

function App() {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false); // Track loading globally

  return (
    <div style={{ maxWidth: 600, margin: "40px auto", fontFamily: "Arial", padding: "0 20px" }}>
      <h1>AI Symptom Checker</h1>
      
      {/* THE MEDICAL DISCLAIMER BANNER */}
      <div style={{ 
        backgroundColor: '#fff3cd', 
        color: '#856404', 
        padding: '12px', 
        borderRadius: '6px', 
        marginBottom: '20px',
        fontSize: '14px',
        border: '1px solid #ffeeba',
        textAlign: 'left'
      }}>
        <strong>Disclaimer:</strong> This application utilizes a localized AI model (MedLlama2) for educational and demonstration purposes. It does not provide real medical advice, diagnosis, or treatment strategies. Always consult a qualified healthcare professional.
      </div>

      {/* Pass loading and setLoading down as props */}
      <SymptomForm onResult={setResult} loading={loading} setLoading={setLoading} />
      
      {/* Pass loading down so the card knows when to spin */}
      <ResultCard result={result} loading={loading} />
    </div>
  );
}

export default App;