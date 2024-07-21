package com.miluski.products.campaignes.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.miluski.products.campaignes.backend.model.dto.*;
import com.miluski.products.campaignes.backend.model.mappers.CampaignMapper;
import com.miluski.products.campaignes.backend.model.services.CampaignService;

@RestController
@RequestMapping("/api/campaignes")
public class CampaignController {

    private final CampaignService campaignService;
    private final CampaignMapper campaignMapper;

    @Autowired
    public CampaignController(CampaignService campaignService, CampaignMapper campaignMapper) {
        this.campaignService = campaignService;
        this.campaignMapper = campaignMapper;
    }

    @GetMapping("/")
    public Optional<List<CampaignDto>> handleGetAllCampaignesRequest(@RequestBody UserDto userDto) {
        List<CampaignDto> userCampaignes = campaignService
                .getAllCampaignsByUser(userDto)
                .stream()
                .map(campaignMapper::convertToCampaignDto)
                .collect(Collectors.toList());
        return Optional.ofNullable(userCampaignes);
    }

    @PostMapping("/create")
    public ResponseEntity<?> handleCreateCampaignRequest(@RequestBody CampaignDto campaignDto) {
        Boolean isCampaignSaved = campaignService.isCampaignSaved(campaignMapper.convertToCampaign(campaignDto));
        return isCampaignSaved ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> handleDeleteCampaignRequest(@PathVariable Long id) {
        Boolean isCampaignDeleted = campaignService.isCampaignDeleted(id);
        return isCampaignDeleted ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> handleEditCampaignRequest(@PathVariable Long id, @RequestBody CampaignDto campaignDto) {
        Boolean isCampaignEdited = campaignService.isCampaignEdited(id, campaignDto);
        return isCampaignEdited ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
