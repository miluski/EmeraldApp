package com.miluski.products.campaignes.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.miluski.products.campaignes.backend.model.dto.CampaignDto;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.Campaign;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.CampaignMapper;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

class CampaignMapperTest {

    private CampaignMapper campaignMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userMapper = mock(UserMapper.class);
        campaignMapper = new CampaignMapper(userRepository, userMapper);
    }

    @Test
    void testConvertToCampaignDto() {
        Campaign campaign = new Campaign();
        UserDto userDto = new UserDto();
        when(userMapper.convertToUserDto(campaign.getUser())).thenReturn(userDto);
        CampaignDto campaignDto = campaignMapper.convertToCampaignDto(campaign);
        assertEquals(campaign.getBidAmount(), campaignDto.getBidAmount());
        assertEquals(campaign.getCampaignFund(), campaignDto.getCampaignFund());
        assertEquals(campaign.getCampaignName(), campaignDto.getCampaignName());
        assertEquals(campaign.getKeywords(), campaignDto.getKeywords());
        assertEquals(campaign.getRadius(), campaignDto.getRadius());
        assertEquals(campaign.getStatus(), campaignDto.getStatus());
        assertEquals(campaign.getTown(), campaignDto.getTown());
        assertEquals(userDto, campaignDto.getUserDto());
        assertEquals(campaign.getId(), campaignDto.getId());
    }

    @Test
    void testConvertToCampaign() {
        CampaignDto campaignDto = new CampaignDto();
        UserDto userDto = new UserDto(1L, "test", 100d);
        campaignDto.setUserDto(userDto);
        User user = new User();
        when(userMapper.convertToUser(campaignDto.getUserDto())).thenReturn(user);
        assertThrows(NoSuchElementException.class, () -> campaignMapper.convertToCampaign(campaignDto));
    }
}