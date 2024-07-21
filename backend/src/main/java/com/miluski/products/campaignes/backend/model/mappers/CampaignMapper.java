package com.miluski.products.campaignes.backend.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miluski.products.campaignes.backend.model.dto.*;
import com.miluski.products.campaignes.backend.model.entities.*;

@Component
public class CampaignMapper {

    private final UserMapper userMapper;

    @Autowired
    public CampaignMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Campaign convertToCampaign(CampaignDto campaignDto) {
        Campaign campaign = new Campaign();
        User user = userMapper.convertToUser(campaignDto.getUserDto());
        campaign.setBidAmount(campaignDto.getBidAmount());
        campaign.setCampaignFund(campaignDto.getCampaignFund());
        campaign.setCampaignName(campaignDto.getCampaignName());
        campaign.setKeywords(campaignDto.getKeywords());
        campaign.setRadius(campaignDto.getRadius());
        campaign.setStatus(campaignDto.getStatus());
        campaign.setTown(campaignDto.getTown());
        campaign.setUser(user);
        return campaign;
    }

    public CampaignDto convertToCampaignDto(Campaign campaign) {
        CampaignDto campaignDto = new CampaignDto();
        UserDto userDto = userMapper.convertToUserDto(campaign.getUser());
        campaignDto.setBidAmount(campaign.getBidAmount());
        campaignDto.setCampaignFund(campaign.getCampaignFund());
        campaignDto.setCampaignName(campaign.getCampaignName());
        campaignDto.setKeywords(campaign.getKeywords());
        campaignDto.setRadius(campaign.getRadius());
        campaignDto.setStatus(campaign.getStatus());
        campaignDto.setTown(campaign.getTown());
        campaignDto.setUserDto(userDto);
        campaignDto.setId(campaign.getId());
        return campaignDto;
    }
    
}
