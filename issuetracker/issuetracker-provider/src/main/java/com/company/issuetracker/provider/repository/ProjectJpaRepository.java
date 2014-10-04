package com.company.issuetracker.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;


public interface ProjectJpaRepository extends JpaRepository<ProjectEntity,ProjectPKEntity>{

	@Query(value="SELECT p FROM ProjectEntity p LEFT JOIN FETCH p.versions v "
			+ "WHERE p.id=:projectPK ORDER BY v.id.versionId")
	public ProjectEntity findWithVersions(@Param("projectPK") ProjectPKEntity projectPKEntity);

	@Query(value="SELECT count(i) FROM IssueEntity i WHERE i.ownerId=:ownerId AND i.projectId=:projectId")
	public long getNumOfIssues(@Param("ownerId") String ownerId, @Param("projectId") String projectId);

	@Query(value="SELECT DISTINCT p FROM ProjectEntity p "
			+ "LEFT JOIN FETCH p.owner o LEFT JOIN FETCH p.versions v WHERE p.id.ownerId=:ownerId OR :ownerId IS NULL",
			countQuery="SELECT count(p) FROM ProjectEntity p WHERE p.id.ownerId=:ownerId OR :ownerId IS NULL")
	public Page<ProjectEntity> findProjects(@Param("ownerId") String ownerId, Pageable pageable);

}
