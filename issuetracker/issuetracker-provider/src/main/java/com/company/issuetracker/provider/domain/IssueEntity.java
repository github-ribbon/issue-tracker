package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.IssueJpaRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: IssueEntity
 *
 */
@Entity
@Table(name="issue")
//@IdClass(IssuePKEntity.class)
@Cacheable(false)
//@Configurable
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class IssueEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id   
	@Column(name = "issue_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq")
	@SequenceGenerator(name = "issue_seq", allocationSize = 1, sequenceName = "issue_seq")
	private long issueId;	

	@Column(nullable=false, name = "owner_id", length = 20)
	private String ownerId;

	@Column(nullable=false, name = "project_id", length = 20)
	private String projectId;	

	@Column(nullable=false,name = "reporter", length = 20)
	private String reporter;

	@Column(nullable=true,name = "assignee", length = 20)
	private String assignee;

	@Column(nullable=false, name = "status_id", length = 1)
	private String statusId;

	@Column(nullable=false, name = "priority_id", length = 1)
	private String priorityId;

	@Column(nullable=false, name = "issue_type_id", length = 1)
	private String issueTypeId;

	@Column(name = "title", nullable = false, length = 50)
	private String title;

	@Column(name = "content", nullable = false, length = 500)
	private String content; 

	@Column(nullable = false, name="created")
	@Temporal(TIMESTAMP)
	private Date created;

	@Column(nullable = false, name="modified")
	@Temporal(TIMESTAMP)
	private Date modified;

	@OneToMany(mappedBy = "issue", fetch = LAZY)
	private Set<FixedVersionEntity> fixedVersions;

	@OneToMany(mappedBy = "issue", fetch = LAZY)
	private Set<AffectedVersionEntity> affectedVersions;

	@OneToMany(mappedBy = "issue", fetch = LAZY)
	private Set<ResponseEntity> responses;

	@ManyToOne(fetch = LAZY)	
	@JoinColumns({
		@JoinColumn(name="owner_id", referencedColumnName="owner_id",insertable=false,updatable=false),
		@JoinColumn(name="project_id", referencedColumnName="project_id",insertable=false,updatable=false)
	})	
	private ProjectEntity project;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="assignee", referencedColumnName="user_id",insertable=false,updatable=false)	
	private UserEntity assigneeUser;


	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="reporter", referencedColumnName="user_id",insertable=false,updatable=false)	
	private UserEntity reporterUser;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="priority_id", referencedColumnName="priority_id",insertable=false,updatable=false)	
	private PriorityEntity priority;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="status_id", referencedColumnName="status_id",insertable=false,updatable=false)	
	private StatusEntity status;

	@ManyToOne(fetch = LAZY)	
	@JoinColumn(name="issue_type_id", referencedColumnName="issue_type_id",insertable=false,updatable=false)	
	private IssueTypeEntity issueType;

	@Autowired
	@Transient
	private IssueJpaRepository issueRepository;

	public IssueEntity(){	
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Set<FixedVersionEntity> getFixedVersions() {
		return fixedVersions;
	}

	public void setFixedVersions(Set<FixedVersionEntity> fixedVersionEntities) {
		this.fixedVersions = fixedVersionEntities;
	}

	public Set<AffectedVersionEntity> getAffectedVersions() {
		return affectedVersions;
	}

	public void setAffectedVersions(Set<AffectedVersionEntity> affectedVersions) {
		this.affectedVersions = affectedVersions;
	}

	public Set<ResponseEntity> getResponses() {
		return responses;
	}

	public void setResponses(Set<ResponseEntity> responses) {
		this.responses = responses;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity projectEntity) {
		this.project = projectEntity;
	}

	public UserEntity getAssigneeUser() {
		return assigneeUser;
	}

	public void setAssigneeUser(UserEntity assigneeUser) {
		this.assigneeUser = assigneeUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserEntity getReporterUser() {
		return reporterUser;
	}

	public void setReporterUser(UserEntity reporterUser) {
		this.reporterUser = reporterUser;
	}

	public PriorityEntity getPriority() {
		return priority;
	}

	public void setPriority(PriorityEntity priorityEntity) {
		this.priority = priorityEntity;
	}

	public StatusEntity getStatus() {
		return status;
	}

	public void setStatus(StatusEntity statusEntity) {
		this.status = statusEntity;
	}

	public IssueTypeEntity getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueTypeEntity issueTypeEntity) {
		this.issueType = issueTypeEntity;
	}

	@Transactional(readOnly=true)
	public IssueEntity get(long issueId){		
		return issueRepository.findOne(issueId);		
	}

	@Transactional
	public IssueEntity modify(IssueEntity issueEntity){

		issueEntity.setModified(new Date());

		IssueEntity retrieved=get(issueEntity.getIssueId());

		issueEntity.setCreated(retrieved.getCreated());
		issueEntity.setOwnerId(retrieved.getOwnerId());
		issueEntity.setProjectId(retrieved.getProjectId());
		issueEntity.setReporter(retrieved.getReporter());

		checkAssignee(issueEntity);

		return issueRepository.save(issueEntity);	
	}

	@Transactional
	public IssueEntity add(IssueEntity issueEntity){
		Date timestamp=new Date();	

		issueEntity.setCreated(timestamp);
		issueEntity.setModified(timestamp);
		issueEntity.setStatusId("o");

		checkAssignee(issueEntity);

		return issueRepository.save(issueEntity);
	}

	private void checkAssignee(IssueEntity issueEntity) {
		try{
			if(issueEntity.getAssignee().isEmpty()){
				issueEntity.setAssignee(null);	
			}
		}catch(NullPointerException e){}
	}

	@Transactional
	public void delete(Long id){
		issueRepository.delete(id);
	}

	@Transactional(readOnly=true)
	public boolean exists(Long issueId){
		return issueRepository.exists(issueId);
	}

	@Transactional(readOnly=true)
	public Page<IssueEntity> getIssues(
			String statusId,
			String priorityId, 
			String issueTypeId,
			String reporter,
			String assignee,
			String ownerId, 
			String projectId,	
			Pageable pageable){

		if(statusId!=null && statusId.isEmpty())
			statusId=null;
		if(priorityId!=null && priorityId.isEmpty())
			priorityId=null;
		if(issueTypeId!=null && issueTypeId.isEmpty())
			issueTypeId=null;
		if(reporter!=null && reporter.isEmpty())
			reporter=null;
		if(assignee!=null && assignee.isEmpty())
			assignee=null;
		if(ownerId!=null && ownerId.isEmpty())
			ownerId=null;
		if(projectId!=null && projectId.isEmpty())
			projectId=null;			

		return issueRepository.findIssues(statusId, priorityId,
				issueTypeId, reporter, assignee, ownerId, projectId,
				pageable);
	}

	public boolean hasDependentObjects(ProjectPKEntity projectPKEntity){
		return false;
	}	
}
