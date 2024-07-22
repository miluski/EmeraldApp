import { Reducer } from "redux";
import { User } from "./User";

export type Campaign = {
    type?: string;
    id?: number;
    campaignName: string;
    keywords: string[];
    bidAmount: number;
    campaignFund: number;
    status: boolean;
    town: string;
    radius: number;
    userDto?: User;
    campaignToEdit?: Campaign;
    isCampaignNameValid?: boolean;
    isKeywordsValid?: boolean;
    isBidAmountValid?: boolean;
    isCampaignFundValid?: boolean;
    isStatusValid?: boolean;
    isTownValid?: boolean;
    isRadiusValid?: boolean;
    isCampaignAdded?: boolean;
    canUpdateBalance?: boolean;
    campaignReducer?: Reducer<Campaign, Campaign>;
}