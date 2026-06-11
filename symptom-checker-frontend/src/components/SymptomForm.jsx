import { useState } from "react";

function SymptomForm({ onSubmit }) {
  const [symptoms, setSymptoms] = useState("");

  const handleSubmit = () => {
    onSubmit(symptoms);
  };

  return (
    <div>
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
    </div>
  );
}

export default SymptomForm;
