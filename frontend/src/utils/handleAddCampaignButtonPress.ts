import { Dispatch, UnknownAction } from "redux";
import { Campaign } from "./Campaign";
import { RESET_CAMPAIGN_REDUCER } from "./CampaignActionTypes";
import { axiosInstance } from "./axiosInstance";

export async function handleAddCampaignButtonPress(
  campaign: Campaign,
  dispatch: Dispatch<UnknownAction>
): Promise<void> {
  campaign.userDto = JSON.parse(
    localStorage.getItem("userCredentials") ??
      '{"id": -1, "username": "", "accountBalance": 0, "password": null}'
  );
  const response = await axiosInstance.post("/api/campaignes/create", campaign);
  if (response.status === 200) {
    if (campaign.userDto) {
      campaign.userDto.accountBalance =
        (campaign.userDto.accountBalance ?? 0) - campaign.campaignFund;
      localStorage.removeItem("userCredentials");
      localStorage.setItem("userCredentials", JSON.stringify(campaign.userDto));
    }
    dispatch({ type: RESET_CAMPAIGN_REDUCER });
    alert("Pomyślnie dodano nową kampanię!");
  } else {
    alert("Wystąpił błąd przy dodawaniu nowej kampanii! Spróbuj ponownie!");
  }
}
