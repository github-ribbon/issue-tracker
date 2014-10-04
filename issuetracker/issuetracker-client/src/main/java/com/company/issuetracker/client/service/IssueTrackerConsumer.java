package com.company.issuetracker.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.issuetracker.client.repository.AffectedVersionRestRepository;
import com.company.issuetracker.client.repository.FixedVersionRestRepository;
import com.company.issuetracker.client.repository.IssueRestRepository;
import com.company.issuetracker.client.repository.ProjectRestRepository;
import com.company.issuetracker.client.repository.ResponseRestRepository;
import com.company.issuetracker.client.repository.UserRestRepository;
import com.company.issuetracker.client.repository.VersionRestRepository;
import com.company.issuetracker.core.dto.AffectedVersionDTO;
import com.company.issuetracker.core.dto.FixedVersionDTO;
import com.company.issuetracker.core.dto.IssueDTO;
import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.core.dto.ResponseDTO;
import com.company.issuetracker.core.dto.UserDTO;
import com.company.issuetracker.core.dto.VersionDTO;

@Service
public class IssueTrackerConsumer {

	@Autowired
	private UserRestRepository userRepository;

	@Autowired
	private ProjectRestRepository projectRepository;

	@Autowired
	private ResponseRestRepository responseRepository;

	@Autowired
	private VersionRestRepository versionRepository;

	@Autowired
	private FixedVersionRestRepository fixedVersionRepository;

	@Autowired
	private AffectedVersionRestRepository affectedVersionRepository;

	@Autowired
	private IssueRestRepository issueRepository;

	//	UserEntity

	public UserDTO getUser(UserDTO userDTO){			
		return userRepository.find(userDTO);		
	}

	public void getAuthUser(UserDTO userDTO){			
		userRepository.findAuth(userDTO);		
	}

	public UserDTO registerUser(UserDTO userDTO){
		return userRepository.add(userDTO);
	}

	public UserDTO updateUser(UserDTO userDTO){
		return userRepository.update(userDTO);
	}

	public UserDTO getAllUsers(UserDTO userDTO){
		return userRepository.findAllUsers(userDTO);
	}


	//	ProjectEntity

	public ProjectDTO addProject(ProjectDTO projectDTO){
		return projectRepository.add(projectDTO);
	}

	public ProjectDTO updateProject(ProjectDTO projectDTO){
		return projectRepository.update(projectDTO);
	}

	public ProjectDTO deleteProject(ProjectDTO projectDTO){
		return projectRepository.delete(projectDTO);
	}

	public ProjectDTO getProject(ProjectDTO projectDTO){
		return projectRepository.findOne(projectDTO);
	}

	public ProjectDTO getProjects(ProjectDTO projectDTO){
		return projectRepository.findProjects(projectDTO);
	}
	
	public ProjectDTO getAllProjects(ProjectDTO projectDTO){
		return projectRepository.findAllProjects(projectDTO);
	}

	//	VersionEntity

	public VersionDTO addVersion(VersionDTO versionDTO){
		return versionRepository.add(versionDTO);		
	}

	public VersionDTO deleteVersion(VersionDTO versionDTO){
		return versionRepository.delete(versionDTO);
	}

	//	FixedVersionEntity

	public FixedVersionDTO addFixedVersion(FixedVersionDTO fixedVersionDTO){
		return fixedVersionRepository.add(fixedVersionDTO);
	}

	public FixedVersionDTO deleteFixedVersion(FixedVersionDTO fixedVersionDTO){
		return fixedVersionRepository.delete(fixedVersionDTO);
	}

	//	AffectedVersionEntity

	public AffectedVersionDTO addAffectedVersion(AffectedVersionDTO affectedVersionDTO){
		return affectedVersionRepository.add(affectedVersionDTO);
	}

	public AffectedVersionDTO deleteAffectedVersion(AffectedVersionDTO affectedVersionDTO){
		return affectedVersionRepository.delete(affectedVersionDTO);
	}

	//	ResponseEntity

	public ResponseDTO addResponse(ResponseDTO responseDTO){
		return responseRepository.add(responseDTO);
	}

	public ResponseDTO updateResponse(ResponseDTO responseDTO){
		return responseRepository.update(responseDTO);
	}

	public ResponseDTO deleteResponse(ResponseDTO responseDTO){	
		return responseRepository.delete(responseDTO);
	}

	public ResponseDTO getResponsesByUser(ResponseDTO responseDTO){
		return responseRepository.findByUser(responseDTO);
	}

	//	IssueEntity

	public IssueDTO getIssue(IssueDTO issueDTO){
		return issueRepository.findOne(issueDTO);
	}

	public IssueDTO getIssues(IssueDTO issueDTO){
		return issueRepository.findIssues(issueDTO);
	}

	public IssueDTO addIssue(IssueDTO issueDTO){
		return issueRepository.add(issueDTO);
	}

	public IssueDTO modifyIssue(IssueDTO issueDTO){
		return issueRepository.update(issueDTO);
	}

	public IssueDTO deleteIssue(IssueDTO issueDTO){
		return issueRepository.delete(issueDTO);
	}
}

