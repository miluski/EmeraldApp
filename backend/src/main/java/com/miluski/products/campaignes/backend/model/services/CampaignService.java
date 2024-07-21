package com.miluski.products.campaignes.backend.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.repositories.CampaignRepository;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    public List<Campaign> getAllCampaignsByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? campaignRepository.findByUser(user.get()) : null;
    }

    public Boolean isCampaignSaved(Campaign campaign) {
        try {
            campaignRepository.save(campaign);
            return true;
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

    public void updateUserBalance(Campaign campaign) {
        User user = campaign.getUser();
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null) {
            dbUser.setAccountBalance(dbUser.getAccountBalance() - campaign.getCampaignFund());
            userRepository.save(dbUser);
        }
    }
}
