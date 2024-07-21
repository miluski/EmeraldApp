package com.miluski.products.campaignes.backend;
import org.junit.jupiter.api.Test;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class CampaignDtoTest {

    @Test
    void testGetUserDto() {
        UserDto userDto = new UserDto();
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setUserDto(userDto);
        assertEquals(userDto, campaignDto.getUserDto());
    }

    @Test
    void testGetId() {
        Long id = 1L;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setId(id);
        assertEquals(id, campaignDto.getId());
    }

    @Test
    void testGetCampaignName() {
        String campaignName = "Test Campaign";
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCampaignName(campaignName);
        assertEquals(campaignName, campaignDto.getCampaignName());
    }

    @Test
    void testGetKeywords() {
        List<String> keywords = Arrays.asList("keyword1", "keyword2");
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setKeywords(keywords);
        assertEquals(keywords, campaignDto.getKeywords());
    }

    @Test
    void testGetBidAmount() {
        Double bidAmount = 10.0;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setBidAmount(bidAmount);
        assertEquals(bidAmount, campaignDto.getBidAmount());
    }

    @Test
    void testGetCampaignFund() {
        Double campaignFund = 100.0;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCampaignFund(campaignFund);
        assertEquals(campaignFund, campaignDto.getCampaignFund());
    }

    @Test
    void testGetStatus() {
        String status = "Active";
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setStatus(status);
        assertEquals(status, campaignDto.getStatus());
    }

    @Test
    void testGetTown() {
        String town = "Test Town";
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setTown(town);
        assertEquals(town, campaignDto.getTown());
    }

    @Test
    void testGetRadius() {
        Double radius = 5.0;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setRadius(radius);
        assertEquals(radius, campaignDto.getRadius());
    }

    @Test
    void testToString() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testUser");
        userDto.setAccountBalance(100d);
        String campaignName = "Test Campaign";
        List<String> keywords = Arrays.asList("keyword1", "keyword2");
        Double bidAmount = 10.0;
        Double campaignFund = 100.0;
        String status = "Active";
        String town = "Test Town";
        Double radius = 5.0;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setUserDto(userDto);
        campaignDto.setCampaignName(campaignName);
        campaignDto.setKeywords(keywords);
        campaignDto.setBidAmount(bidAmount);
        campaignDto.setCampaignFund(campaignFund);
        campaignDto.setStatus(status);
        campaignDto.setTown(town);
        campaignDto.setRadius(radius);
        String expectedToString = "CampaignDto{" +
                "user=" + userDto.toString() +
                ", campaignName='" + campaignName + '\'' +
                ", keywords=" + keywords +
                ", bidAmount=" + bidAmount +
                ", campaignFund=" + campaignFund +
                ", status='" + status + '\'' +
                ", town='" + town + '\'' +
                ", radius=" + radius +
                '}';
        assertEquals(expectedToString, campaignDto.toString());
    }
}