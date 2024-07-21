import { Campaign } from "./Campaign";
import {
  CHANGE_BID_AMOUNT,
  CHANGE_CAMPAIGN_FUND,
  CHANGE_CAMPAIGN_NAME,
  CHANGE_IS_BID_AMOUNT_VALID,
  CHANGE_IS_CAMPAIGN_ADDED,
  CHANGE_IS_CAMPAIGN_FUND_VALID,
  CHANGE_IS_CAMPAIGN_NAME_VALID,
  CHANGE_IS_KEYWORDS_VALID,
  CHANGE_IS_TOWN_VALID,
  CHANGE_KEYWORDS,
  CHANGE_RADIUS,
  CHANGE_STATUS,
  CHANGE_TOWN,
  RESET_CAMPAIGN_REDUCER,
} from "./CampaignActionTypes";

const initialState = {
  campaignName: "",
  keywords: ["promotion", "campaign"],
  bidAmount: 500,
  campaignFund: 500,
  status: true,
  town: "Kraków",
  radius: 1,
  isCampaignNameValid: undefined,
  isKeywordsValid: undefined,
  isBidAmountValid: undefined,
  isCampaignFundValid: undefined,
  isTownValid: undefined,
  isCampaignAdded: false,
};

export function campaignReducer(state = initialState, action: Campaign) {
  switch (action.type) {
    case CHANGE_CAMPAIGN_NAME:
      return {
        ...state,
        campaignName: action.campaignName,
      };
    case CHANGE_KEYWORDS:
      return {
        ...state,
        keywords: action.keywords,
      };
    case CHANGE_BID_AMOUNT:
      return {
        ...state,
        bidAmount: action.bidAmount,
      };
    case CHANGE_CAMPAIGN_FUND:
      return {
        ...state,
        campaignFund: action.campaignFund,
      };
    case CHANGE_STATUS:
      return {
        ...state,
        status: action.status,
      };
    case CHANGE_TOWN:
      return {
        ...state,
        town: action.town,
      };
    case CHANGE_RADIUS:
      return {
        ...state,
        radius: action.radius,
      };
    case CHANGE_IS_CAMPAIGN_NAME_VALID:
      return {
        ...state,
        isCampaignNameValid: action.isCampaignNameValid,
      };
    case CHANGE_IS_KEYWORDS_VALID:
      return {
        ...state,
        isKeywordsValid: action.isKeywordsValid,
      };
    case CHANGE_IS_BID_AMOUNT_VALID:
      return {
        ...state,
        isBidAmountValid: action.isBidAmountValid,
      };
    case CHANGE_IS_CAMPAIGN_FUND_VALID:
      return {
        ...state,
        isCampaignFundValid: action.isCampaignFundValid,
      };
    case CHANGE_IS_TOWN_VALID:
      return {
        ...state,
        isTownValid: action.isTownValid,
      };
    case CHANGE_IS_CAMPAIGN_ADDED:
      return {
        ...state,
        isCampaignAdded: action.isCampaignAdded,
      };
    case RESET_CAMPAIGN_REDUCER:
      return initialState;
    default:
      return state;
  }
}
