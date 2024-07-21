package com.miluski.products.campaignes.backend.model.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="campaignes")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @Column(name="campaign_name", nullable = false)
    private String campaignName;
    @Column(name="keywords", nullable = false)
    private List<String> keywords;
    @Column(name="bid_amount", nullable = false)
    private Double bidAmount;
    @Column(name="campaign_fund", nullable = false)
    private Double campaignFund;
    @Column(name="status", nullable = false)
    private String status;
    @Column(name = "Town", nullable = false)
    private String town;
    @Column(name = "Radius", nullable = false)
    private Double radius;

    public Long getId() {
        return this.id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
}
