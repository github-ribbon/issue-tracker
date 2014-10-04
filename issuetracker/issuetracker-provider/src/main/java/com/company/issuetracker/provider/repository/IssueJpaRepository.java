package com.company.issuetracker.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.issuetracker.provider.domain.IssueEntity;

public interface IssueJpaRepository extends JpaRepository<IssueEntity,Long>{

	@Query(value="SELECT i FROM IssueEntity i LEFT JOIN FETCH i.affectedVersions av"
			+ " LEFT JOIN FETCH i.fixedVersions fv LEFT JOIN FETCH i.responses r LEFT JOIN FETCH r.user u"
			+ " JOIN FETCH i.reporterUser rep LEFT JOIN FETCH i.assigneeUser a"
			+ " WHERE i.issueId=:issueId")
	public IssueEntity findOne(@Param("issueId") long issueId);


	@Query(value="SELECT i FROM IssueEntity i "		
			+ " JOIN FETCH i.reporterUser rep LEFT JOIN FETCH i.assigneeUser a "
			+ " WHERE (i.statusId=:statusId OR :statusId IS NULL) "
			+ " AND (i.priorityId=:priorityId OR :priorityId IS NULL)"
			+ " AND (i.issueTypeId=:issueTypeId OR :issueTypeId IS NULL)"
			+ " AND (i.priorityId=:priorityId OR :priorityId IS NULL)"
			+ " AND (i.reporter=:reporter OR :reporter IS NULL)"
			+ " AND (i.assignee=:assignee OR :assignee IS NULL)"
			+ " AND (i.ownerId=:ownerId OR :ownerId IS NULL)"
			+ " AND (i.projectId=:projectId OR :projectId IS NULL)",			
			countQuery="SELECT count(i) FROM IssueEntity i "		
					+ " JOIN i.reporterUser rep LEFT JOIN i.assigneeUser a "
					+ " WHERE (i.statusId=:statusId OR :statusId IS NULL) "
					+ " AND (i.priorityId=:priorityId OR :priorityId IS NULL)"
					+ " AND (i.issueTypeId=:issueTypeId OR :issueTypeId IS NULL)"
					+ " AND (i.priorityId=:priorityId OR :priorityId IS NULL)"
					+ " AND (i.reporter=:reporter OR :reporter IS NULL)"
					+ " AND (i.assignee=:assignee OR :assignee IS NULL)"
					+ " AND (i.ownerId=:ownerId OR :ownerId IS NULL)"
					+ " AND (i.projectId=:projectId OR :projectId IS NULL)")
	public Page<IssueEntity> findIssues(
			@Param("statusId") String statusId,
			@Param("priorityId") String priorityId, 
			@Param("issueTypeId") String issueTypeId,
			@Param("reporter") String reporter,
			@Param("assignee") String assignee,
			@Param("ownerId") String ownerId, 
			@Param("projectId") String projectId,			
			Pageable pageable);

}

