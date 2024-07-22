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
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Campaign } from "../utils/Campaign";
import { handleEditCampaign } from "../utils/handleEditCampaign";
import { validateCampaignData } from "../utils/validateCampaignData";
import TagsInput from "./TagsInput";

export default function EditCampaignView() {
  const dispatch = useDispatch();
  const {
    campaignToEdit,
    isCampaignNameValid,
    isCampaignFundValid,
    isKeywordsValid,
    isBidAmountValid,
    isTownValid,
  } = useSelector(
    (state: Campaign) => state.campaignReducer as unknown as Campaign
  );
  const [editedCampaign, setEditedCampaign] = useState({ ...campaignToEdit });

  const handleChange = (value: any, field: any) => {
    editedCampaign && setEditedCampaign({ ...editedCampaign, [field]: value });
  };

  return campaignToEdit ? (
    <div className="flex justify-center items-center mt-2">
      <form
        className="flex flex-col items-center text-center gap-5 max-w-[300px] pb-5"
        action=""
      >
        <h1>Edit campaign number {campaignToEdit.id}</h1>
        <TextField
          label="Campaign Name"
          variant="outlined"
          name="campaignName"
          value={editedCampaign.campaignName}
          error={isCampaignNameValid === false}
          helperText={
            isCampaignNameValid === false ? "Campaign name is required" : ""
          }
          onChange={(event) => handleChange(event.target.value, "campaignName")}
          required
        />
        <TagsInput
          value={editedCampaign.keywords}
          onChange={(newKeywords: string[]) =>
            handleChange(newKeywords, "keywords")
          }
          error={isKeywordsValid === false}
          helperText={
            isKeywordsValid === false ? "At least one keyword is required" : ""
          }
        />
        <TextField
          label="Bid Amount"
          variant="outlined"
          type="number"
          name="bidAmount"
          value={editedCampaign.bidAmount}
          error={isBidAmountValid === false}
          helperText={
            isBidAmountValid === false ? "Minimum bid amount is 1" : ""
          }
          onChange={(event) =>
            handleChange(Number(event.target.value), "bidAmount")
          }
          inputProps={{ min: 1 }}
          required
        />
        <TextField
          label="Campaign Fund"
          variant="outlined"
          type="number"
          name="campaignFund"
          value={editedCampaign.campaignFund}
          error={isCampaignFundValid === false}
          helperText={
            isCampaignFundValid === false ? "Minimum campaign fund is 500" : ""
          }
          onChange={(event) =>
            handleChange(Number(event.target.value), "campaignFund")
          }
          inputProps={{ min: 500 }}
          required
        />
        <span className="text-gray-500">Status</span>
        <FormControlLabel
          control={
            <Switch
              checked={editedCampaign.status}
              onChange={(event: any) => {
                handleChange(event.target.checked, "status");
              }}
            />
          }
          label={editedCampaign.status ? "On" : "Off"}
        />
        <FormControl fullWidth error={isTownValid === false}>
          <InputLabel id="town-select-label">Town</InputLabel>
          <Select
            labelId="town-select-label"
            id="town-select"
            value={editedCampaign.town}
            label="Town"
            name="town"
            onChange={(event) => handleChange(event.target.value, "town")}
            required
          >
            <MenuItem value="Kraków">Kraków</MenuItem>
            <MenuItem value="Kielce">Kielce</MenuItem>
            <MenuItem value="Warszawa">Warszawa</MenuItem>
            <MenuItem value="Wrocław">Wrocław</MenuItem>
          </Select>
          {isTownValid === false && (
            <Typography color="error">Please select a town.</Typography>
          )}
        </FormControl>
        <Typography gutterBottom className="text-gray-500">
          Radius (km)
        </Typography>
        <Slider
          value={editedCampaign.radius}
          onChange={(_event: any, newValue: number | number[]) =>
            handleChange(newValue, "radius")
          }
          aria-labelledby="radius-slider"
          valueLabelDisplay="on"
          min={1}
          max={100}
          color="success"
        />
        <Button
          variant="contained"
          color="success"
          onClick={async () => {
            const isValid = validateCampaignData(
              editedCampaign as Campaign,
              dispatch
            );
            editedCampaign.canUpdateBalance =
              campaignToEdit.campaignFund !== editedCampaign.campaignFund;
            isValid
              ? await handleEditCampaign(
                  campaignToEdit.id ?? -1,
                  editedCampaign as Campaign,
                  dispatch
                )
              : alert("Wprowadzone dane są nieprawidłowe!");
          }}
        >
          Edit campaign
        </Button>
      </form>
    </div>
  ) : (
    <></>
  );
}
