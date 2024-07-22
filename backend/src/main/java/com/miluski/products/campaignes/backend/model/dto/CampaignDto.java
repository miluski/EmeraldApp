package com.miluski.products.campaignes.backend.model.dto;

import java.util.List;

/**
 * Represents a Data Transfer Object (DTO) for a campaign.
 * This class encapsulates the data for a campaign, including details about the
 * user,
 * campaign name, keywords, bid amount, campaign fund, status, town, and radius.
 */
public class CampaignDto {
    private Long id;
    private UserDto userDto;
    private String campaignName;
    private List<String> keywords;
    private Double bidAmount;
    private Double campaignFund;
    private String status;
    private String town;
    private Double radius;
    private Boolean canUpdateBalance;

    /**
     * Gets the UserDto associated with the campaign.
     * 
     * @return The UserDto object representing the user associated with the
     *         campaign.
     */
    public UserDto getUserDto() {
        return this.userDto;
    }

    /**
     * Sets the UserDto associated with the campaign.
     * 
     * @param userDto The UserDto object to associate with the campaign.
     */
    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    /**
     * Gets the unique identifier of the campaign.
     * 
     * @return The unique identifier of the campaign.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the campaign.
     * 
     * @param id The unique identifier to set for the campaign.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the campaign.
     * 
     * @return The name of the campaign.
     */
    public String getCampaignName() {
        return campaignName;
    }

    /**
     * Sets the name of the campaign.
     * 
     * @param campaignName The name to set for the campaign.
     */
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    /**
     * Gets the list of keywords associated with the campaign.
     * 
     * @return The list of keywords for the campaign.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Sets the list of keywords for the campaign.
     * 
     * @param keywords The list of keywords to set for the campaign.
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Gets the bid amount for the campaign.
     * 
     * @return The bid amount for the campaign.
     */
    public Double getBidAmount() {
        return bidAmount;
    }

    /**
     * Sets the bid amount for the campaign.
     * 
     * @param bidAmount The bid amount to set for the campaign.
     */
    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    /**
     * Gets the fund allocated for the campaign.
     * 
     * @return The campaign fund.
     */
    public Double getCampaignFund() {
        return campaignFund;
    }

    /**
     * Sets the fund allocated for the campaign.
     * 
     * @param campaignFund The campaign fund to set.
     */
    public void setCampaignFund(Double campaignFund) {
        this.campaignFund = campaignFund;
    }

    /**
     * Gets the status of the campaign.
     * 
     * @return The status of the campaign.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the campaign.
     * 
     * @param status The status to set for the campaign.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the town associated with the campaign.
     * 
     * @return The town associated with the campaign.
     */
    public String getTown() {
        return town;
    }

    /**
     * Sets the town associated with the campaign.
     * 
     * @param town The town to set for the campaign.
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Gets the radius of the campaign's target area.
     * 
     * @return The radius of the campaign's target area.
     */
    public Double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the campaign's target area.
     * 
     * @param radius The radius to set for the campaign's target area.
     */
    public void setRadius(Double radius) {
        this.radius = radius;
    }

    /**
     * Checks if the campaign's balance can be updated.
     * 
     * @return True if the campaign's balance can be updated, false otherwise.
     */
    public Boolean getCanUpdateBalance() {
        return this.canUpdateBalance;
    }

    /**
     * Sets the ability to update the campaign's balance.
     * 
     * @param canUpdateBalance True to allow updating the campaign's balance, false
     *                         to disallow.
     */
    public void setCanUpdateBalance(Boolean canUpdateBalance) {
        this.canUpdateBalance = canUpdateBalance;
    }

    /**
     * Returns a string representation of the CampaignDto.
     * This includes the user, campaign name, keywords, bid amount, campaign fund,
     * status, town, and radius.
     * 
     * @return A string representation of the CampaignDto.
     */
    @Override
    public String toString() {
        return "CampaignDto{" +
                "user=" + userDto.toString() +
                ", campaignName='" + campaignName + '\'' +
                ", keywords=" + keywords +
                ", bidAmount=" + bidAmount +
                ", campaignFund=" + campaignFund +
                ", status='" + status + '\'' +
                ", town='" + town + '\'' +
                ", radius=" + radius +
                '}';
    }
}