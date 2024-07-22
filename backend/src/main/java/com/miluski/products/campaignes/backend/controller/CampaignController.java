package com.miluski.products.campaignes.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.mappers.CampaignMapper;
import com.miluski.products.campaignes.backend.model.services.CampaignService;

/**
 * CampaignController is responsible for handling HTTP requests related to
 * campaign operations.
 * It provides endpoints for creating, retrieving, updating, and deleting
 * campaigns.
 */
@RestController
@RequestMapping("/api/campaignes")
public class CampaignController {

    private final CampaignService campaignService;
    private final CampaignMapper campaignMapper;

    /*
     * Constructs a CampaignController with a CampaignService and CampaignMapper.
     * 
     * @param campaignService The service for campaign-related business logic.
     * 
     * @param campaignMapper The mapper for converting between campaign DTOs and
     * entities.
     */
    @Autowired
    public CampaignController(CampaignService campaignService, CampaignMapper campaignMapper) {
        this.campaignService = campaignService;
        this.campaignMapper = campaignMapper;
    }

    /**
     * Retrieves all campaigns associated with a given user ID.
     * 
     * @param id The ID of the user whose campaigns are to be retrieved.
     * @return An Optional containing a list of CampaignDto objects if found,
     *         otherwise empty.
     */
    @GetMapping("/user/{id}")
    public Optional<List<CampaignDto>> handleGetAllCampaignesRequest(@PathVariable Long id) {
        List<CampaignDto> userCampaignes = campaignService
                .getAllCampaignsByUserId(id)
                .stream()
                .map(campaignMapper::convertToCampaignDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(userCampaignes);
    }

    /**
     * Handles the creation of a new campaign.
     * 
     * @param campaignDto The CampaignDto containing the campaign details to be
     *                    created.
     * @return A ResponseEntity indicating the outcome of the create operation.
     */
    @PostMapping("/create")
    public ResponseEntity<?> handleCreateCampaignRequest(@RequestBody CampaignDto campaignDto) {
        Boolean isCampaignSaved = campaignService.isCampaignSaved(campaignMapper.convertToCampaign(campaignDto));
        campaignService.updateUserBalance(campaignMapper.convertToCampaign(campaignDto));
        return isCampaignSaved ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Handles the deletion of a campaign by its ID.
     * 
     * @param id The ID of the campaign to be deleted.
     * @return A ResponseEntity indicating the outcome of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> handleDeleteCampaignRequest(@PathVariable Long id) {
        Boolean isCampaignDeleted = campaignService.isCampaignDeleted(id);
        return isCampaignDeleted ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /*
     * Handles the request to edit an existing campaign.
     * 
     * @param id The ID of the campaign to be edited.
     * 
     * @param campaignDto The CampaignDto containing the updated campaign details.
     * 
     * @return A ResponseEntity indicating the outcome of the edit operation.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> handleEditCampaignRequest(@PathVariable Long id, @RequestBody CampaignDto campaignDto) {
        Boolean isCampaignEdited = campaignService.isCampaignEdited(id, campaignDto);
        Boolean canUpdateBalance = campaignDto.getCanUpdateBalance();
        if (canUpdateBalance) {
            campaignService.updateUserBalance(campaignMapper.convertToCampaign(campaignDto));
        }
        return isCampaignEdited ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
