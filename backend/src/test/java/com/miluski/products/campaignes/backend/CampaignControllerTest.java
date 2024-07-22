package com.miluski.products.campaignes.backend;

import com.miluski.products.campaignes.backend.controller.CampaignController;
import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.mappers.CampaignMapper;
import com.miluski.products.campaignes.backend.model.services.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignControllerTest {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignService campaignService;

    @Mock
    private CampaignMapper campaignMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleGetAllCampaignesRequest_ValidUserDto_ReturnsListOfCampaignDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        List<Campaign> expectedCampaignes = Arrays.asList(new Campaign(), new Campaign());
        when(campaignService.getAllCampaignsByUserId(userDto.getId())).thenReturn(expectedCampaignes);
        when(campaignMapper.convertToCampaignDto(any())).thenReturn(new CampaignDto());
        Optional<List<CampaignDto>> result = campaignController.handleGetAllCampaignesRequest(userDto.getId());
        assertTrue(result.isPresent());
        assertEquals(expectedCampaignes.size(), result.get().size());
        verify(campaignService, times(1)).getAllCampaignsByUserId(userDto.getId());
        verify(campaignMapper, times(expectedCampaignes.size())).convertToCampaignDto(any());
    }

    @Test
    void handleCreateCampaignRequest_ValidCampaignDto_ReturnsHttpStatusOk() {
        CampaignDto campaignDto = new CampaignDto();
        when(campaignMapper.convertToCampaign(campaignDto)).thenReturn(new Campaign());
        when(campaignService.isCampaignSaved(any())).thenReturn(true);
        ResponseEntity<?> responseEntity = campaignController.handleCreateCampaignRequest(campaignDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignSaved(any());
    }

    @Test
    void handleCreateCampaignRequest_ValidCampaignDto_ReturnsHttpStatusForbidden() {
        CampaignDto campaignDto = new CampaignDto();
        when(campaignMapper.convertToCampaign(campaignDto)).thenReturn(new Campaign());
        when(campaignService.isCampaignSaved(any())).thenReturn(false);
        ResponseEntity<?> responseEntity = campaignController.handleCreateCampaignRequest(campaignDto);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignSaved(any());
    }

    @Test
    void handleDeleteCampaignRequest_ValidId_ReturnsHttpStatusOk() {
        Long id = 1L;
        when(campaignService.isCampaignDeleted(id)).thenReturn(true);
        ResponseEntity<?> responseEntity = campaignController.handleDeleteCampaignRequest(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignDeleted(id);
    }

    @Test
    void handleDeleteCampaignRequest_ValidId_ReturnsHttpStatusNotFound() {
        Long id = 1L;
        when(campaignService.isCampaignDeleted(id)).thenReturn(false);
        ResponseEntity<?> responseEntity = campaignController.handleDeleteCampaignRequest(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignDeleted(id);
    }

    @Test
    void handleEditCampaignRequest_ValidIdAndCampaignDto_ReturnsHttpStatusOk() {
        Long id = 1L;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCanUpdateBalance(false);
        when(campaignService.isCampaignEdited(id, campaignDto)).thenReturn(true);
        ResponseEntity<?> responseEntity = campaignController.handleEditCampaignRequest(id, campaignDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignEdited(id, campaignDto);
    }

    @Test
    void handleEditCampaignRequest_ValidIdAndCampaignDto_ReturnsHttpStatusNotFound() {
        Long id = 1L;
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCanUpdateBalance(false);
        when(campaignService.isCampaignEdited(id, campaignDto)).thenReturn(false);
        ResponseEntity<?> responseEntity = campaignController.handleEditCampaignRequest(id, campaignDto);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(campaignService, times(1)).isCampaignEdited(id, campaignDto);
    }
}