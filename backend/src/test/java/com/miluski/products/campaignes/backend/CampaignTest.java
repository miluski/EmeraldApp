package com.miluski.products.campaignes.backend;

import org.junit.jupiter.api.Test;

import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CampaignTest {

    @Test
    void testGetUser() {
        User user = new User();
        Campaign campaign = new Campaign();
        campaign.setUser(user);
        assertEquals(user, campaign.getUser());
    }

    @Test
    void testGetCampaignName() {
        String campaignName = "Test Campaign";
        Campaign campaign = new Campaign();
        campaign.setCampaignName(campaignName);
        assertEquals(campaignName, campaign.getCampaignName());
    }

    @Test
    void testGetKeywords() {
        List<String> keywords = Arrays.asList("keyword1", "keyword2");
        Campaign campaign = new Campaign();
        campaign.setKeywords(keywords);
        assertEquals(keywords, campaign.getKeywords());
    }

    @Test
    void testGetBidAmount() {
        Double bidAmount = 10.0;
        Campaign campaign = new Campaign();
        campaign.setBidAmount(bidAmount);
        assertEquals(bidAmount, campaign.getBidAmount());
    }

    @Test
    void testGetCampaignFund() {
        Double campaignFund = 100.0;
        Campaign campaign = new Campaign();
        campaign.setCampaignFund(campaignFund);
        assertEquals(campaignFund, campaign.getCampaignFund());
    }

    @Test
    void testGetStatus() {
        String status = "Active";
        Campaign campaign = new Campaign();
        campaign.setStatus(status);
        assertEquals(status, campaign.getStatus());
    }

    @Test
    void testGetTown() {
        String town = "Test Town";
        Campaign campaign = new Campaign();
        campaign.setTown(town);
        assertEquals(town, campaign.getTown());
    }

    @Test
    void testGetRadius() {
        Double radius = 5.0;
        Campaign campaign = new Campaign();
        campaign.setRadius(radius);
        assertEquals(radius, campaign.getRadius());
    }
}