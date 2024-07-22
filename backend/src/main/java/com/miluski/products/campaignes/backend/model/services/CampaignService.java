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

/**
 * Service class for managing campaign-related operations.
 * This class provides methods to perform CRUD operations on campaigns,
 * such as retrieving all campaigns for a specific user, saving, deleting,
 * and editing campaigns. It also includes a method to update the user's account
 * balance
 * based on campaign funds.
 */
@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a CampaignService with the specified CampaignRepository and
     * UserRepository.
     * 
     * @param campaignRepository The repository for campaign data access.
     * @param userRepository     The repository for user data access.
     */
    @Autowired
    public CampaignService(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all campaigns associated with a given user ID.
     * 
     * @param id The ID of the user whose campaigns are to be retrieved.
     * @return A list of Campaign objects associated with the user, or null if the
     *         user does not exist.
     */
    public List<Campaign> getAllCampaignsByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? campaignRepository.findByUser(user.get()) : null;
    }

    /**
     * Attempts to save a campaign to the database.
     * 
     * @param campaign The campaign to be saved.
     * @return True if the campaign is successfully saved, false otherwise.
     */
    public Boolean isCampaignSaved(Campaign campaign) {
        try {
            campaignRepository.save(campaign);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Attempts to delete a campaign by its ID.
     * 
     * @param id The ID of the campaign to be deleted.
     * @return True if the campaign is successfully deleted, false otherwise.
     */
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

    /**
     * Edits an existing campaign with new details provided in a CampaignDto object.
     * 
     * @param campaignId  The ID of the campaign to be edited.
     * @param campaignDto The new campaign details.
     * @return True if the campaign is successfully edited, false otherwise.
     */
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

    /**
     * Updates the account balance of the user associated with a campaign.
     * The user's balance is decreased by the campaign fund amount.
     * 
     * @param campaign The campaign for which the user's balance is to be updated.
     */
    public void updateUserBalance(Campaign campaign) {
        User user = campaign.getUser();
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null) {
            dbUser.setAccountBalance(dbUser.getAccountBalance() - campaign.getCampaignFund());
            userRepository.save(dbUser);
        }
    }

}
