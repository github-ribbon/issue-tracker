package com.company.issuetracker.provider.domain;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.repository.UserJpaRepository;
import com.company.issuetracker.provider.util.HashCodeUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * Entity implementation class for Entity: UserEntity
 *
 */
//@Configurable
@Entity
@Table(name = "usr")
@Cacheable(false)
@Component
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId	
	private UserPKEntity id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
	private String surname;

	@Column(nullable = false, length = 200)
	private String password;

	@OneToMany(mappedBy = "owner", fetch = LAZY)
	private Set<ProjectEntity> projects;

	@OneToMany(mappedBy = "user", fetch = LAZY)
	private Set<ResponseEntity> responses;

	@OneToMany(mappedBy = "assigneeUser", fetch = LAZY)
	private Set<IssueEntity> assigneeIssues;

	@OneToMany(mappedBy = "reporterUser", fetch = LAZY)
	private Set<IssueEntity> reporterIssues;

	@Autowired
	@Transient
	private UserJpaRepository userRepository;

	@Autowired
	@Transient	
	private BCryptPasswordEncoder passwordEncoder;

	public UserEntity(){
	}

	public UserEntity(UserPKEntity id){
		this.id=id;
	}

	public UserPKEntity getId() {
		return id;
	}

	public void setId(UserPKEntity id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ProjectEntity> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectEntity> projectEntities) {
		this.projects = projectEntities;
	}

	public Set<ResponseEntity> getResponses() {
		return responses;
	}

	public void setResponses(Set<ResponseEntity> responses){
		this.responses = responses;
	}

	public Set<IssueEntity> getAssigneeIssues() {
		return assigneeIssues;
	}

	public void setAssigneeIssues(Set<IssueEntity> assigneeIssues) {
		this.assigneeIssues = assigneeIssues;
	}

	public Set<IssueEntity> getReporterIssues() {
		return reporterIssues;
	}

	public void setReporterIssues(Set<IssueEntity> reporterIssues) {
		this.reporterIssues = reporterIssues;
	}

	@Transactional
	public UserEntity save(UserEntity userEntity){
		userEntity.setPassword(HashCodeUtil.getHashPassword(userEntity.getPassword()));
		return userRepository.save(userEntity);
	}

	@Transactional(readOnly=true)
	public UserEntity get(UserPKEntity userPKEntity){
		//		return userRepository.findUser();
		return userRepository.findOne(userPKEntity);
	}

	@Transactional(readOnly=true)
	public boolean exists(UserPKEntity userPKEntity){
		return userRepository.exists(userPKEntity);
	}

	@Transactional(readOnly=true)
	@JsonIgnore
	public List<UserEntity> getAllUsers(){
		return userRepository.findAll();
	}
}
