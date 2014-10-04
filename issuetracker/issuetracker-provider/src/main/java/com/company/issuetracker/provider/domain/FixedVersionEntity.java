package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.FixedVersionJpaRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: FixedVersionEntity
 *
 */
@Entity
@Table(name="fixed_version")
@Cacheable(false)
//@Configurable
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class FixedVersionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FixedVersionPKEntity id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name="issue_id", referencedColumnName="issue_id",insertable=false,updatable=false)		
	private IssueEntity issue;

	@ManyToOne(fetch = LAZY)	
	@JoinColumns({
		@JoinColumn(name="owner_id", referencedColumnName="owner_id",insertable=false,updatable=false),
		@JoinColumn(name="project_id", referencedColumnName="project_id",insertable=false,updatable=false),
		@JoinColumn(name="version_id", referencedColumnName="version_id",insertable=false,updatable=false)
	})	
	private VersionEntity version;

	@Autowired
	@Transient
	private FixedVersionJpaRepository fixedVersionRepository;

	public FixedVersionEntity(){
	}

	public FixedVersionPKEntity getId() {
		return id;
	}

	public void setId(FixedVersionPKEntity id) {
		this.id = id;
	}

	public IssueEntity getIssue() {
		return issue;
	}

	public void setIssue(IssueEntity issueEntity) {
		this.issue = issueEntity;
	}

	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity versionEntity) {
		this.version = versionEntity;
	}	

	@Transactional(readOnly=true)
	public FixedVersionEntity get(FixedVersionPKEntity fixedVersionPKEntity){
		return fixedVersionRepository.findOne(fixedVersionPKEntity);
	}

	@Transactional
	public FixedVersionEntity save(FixedVersionEntity fixedVersionEntity){
		return fixedVersionRepository.save(fixedVersionEntity);
	}

	@Transactional
	public void delete(FixedVersionPKEntity fixedVersionPKEntity){
		fixedVersionRepository.delete(fixedVersionPKEntity);
	}

	@Transactional(readOnly=true)
	public boolean exists(FixedVersionPKEntity fixedVersionPKEntity){
		return fixedVersionRepository.exists(fixedVersionPKEntity);
	}
}
