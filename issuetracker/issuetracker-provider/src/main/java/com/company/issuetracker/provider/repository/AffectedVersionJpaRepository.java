package com.company.issuetracker.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.issuetracker.provider.domain.AffectedVersionEntity;
import com.company.issuetracker.provider.domain.AffectedVersionPKEntity;


public interface AffectedVersionJpaRepository 
extends JpaRepository<AffectedVersionEntity,AffectedVersionPKEntity>{

}
