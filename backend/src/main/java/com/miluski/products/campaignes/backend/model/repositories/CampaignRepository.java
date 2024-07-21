package com.miluski.products.campaignes.backend.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miluski.products.campaignes.backend.model.entities.*;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByUser(User user);
}
