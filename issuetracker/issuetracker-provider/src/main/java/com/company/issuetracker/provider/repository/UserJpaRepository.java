package com.company.issuetracker.provider.repository;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.company.issuetracker.provider.domain.UserEntity;
import com.company.issuetracker.provider.domain.UserPKEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity,UserPKEntity>{

	/*
	@Query(value="SELECT u FROM UserEntity u JOIN FETCH u.projects p",
			countQuery="SELECT count(u) FROM UserEntity u JOIN u.projects p")	
	public Page<UserEntity> find(Pageable pageable);
	*/

}
