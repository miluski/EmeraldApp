package com.miluski.products.campaignes.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.CampaignMapper;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignMapperTest {

    private CampaignMapper campaignMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        campaignMapper = new CampaignMapper(userMapper);
    }

    @Test
    void testConvertToCampaign() {
        CampaignDto campaignDto = new CampaignDto();
        User user = new User();
        when(userMapper.convertToUser(campaignDto.getUserDto())).thenReturn(user);
        Campaign campaign = campaignMapper.convertToCampaign(campaignDto);
        assertEquals(campaignDto.getBidAmount(), campaign.getBidAmount());
        assertEquals(campaignDto.getCampaignFund(), campaign.getCampaignFund());
        assertEquals(campaignDto.getCampaignName(), campaign.getCampaignName());
        assertEquals(campaignDto.getKeywords(), campaign.getKeywords());
        assertEquals(campaignDto.getRadius(), campaign.getRadius());
        assertEquals(campaignDto.getStatus(), campaign.getStatus());
        assertEquals(campaignDto.getTown(), campaign.getTown());
        assertEquals(user, campaign.getUser());
    }

    @Test
    void testConvertToCampaignDto() {
        Campaign campaign = new Campaign();
        UserDto userDto = new UserDto();
        when(userMapper.convertToUserDto(campaign.getUser())).thenReturn(userDto);
        CampaignDto campaignDto = campaignMapper.convertToCampaignDto(campaign);
        assertEquals(campaign.getBidAmount(), campaignDto.getBidAmount());
        assertEquals(campaign.getCampaignFund(), campaignDto.getCampaignFund());
        assertEquals(campaign.getCampaignName(), campaignDto.getCampaignName());
        assertEquals(campaign.getKeywords(), campaignDto.getKeywords());
        assertEquals(campaign.getRadius(), campaignDto.getRadius());
        assertEquals(campaign.getStatus(), campaignDto.getStatus());
        assertEquals(campaign.getTown(), campaignDto.getTown());
        assertEquals(userDto, campaignDto.getUserDto());
        assertEquals(campaign.getId(), campaignDto.getId());
    }
}