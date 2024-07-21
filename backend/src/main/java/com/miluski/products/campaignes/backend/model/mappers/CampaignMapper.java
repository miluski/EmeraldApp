package com.miluski.products.campaignes.backend.model.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

@Component
public class CampaignMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public CampaignMapper(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Campaign convertToCampaign(CampaignDto campaignDto) {
        Campaign campaign = new Campaign();
        Optional<User> user = userRepository.findById(campaignDto.getUserDto().getId());
        campaign.setBidAmount(campaignDto.getBidAmount());
        campaign.setCampaignFund(campaignDto.getCampaignFund());
        campaign.setCampaignName(campaignDto.getCampaignName());
        campaign.setKeywords(campaignDto.getKeywords());
        campaign.setRadius(campaignDto.getRadius());
        campaign.setStatus(campaignDto.getStatus());
        campaign.setTown(campaignDto.getTown());
        campaign.setUser(user.get());
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
