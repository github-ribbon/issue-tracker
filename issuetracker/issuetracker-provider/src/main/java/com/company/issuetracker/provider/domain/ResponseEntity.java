package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.ResponseJpaRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: ResponseEntity
 *
 */
@Entity
@Table(name="response")
//@IdClass(ResponsePKEntity.class)
@Cacheable(false)
//@Configurable
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class ResponseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id   
	@Column(name = "response_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_seq")
	@SequenceGenerator(name = "response_seq", allocationSize = 1, sequenceName = "response_seq")
	private long responseId;	

	@Column(nullable = false, name = "issue_id")
	private long issueId;

	@Column(nullable = false, name = "user_id", length = 20)
	private String userId;

	@Column(name = "content", nullable = false, length = 500)
	private String content;	

	@Column(nullable = false, name="created")
	@Temporal(TIMESTAMP)
	private Date created;

	@Column(nullable = false, name="modified")
	@Temporal(TIMESTAMP)
	private Date modified;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name="issue_id", referencedColumnName="issue_id",insertable=false,updatable=false)		
	private IssueEntity issue;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name="user_id", referencedColumnName="user_id",insertable=false,updatable=false)		
	private UserEntity user;

	@Autowired
	@Transient
	private ResponseJpaRepository responseRepository;

	public ResponseEntity(){
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public IssueEntity getIssue() {
		return issue;
	}

	public void setIssue(IssueEntity issueEntity) {
		this.issue = issueEntity;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity userEntity) {
		this.user = userEntity;
	}

	@Transactional(readOnly=true)
	public ResponseEntity get(Long id){
		return responseRepository.findOne(id);
	}

	@Transactional
	public ResponseEntity modify(ResponseEntity responseEntity){
		Date timestamp=new Date();

		ResponseEntity retrieved=get(responseEntity.getResponseId());

		responseEntity.setModified(timestamp);
		responseEntity.setIssueId(retrieved.getIssueId());
		responseEntity.setCreated(retrieved.getCreated());

		return responseRepository.save(responseEntity);	
	}

	@Transactional
	public ResponseEntity add(ResponseEntity responseEntity){
		Date timestamp=new Date();

		responseEntity.setCreated(timestamp);
		responseEntity.setModified(timestamp);

		return responseRepository.save(responseEntity);
	}

	@Transactional
	public void delete(Long id){
		responseRepository.delete(id);
	}

	@Transactional(readOnly=true)
	public Page<ResponseEntity> getResponses(String userId,Pageable pageable){
		return responseRepository.findByUserId(userId, pageable);		
	}
}
