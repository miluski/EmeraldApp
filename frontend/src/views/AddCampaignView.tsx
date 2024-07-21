import {
  Button,
  FormControl,
  FormControlLabel,
  InputLabel,
  MenuItem,
  Select,
  Slider,
  Switch,
  TextField,
  Typography,
} from "@mui/material";
import React from "react";

export default function AddCampaignForm() {
  const [campaignData, setCampaignData] = React.useState({
    campaignName: "",
    keywords: "",
    bidAmount: 500,
    campaignFund: 500,
    status: false,
    town: "",
    radius: 0,
  });

  const handleInputChange = (event: any) => {
    const { name, value } = event.target;
    setCampaignData({ ...campaignData, [name]: value });
  };

  const handleSliderChange = (_event: any, newValue: any) => {
    setCampaignData({ ...campaignData, radius: newValue });
  };

  const handleStatusChange = (event: any) => {
    setCampaignData({ ...campaignData, status: event.target.checked });
  };

  return (
    <div className="flex justify-center items-center mt-2">
      <form className="flex flex-col items-center text-center gap-5 max-w-[300px] pb-5">
        <h1>Add new campaign</h1>
        <TextField
          label="Campaign Name"
          variant="outlined"
          name="campaignName"
          value={campaignData.campaignName}
          onChange={handleInputChange}
          required
        />
        <TextField
          label="Keywords"
          variant="outlined"
          name="keywords"
          value={campaignData.keywords}
          onChange={handleInputChange}
          required
        />
        <TextField
          label="Bid Amount"
          variant="outlined"
          type="number"
          name="bidAmount"
          value={campaignData.bidAmount}
          onChange={handleInputChange}
          inputProps={{ min: 500 }}
          required
        />
        <TextField
          label="Campaign Fund"
          variant="outlined"
          type="number"
          name="campaignFund"
          value={campaignData.campaignFund}
          onChange={handleInputChange}
          inputProps={{ min: 500 }}
          required
        />
        <span className="text-gray-500">Status</span>
        <FormControlLabel
          control={
            <Switch
              checked={campaignData.status}
              onChange={handleStatusChange}
            />
          }
          label={campaignData.status ? "On" : "Off"}
        />
        <FormControl fullWidth>
          <InputLabel id="town-select-label">Town</InputLabel>
          <Select
            labelId="town-select-label"
            id="town-select"
            value={campaignData.town}
            label="Town"
            name="town"
            onChange={handleInputChange}
            required
          >
            <MenuItem value="krakow">Kraków</MenuItem>
            <MenuItem value="kielce">Kielce</MenuItem>
            <MenuItem value="warszawa">Warszawa</MenuItem>
            <MenuItem value="wroclaw">Wrocław</MenuItem>
          </Select>
        </FormControl>
        <Typography gutterBottom className="text-gray-500">Radius (km)</Typography>
        <Slider
          value={campaignData.radius}
          onChange={handleSliderChange}
          aria-labelledby="radius-slider"
          valueLabelDisplay="on"
          min={1}
          max={100}
          color="success"
        />
        <Button type="submit" variant="contained" color="success">
          Add campaign
        </Button>
      </form>
    </div>
  );
}
