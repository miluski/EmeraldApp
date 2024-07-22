import { Dispatch, UnknownAction } from "redux";
import { axiosInstance } from "./axiosInstance";
import { Campaign } from "./Campaign";
import { CHANGE_SELECTED_MAIN_PAGE_INDEX } from "./UserActionTypes";

export async function handleEditCampaign(
  id: number,
  campaignDto: Campaign,
  dispatch: Dispatch<UnknownAction>
): Promise<void> {
  campaignDto.userDto = JSON.parse(
    localStorage.getItem("userCredentials") ??
      '{"id": -1, "username": "", "accountBalance": 0, "password": null}'
  );
  const response = await axiosInstance.patch(
    `/api/campaignes/${id}`,
    campaignDto
  );
  if (response.status === 200) {
    if (campaignDto.userDto && campaignDto.canUpdateBalance) {
      campaignDto.userDto.accountBalance =
        (campaignDto.userDto.accountBalance ?? 0) - campaignDto.campaignFund;
      localStorage.removeItem("userCredentials");
      localStorage.setItem(
        "userCredentials",
        JSON.stringify(campaignDto.userDto)
      );
    }
    alert("Pomyślnie zedytowano kampanię!");
    dispatch({
      type: CHANGE_SELECTED_MAIN_PAGE_INDEX,
      selectedMainPageIndex: 0,
    });
  } else {
    alert("Wystąpił błąd przy edycji kampanii!");
  }
}
