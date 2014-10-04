package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.VersionJpaRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * Entity implementation class for Entity: VersionEntity
 *
 */
@Entity
@Table(name = "version")
@Cacheable(false)
//@Configurable
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class VersionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VersionPKEntity id;

	@ManyToOne(fetch = LAZY)	
	@JoinColumns({
		@JoinColumn(name="owner_id", referencedColumnName="owner_id",insertable=false,updatable=false),
		@JoinColumn(name="project_id", referencedColumnName="project_id",insertable=false,updatable=false)
	})	
	private ProjectEntity project;

	@OneToMany(mappedBy = "version", fetch = LAZY)
	private Set<AffectedVersionEntity> affectedVersionEntities;

	@OneToMany(mappedBy = "version", fetch = LAZY)
	private Set<FixedVersionEntity> fixedVersionEntities;

	@Autowired
	@Transient
	private VersionJpaRepository versionRepository;

	public VersionEntity(){	
	}

	public VersionEntity(VersionPKEntity id){
		this.id=id;
	}

	public VersionPKEntity getId() {
		return id;
	}

	public void setId(VersionPKEntity id) {
		this.id = id;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity projectEntity) {
		this.project = projectEntity;
	}

	public Set<AffectedVersionEntity> getAffectedVersions() {
		return affectedVersionEntities;
	}

	public void setAffectedVersions(Set<AffectedVersionEntity> affectedVersionEntities) {
		this.affectedVersionEntities = affectedVersionEntities;
	}

	public Set<FixedVersionEntity> getFixedVersions() {
		return fixedVersionEntities;
	}

	public void setFixedVersions(Set<FixedVersionEntity> fixedVersionEntities) {
		this.fixedVersionEntities = fixedVersionEntities;
	}

	@Transactional(readOnly=true)
	public VersionEntity get(VersionPKEntity versionPKEntity){
		return versionRepository.findOne(versionPKEntity);
	}

	@Transactional
	public VersionEntity save(VersionEntity versionEntity){
		return versionRepository.save(versionEntity);
	}

	@Transactional
	public void delete(VersionPKEntity versionPKEntity){
		versionRepository.delete(versionPKEntity);
	}

	@Transactional(readOnly=true)
	public boolean exists(VersionPKEntity versionPKEntity){
		return versionRepository.exists(versionPKEntity);
	}

	@Transactional(readOnly=true)
	public List<VersionEntity> getNotFixed(String ownerId, String projectId, long issueId){
		return versionRepository.findNotFixedVersions(ownerId,projectId,issueId);
	}

	@Transactional(readOnly=true)
	public List<VersionEntity> getNotAffected(String ownerId, String projectId, long issueId){
		return versionRepository.findNotAffectedVersions(ownerId,projectId,issueId);
	}
}
