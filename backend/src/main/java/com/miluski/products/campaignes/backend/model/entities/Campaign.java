package com.miluski.products.campaignes.backend.model.entities;

import java.util.List;

import jakarta.persistence.*;

/**
 * Represents a campaign entity in the database.
 * This class is an entity model for campaigns, containing various properties
 * like
 * campaign name, keywords, bid amount, campaign fund, status, town, and radius.
 * It is mapped to the "campaignes" table in the database.
 */
@Entity
@Table(name = "campaignes")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "campaign_name", nullable = false)
    private String campaignName;
    @Column(name = "keywords", nullable = false)
    private List<String> keywords;
    @Column(name = "bid_amount", nullable = false)
    private Double bidAmount;
    @Column(name = "campaign_fund", nullable = false)
    private Double campaignFund;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "Town", nullable = false)
    private String town;
    @Column(name = "Radius", nullable = false)
    private Double radius;

    /**
     * Gets the unique identifier of the campaign.
     * 
     * @return The unique identifier of the campaign.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the user associated with the campaign.
     * 
     * @return The user associated with the campaign.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the campaign.
     * 
     * @param user The user to associate with the campaign.
     */
    public void setUser(User user) {
        this.user = user;
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
}