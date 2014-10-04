package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.ProjectJpaRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * Entity implementation class for Entity: ProjectEntity
 *
 */
@Entity
@Table(name = "project")
@Cacheable(false)
//@Configurable
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class ProjectEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProjectPKEntity id;

	@Column(name = "description", nullable = true, length = 500)
	private String description;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="owner_id", referencedColumnName="user_id",insertable=false,updatable=false)		
	private UserEntity owner;

	@OneToMany(mappedBy = "project", fetch = LAZY)
	@OrderBy
	private Set<VersionEntity> versions;

	@OneToMany(mappedBy = "project", fetch = LAZY)
	private Set<IssueEntity> issues;

	@Autowired
	@Transient
	private ProjectJpaRepository projectRepository;

	public ProjectEntity(){	
	}

	public ProjectEntity(ProjectPKEntity id){
		this.id=id;
	}

	public ProjectPKEntity getId() {
		return id;
	}

	public void setId(ProjectPKEntity id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public Set<VersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(Set<VersionEntity> versionEntities) {
		this.versions = versionEntities;
	}

	public Set<IssueEntity> getIssues() {
		return issues;
	}

	public void setIssues(Set<IssueEntity> issueEntities) {
		this.issues = issueEntities;
	}

	@Transactional(readOnly=true)
	public ProjectEntity get(ProjectPKEntity projectPKEntity){
		return projectRepository.findWithVersions(projectPKEntity);
	}

	@Transactional(readOnly=true)
	public long getNumOfIssues(ProjectPKEntity projectPKEntity){
		return projectRepository.getNumOfIssues(projectPKEntity.getOwnerId(), projectPKEntity.getProjectId());
	}

	@Transactional
	public ProjectEntity save(ProjectEntity projectEntity){
		return projectRepository.save(projectEntity);
	}

	@Transactional
	public void delete(ProjectPKEntity projectPKEntity){
		projectRepository.delete(projectPKEntity);
	}

	@Transactional(readOnly=true)
	public boolean exists(ProjectPKEntity projectPKEntity){
		return projectRepository.exists(projectPKEntity);
	}

	@Transactional(readOnly=true)
	public Page<ProjectEntity> getProjects(String ownerId, Pageable pageable){

		if(ownerId!=null && ownerId.isEmpty())
			ownerId=null;

		return projectRepository.findProjects(ownerId, pageable);
	}

	@Transactional(readOnly=true)
	@JsonIgnore
	public List<ProjectEntity> getAllProjects(){
		return projectRepository.findAll();
	}

	public boolean hasDependentObjects(ProjectPKEntity projectPKEntity){
		return false;
	}
}
