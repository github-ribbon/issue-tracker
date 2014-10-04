package com.company.issuetracker.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.issuetracker.provider.domain.FixedVersionEntity;
import com.company.issuetracker.provider.domain.FixedVersionPKEntity;

public interface FixedVersionJpaRepository 
extends JpaRepository<FixedVersionEntity,FixedVersionPKEntity>{

}
