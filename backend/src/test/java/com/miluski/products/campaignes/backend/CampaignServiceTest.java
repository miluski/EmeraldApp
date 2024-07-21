package com.miluski.products.campaignes.backend;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.CampaignRepository;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;
import com.miluski.products.campaignes.backend.model.services.CampaignService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    void testGetAllCampaignsByUser() {
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.convertToUser(userDto)).thenReturn(user);
        List<Campaign> campaigns = new ArrayList<>();
        when(campaignRepository.findByUser(user)).thenReturn(campaigns);
        List<Campaign> result = campaignService.getAllCampaignsByUser(userDto);
        assertEquals(campaigns, result);
        verify(userMapper).convertToUser(userDto);
        verify(campaignRepository).findByUser(user);
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
        assertEquals(user.getAccountBalance() - campaign.getCampaignFund(), -800);
        verify(campaignRepository).save(campaign);
        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testIsCampaignSaved_whenUserNotFound() {
        Campaign campaign = new Campaign();
        User user = new User();
        campaign.setUser(user);
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        Boolean result = campaignService.isCampaignSaved(campaign);
        assertFalse(result);
        verify(campaignRepository).save(campaign);
        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository, never()).save(user);
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