import React, { useState } from "react";
import SymptomForm from "./components/SymptomForm";
import ResultCard from "./components/ResultCard";
import "./App.css";

function App() {
  const [result, setResult] = useState(null);

  return (
    <div style={{ maxWidth: 600, margin: "40px auto", fontFamily: "Arial" }}>
      <h1>AI Symptom Checker</h1>
      <SymptomForm onResult={setResult} />
      <ResultCard result={result} />
    </div>
  );
}

export default App;