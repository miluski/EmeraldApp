package com.miluski.products.campaignes.backend.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.*;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.*;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, UserRepository userRepository,
            UserMapper userMapper) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<Campaign> getAllCampaignsByUser(UserDto userDto) {
        User user = userMapper.convertToUser(userDto);
        return campaignRepository.findByUser(user);
    }

    public Boolean isCampaignSaved(Campaign campaign) {
        try {
            campaignRepository.save(campaign);
            User user = campaign.getUser();
            User dbUser = userRepository.findByUsername(user.getUsername());
            if (dbUser != null) {
                user.setAccountBalance(user.getAccountBalance() - campaign.getCampaignFund());
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean isCampaignDeleted(Long id) {
        try {
            Optional<Campaign> campaign = campaignRepository.findById(id);
            if (campaign.isPresent()) {
                campaignRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean isCampaignEdited(Long campaignId, CampaignDto campaignDto) {
        Optional<Campaign> campaign = campaignRepository.findById(campaignId);
        if (campaign.isPresent()) {
            Campaign retrievedCampaign = campaign.get();
            if (campaignDto.getCampaignName() != null) {
                retrievedCampaign.setCampaignName(campaignDto.getCampaignName());
            }
            if (campaignDto.getKeywords() != null) {
                retrievedCampaign.setKeywords(campaignDto.getKeywords());
            }
            if (campaignDto.getBidAmount() != null) {
                retrievedCampaign.setBidAmount(campaignDto.getBidAmount());
            }
            if (campaignDto.getCampaignFund() != null) {
                retrievedCampaign.setCampaignFund(campaignDto.getCampaignFund());
            }
            if (campaignDto.getStatus() != null) {
                retrievedCampaign.setStatus(campaignDto.getStatus());
            }
            if (campaignDto.getTown() != null) {
                retrievedCampaign.setTown(campaignDto.getTown());
            }
            if (campaignDto.getRadius() != null) {
                retrievedCampaign.setRadius(campaignDto.getRadius());
            }
            campaignRepository.save(retrievedCampaign);
            return true;
        }
        return false;
    }
}
