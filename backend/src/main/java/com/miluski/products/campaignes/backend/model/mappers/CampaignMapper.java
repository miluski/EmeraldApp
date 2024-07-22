package com.miluski.products.campaignes.backend.model.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

/**
 * Component responsible for mapping between Campaign entities and Campaign
 * DTOs.
 * This class provides functionality to convert Campaign DTOs to Campaign
 * entities
 * and vice versa, facilitating the transfer of data between the application's
 * service layer and database layer.
 */
@Component
public class CampaignMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructs a CampaignMapper with the specified UserRepository and UserMapper.
     * 
     * @param userRepository The UserRepository used for accessing user data.
     * @param userMapper     The UserMapper used for converting between User
     *                       entities and User DTOs.
     */
    @Autowired
    public CampaignMapper(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Converts a CampaignDto to a Campaign entity.
     * This method retrieves the User entity associated with the CampaignDto,
     * sets all relevant fields from the CampaignDto to the Campaign entity,
     * and returns the populated Campaign entity.
     * 
     * @param campaignDto The CampaignDto to convert to a Campaign entity.
     * @return The Campaign entity populated with data from the CampaignDto.
     */
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

    /**
     * Converts a Campaign entity to a CampaignDto.
     * This method converts the User entity associated with the Campaign to a
     * UserDto,
     * sets all relevant fields from the Campaign entity to the CampaignDto,
     * and returns the populated CampaignDto.
     * 
     * @param campaign The Campaign entity to convert to a CampaignDto.
     * @return The CampaignDto populated with data from the Campaign entity.
     */
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
