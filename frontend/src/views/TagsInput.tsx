import { Chip, Paper, TextField } from "@mui/material";
import { useState } from "react";

export default function TagsInput({ value, onChange, error, helperText }: any) {
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (event: any) => {
    setInputValue(event.target.value);
  };

  const handleKeyDown = (event: any) => {
    if (event.key === "Enter" && inputValue) {
      if (!value.includes(inputValue)) {
        onChange([...value, inputValue]);
        setInputValue("");
      }
    } else if (event.key === "Backspace" && !inputValue) {
      onChange(value.slice(0, value.length - 1));
    }
  };

  const handleDelete = (item: any) => {
    onChange(value.filter((i: any) => i !== item));
  };

  return (
    <Paper
      component="ul"
      style={{
        display: "flex",
        justifyContent: "center",
        flexWrap: "wrap",
        listStyle: "none",
        padding: "0.5rem",
        margin: 0,
      }}
    >
      {value.map((item: string, index: number) => (
        <li key={index} style={{ margin: "0.5rem" }}>
          <Chip label={item} onDelete={() => handleDelete(item)} />
        </li>
      ))}
      <TextField
        label="Add Keyword"
        variant="outlined"
        value={inputValue}
        onChange={handleInputChange}
        onKeyDown={handleKeyDown}
        error={error}
        helperText={helperText}
      />
    </Paper>
  );
}
