import { Dispatch, UnknownAction } from "redux";
import { Campaign } from "./Campaign";
import {
  CHANGE_IS_BID_AMOUNT_VALID,
  CHANGE_IS_CAMPAIGN_FUND_VALID,
  CHANGE_IS_CAMPAIGN_NAME_VALID,
  CHANGE_IS_KEYWORDS_VALID,
  CHANGE_IS_TOWN_VALID,
} from "./CampaignActionTypes";

export function validateCampaignData(
  campaign: Campaign,
  dispatch: Dispatch<UnknownAction>
): boolean {
  const { accountBalance } = JSON.parse(
    localStorage.getItem("userCredentials") ??
      '{"username": "", "accountBalance": 0}'
  );
  const isCampaignNameValid = campaign.campaignName.trim().length > 0;
  const isKeywordsValid =
    Array.isArray(campaign.keywords) && campaign.keywords.length !== 0;
  const isBidAmountValid = Number(campaign.bidAmount) >= 1;
  const isCampaignFundValid = Number(campaign.campaignFund) >= 500;
  const isTownValid = campaign.town.trim().length > 0;
  const isRadiusValid =
    Number(campaign.radius) >= 1 && Number(campaign.radius) <= 100;
  const hasUserFunds = accountBalance >= campaign.campaignFund;
  hasUserFunds
    ? null
    : alert(
        "Twoje fundusze są niewystarczające do wystartowania tej kampanii!"
      );
  dispatch({
    type: CHANGE_IS_KEYWORDS_VALID,
    isKeywordsValid: isKeywordsValid,
  });
  dispatch({
    type: CHANGE_IS_BID_AMOUNT_VALID,
    isBidAmountValid: isBidAmountValid,
  });
  dispatch({
    type: CHANGE_IS_CAMPAIGN_FUND_VALID,
    isCampaignFundValid: isCampaignFundValid,
  });
  dispatch({ type: CHANGE_IS_TOWN_VALID, isTownValid: isTownValid });
  dispatch({
    type: CHANGE_IS_CAMPAIGN_NAME_VALID,
    isCampaignNameValid: isCampaignNameValid,
  });
  return (
    isCampaignNameValid &&
    isKeywordsValid &&
    isBidAmountValid &&
    isCampaignFundValid &&
    isTownValid &&
    isRadiusValid &&
    hasUserFunds
  );
}
