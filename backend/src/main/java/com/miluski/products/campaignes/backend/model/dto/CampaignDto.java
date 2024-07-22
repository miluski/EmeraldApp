package com.miluski.products.campaignes.backend.model.dto;

import java.util.List;

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

    public UserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Double getCampaignFund() {
        return campaignFund;
    }

    public void setCampaignFund(Double campaignFund) {
        this.campaignFund = campaignFund;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Boolean getCanUpdateBalance() {
        return this.canUpdateBalance;
    }

    public void setCanUpdateBalance(Boolean canUpdateBalance) {
        this.canUpdateBalance = canUpdateBalance;
    }

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
