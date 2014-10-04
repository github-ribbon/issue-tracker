package com.company.issuetracker.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.issuetracker.provider.domain.VersionEntity;
import com.company.issuetracker.provider.domain.VersionPKEntity;


public interface VersionJpaRepository extends JpaRepository<VersionEntity,VersionPKEntity>{

	@Query(value="SELECT v FROM VersionEntity v LEFT JOIN v.fixedVersionEntities fv "
			+ "WHERE v.id.ownerId=:ownerId AND v.id.projectId=:projectId "
			+ "AND (fv IS NULL OR fv.id.issueId!=:issueId) ORDER BY v.id.versionId")
	public List<VersionEntity> findNotFixedVersions(@Param("ownerId") String ownerId,
			@Param("projectId") String projectId, @Param("issueId") long issueId);

	@Query(value="SELECT v FROM VersionEntity v LEFT JOIN v.affectedVersionEntities av "
			+ "WHERE v.id.ownerId=:ownerId AND v.id.projectId=:projectId "
			+ "AND (av IS NULL OR av.id.issueId!=:issueId) ORDER BY v.id.versionId")
	public List<VersionEntity> findNotAffectedVersions(@Param("ownerId") String ownerId,
			@Param("projectId") String projectId, @Param("issueId") long issueId);
}

