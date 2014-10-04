package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: IssueTypeEntity
 *
 */
@Entity
@Table(name = "issue_type")
@Cacheable(false)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class IssueTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IssueTypePKEntity id;	

	@OneToMany(mappedBy = "issueType", fetch = LAZY)
	private Set<IssueEntity> issueEntities;

	public IssueTypeEntity(){
	}

	public IssueTypePKEntity getId() {
		return id;
	}

	public void setId(IssueTypePKEntity id) {
		this.id = id;
	}

	public Set<IssueEntity> getIssues() {
		return issueEntities;
	}

	public void setIssues(Set<IssueEntity> issueEntities) {
		this.issueEntities = issueEntities;
	}	
}
