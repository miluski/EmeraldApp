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
import { useDispatch, useSelector } from "react-redux";
import { Campaign } from "../utils/Campaign";
import {
  CHANGE_BID_AMOUNT,
  CHANGE_CAMPAIGN_FUND,
  CHANGE_CAMPAIGN_NAME,
  CHANGE_KEYWORDS,
  CHANGE_RADIUS,
  CHANGE_STATUS,
  CHANGE_TOWN,
} from "../utils/CampaignActionTypes";
import { handleAddCampaignButtonPress } from "../utils/handleAddCampaignButtonPress";
import { validateCampaignData } from "../utils/validateCampaignData";
import TagsInput from "./TagsInput";

export default function AddCampaignForm() {
  const dispatch = useDispatch();
  const {
    campaignName,
    keywords,
    bidAmount,
    campaignFund,
    status,
    town,
    radius,
    isCampaignNameValid,
    isKeywordsValid,
    isBidAmountValid,
    isCampaignFundValid,
    isTownValid,
  } = useSelector(
    (state: Campaign) => state.campaignReducer as unknown as Campaign
  );

  return (
    <div className="flex justify-center items-center mt-2">
      <form
        className="flex flex-col items-center text-center gap-5 max-w-[300px] pb-5"
        action=""
      >
        <h1>Add new campaign</h1>
        <TextField
          label="Campaign Name"
          variant="outlined"
          name="campaignName"
          value={campaignName}
          error={isCampaignNameValid === false}
          helperText={
            isCampaignNameValid === false ? "Campaign name is required" : ""
          }
          onChange={(event) =>
            dispatch({
              type: CHANGE_CAMPAIGN_NAME,
              campaignName: event.target.value,
            })
          }
          required
        />
        <TagsInput
          value={keywords}
          onChange={(newKeywords: string[]) =>
            dispatch({ type: CHANGE_KEYWORDS, keywords: newKeywords })
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
          value={bidAmount}
          error={isBidAmountValid === false}
          helperText={
            isBidAmountValid === false ? "Minimum bid amount is 500" : ""
          }
          onChange={(event) =>
            dispatch({
              type: CHANGE_BID_AMOUNT,
              bidAmount: event.target.value,
            })
          }
          inputProps={{ min: 500 }}
          required
        />
        <TextField
          label="Campaign Fund"
          variant="outlined"
          type="number"
          name="campaignFund"
          value={campaignFund}
          error={isCampaignFundValid === false}
          helperText={
            isCampaignFundValid === false ? "Minimum campaign fund is 500" : ""
          }
          onChange={(event) =>
            dispatch({
              type: CHANGE_CAMPAIGN_FUND,
              campaignFund: event.target.value,
            })
          }
          inputProps={{ min: 500 }}
          required
        />
        <span className="text-gray-500">Status</span>
        <FormControlLabel
          control={
            <Switch
              checked={status}
              onChange={(event: any) =>
                dispatch({
                  type: CHANGE_STATUS,
                  campaignName: event.target.checked,
                })
              }
            />
          }
          label={status ? "On" : "Off"}
        />
        <FormControl fullWidth error={isTownValid === false}>
          <InputLabel id="town-select-label">Town</InputLabel>
          <Select
            labelId="town-select-label"
            id="town-select"
            value={town}
            label="Town"
            name="town"
            onChange={(event) =>
              dispatch({
                type: CHANGE_TOWN,
                town: event.target.value,
              })
            }
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
          value={radius}
          onChange={(_event: any, newValue: number | number[]) =>
            dispatch({
              type: CHANGE_RADIUS,
              radius: newValue,
            })
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
            const campaignObject = {
              campaignName: campaignName,
              keywords: keywords,
              bidAmount: bidAmount,
              campaignFund: campaignFund,
              status: status,
              town: town,
              radius: radius,
            };
            const isValid = validateCampaignData(campaignObject, dispatch);
            isValid
              ? handleAddCampaignButtonPress(campaignObject, dispatch)
              : alert("Wprowadzone dane są nieprawidłowe!");
          }}
        >
          Add campaign
        </Button>
      </form>
    </div>
  );
}
