package com.miluski.products.campaignes.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.CampaignRepository;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;
import com.miluski.products.campaignes.backend.model.services.CampaignService;

class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsCampaignSaved() {
        Campaign campaign = new Campaign();
        User user = new User();
        campaign.setUser(user);
        campaign.setCampaignName("Example Campaign");
        campaign.setKeywords(Arrays.asList("keyword1", "keyword2"));
        campaign.setBidAmount(50.00);
        campaign.setCampaignFund(500.00);
        campaign.setStatus("ACTIVE");
        campaign.setTown("ExampleTown");
        campaign.setRadius(10.0);
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Boolean result = campaignService.isCampaignSaved(campaign);
        assertTrue(result);
        System.out.println(user.getAccountBalance());
        assertEquals(user.getAccountBalance() - campaign.getCampaignFund(), -300);
        verify(campaignRepository).save(campaign);
    }

    @Test
    void testIsCampaignSaved_whenExceptionThrown() {
        Campaign campaign = new Campaign();
        User user = new User();
        campaign.setUser(user);
        when(campaignRepository.save(campaign)).thenThrow(new RuntimeException("Save failed"));
        Boolean result = campaignService.isCampaignSaved(campaign);
        assertFalse(result);
        verify(campaignRepository).save(campaign);
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testIsCampaignDeleted() {
        Long id = 1L;
        Optional<Campaign> campaign = Optional.of(new Campaign());
        when(campaignRepository.findById(id)).thenReturn(campaign);
        Boolean result = campaignService.isCampaignDeleted(id);
        assertTrue(result);
        verify(campaignRepository).findById(id);
        verify(campaignRepository).deleteById(id);
    }

    @Test
    void testIsCampaignDeleted_whenCampaignNotFound() {
        Long id = 1L;
        Optional<Campaign> campaign = Optional.empty();
        when(campaignRepository.findById(id)).thenReturn(campaign);
        Boolean result = campaignService.isCampaignDeleted(id);
        assertFalse(result);
        verify(campaignRepository).findById(id);
        verify(campaignRepository, never()).deleteById(id);
    }

    @Test
    void testIsCampaignEdited() {
        Long campaignId = 1L;
        CampaignDto campaignDto = new CampaignDto();
        Campaign campaign = new Campaign();
        Optional<Campaign> optionalCampaign = Optional.of(campaign);
        when(campaignRepository.findById(campaignId)).thenReturn(optionalCampaign);
        Boolean result = campaignService.isCampaignEdited(campaignId, campaignDto);
        assertTrue(result);
        verify(campaignRepository).findById(campaignId);
        verify(campaignRepository).save(campaign);
    }

    @Test
    void testIsCampaignEdited_whenCampaignNotFound() {
        Long campaignId = 1L;
        CampaignDto campaignDto = new CampaignDto();
        Optional<Campaign> optionalCampaign = Optional.empty();
        when(campaignRepository.findById(campaignId)).thenReturn(optionalCampaign);
        Boolean result = campaignService.isCampaignEdited(campaignId, campaignDto);
        assertFalse(result);
        verify(campaignRepository).findById(campaignId);
        verify(campaignRepository, never()).save(any(Campaign.class));
    }
}