package com.company.issuetracker.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.issuetracker.provider.domain.ResponseEntity;

public interface ResponseJpaRepository extends JpaRepository<ResponseEntity,Long>{

	public Page<ResponseEntity> findByUserId(String userId, Pageable pageable);
}

