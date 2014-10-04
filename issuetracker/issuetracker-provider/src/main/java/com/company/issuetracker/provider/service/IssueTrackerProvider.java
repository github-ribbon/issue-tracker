package com.company.issuetracker.provider.service;

import java.security.Principal;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.company.issuetracker.core.domain.AffectedVersion;
import com.company.issuetracker.core.domain.FixedVersion;
import com.company.issuetracker.core.domain.Issue;
import com.company.issuetracker.core.domain.Project;
import com.company.issuetracker.core.domain.Response;
import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.domain.Version;
import com.company.issuetracker.core.dto.AffectedVersionDTO;
import com.company.issuetracker.core.dto.FixedVersionDTO;
import com.company.issuetracker.core.dto.IssueDTO;
import com.company.issuetracker.core.dto.Pager;
import com.company.issuetracker.core.dto.ProjectDTO;
import com.company.issuetracker.core.dto.ResponseDTO;
import com.company.issuetracker.core.dto.UserDTO;
import com.company.issuetracker.core.dto.VersionDTO;
import com.company.issuetracker.core.util.Wrapper;
import com.company.issuetracker.provider.domain.AffectedVersionEntity;
import com.company.issuetracker.provider.domain.AffectedVersionPKEntity;
import com.company.issuetracker.provider.domain.FixedVersionEntity;
import com.company.issuetracker.provider.domain.FixedVersionPKEntity;
import com.company.issuetracker.provider.domain.IssueEntity;
import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.domain.ResponseEntity;
import com.company.issuetracker.provider.domain.UserEntity;
import com.company.issuetracker.provider.domain.UserPKEntity;
import com.company.issuetracker.provider.domain.VersionEntity;
import com.company.issuetracker.provider.domain.VersionPKEntity;

@Service
public class IssueTrackerProvider {

	@Autowired
	private DozerBeanMapper mapper;

	@Autowired
	private UserEntity user;

	@Autowired
	private ProjectEntity project;

	@Autowired
	private VersionEntity version;

	@Autowired
	private FixedVersionEntity fixedVersion;

	@Autowired
	private AffectedVersionEntity affectedVersion;

	@Autowired
	private ResponseEntity response;

	@Autowired
	private IssueEntity issue;


	//	UserEntity

	public void getUser(UserDTO userDTO){			
		userDTO.setUser(
				mapper.map(
						user.get(
								mapper.map(
										userDTO.getUser().getId(),
										UserPKEntity.class
										)
								),
								User.class
						)
				);		
	}

	public void registerUser(UserDTO userDTO){		
		userDTO.setUser(
				mapper.map(
						user.save(
								mapper.map(
										userDTO.getUser(),
										UserEntity.class
										)
								),
								User.class
						)
				);
	}

	public void updateUser(UserDTO userDTO){
		userDTO.setUser(
				mapper.map(
						user.save(
								mapper.map(
										userDTO.getUser(),
										UserEntity.class
										)
								),
								User.class
						)
				);
	}

	@SuppressWarnings("unchecked")
	public void getAllUsers(UserDTO userDTO){
		//		Page<UserEntity> page=user.getUsers(userDTO.getPageable());

		Wrapper<UserEntity> wrapper=new Wrapper<UserEntity>();
		wrapper.setContent(user.getAllUsers());

		userDTO.setUsersWrapper(mapper.map(wrapper, Wrapper.class));		
	}

	/*
	public void getUsers(UserDTO userDTO){
		userDTO.setUsers(user.getUsers(userDTO.getPageable()));
	}

	 */

	//	ProjectEntity

	@SuppressWarnings("unchecked")
	public void getAllProjects(ProjectDTO projectDTO){
		Wrapper<ProjectEntity> wrapper=new Wrapper<ProjectEntity>();
		wrapper.setContent(project.getAllProjects());

		projectDTO.setProjectsWrapper(mapper.map(wrapper, Wrapper.class));		
	}

	public void addProject(ProjectDTO projectDTO){		
		projectDTO.setProject(
				mapper.map(
						project.save(
								mapper.map(
										projectDTO.getProject(),
										ProjectEntity.class
										)
								),
								Project.class
						)
				);
	}

	public void updateProject(ProjectDTO projectDTO){		
		projectDTO.setProject(
				mapper.map(
						project.save(
								mapper.map(
										projectDTO.getProject(),
										ProjectEntity.class
										)
								), 
								Project.class
						)
				);
	}

	public void deleteProject(ProjectDTO projectDTO){		
		project.delete(
				mapper.map(
						projectDTO.getProject().getId(),
						ProjectPKEntity.class
						)
				);
	}

	public void getProject(ProjectDTO projectDTO){

		ProjectEntity entity=project.get(
				mapper.map(
						projectDTO.getProject().getId(),
						ProjectPKEntity.class
						)
				);

		if(entity!=null){
			projectDTO.setProject(
					mapper.map(
							entity,
							Project.class
							)
					);


			projectDTO.setNumOfIssues(
					project.getNumOfIssues(
							mapper.map(
									projectDTO.getProject().getId(), ProjectPKEntity.class)));
		}else{
			projectDTO.setProject(null);
		}
	}

	@SuppressWarnings("unchecked")
	public void getProjects(ProjectDTO projectDTO){

		Page<ProjectEntity> page=project
				.getProjects(projectDTO.getOwnerId(), projectDTO.getPager().getPageable());		


		projectDTO.setProjectsWrapper(
				mapper.map(
						new Wrapper<ProjectEntity>(page),
						Wrapper.class
						)			
				);		

		projectDTO.setPager(new Pager(page.getNumber(), page.getSize(), 
				page.getTotalPages(), page.getTotalElements(), page.getSort()));	
	}

	//	VersionEntity

	public void addVersion(VersionDTO versionDTO){
		versionDTO.setVersion(
				mapper.map(
						version.save(
								mapper.map(
										versionDTO.getVersion(), 
										VersionEntity.class
										)
								),
								Version.class
						)
				);		
	}

	public void deleteVersion(VersionDTO versionDTO){
		version.delete(
				mapper.map(
						versionDTO.getVersion().getId(), VersionPKEntity.class));
	}

	//	FixedVersionEntity

	public void addFixedVersion(FixedVersionDTO fixedVersionDTO){
		fixedVersionDTO.setFixedVersion(
				mapper.map(
						fixedVersion.save(
								mapper.map(
										fixedVersionDTO.getFixedVersion(),
										FixedVersionEntity.class
										)
								),
								FixedVersion.class
						)
				);
	}

	public void deleteFixedVersion(FixedVersionDTO fixedVersionDTO){
		fixedVersion.delete(
				mapper.map(
						fixedVersionDTO.getFixedVersion().getId(), FixedVersionPKEntity.class));
	}

	//	AffectedVersionEntity

	public void addAffectedVersion(AffectedVersionDTO affectedVersionDTO){
		affectedVersionDTO.setAffectedVersion(
				mapper.map(
						affectedVersion.save(
								mapper.map(
										affectedVersionDTO.getAffectedVersion(), AffectedVersionEntity.class
										)
								),
								AffectedVersion.class
						)
				);
	}

	public void deleteAffectedVersion(AffectedVersionDTO affectedVersionDTO){
		affectedVersion.delete(
				mapper.map(
						affectedVersionDTO.getAffectedVersion().getId(), AffectedVersionPKEntity.class)	);
	}

	//	ResponseEntity

	public void addResponse(ResponseDTO responseDTO){
		responseDTO.setResponse(
				mapper.map(
						response.add(
								mapper.map(
										responseDTO.getResponse(),
										ResponseEntity.class
										)
								),
								Response.class
						)
				);		
	}

	public void updateResponse(ResponseDTO responseDTO){
		responseDTO.setResponse(
				mapper.map(
						response.modify(
								mapper.map(
										responseDTO.getResponse(),
										ResponseEntity.class
										)
								),
								Response.class
						)
				);
	}

	public void deleteResponse(ResponseDTO responseDTO){		
		response.delete(responseDTO.getResponse().getResponseId());
	}

	public void getResponsesByUser(ResponseDTO responseDTO){
		//
	}

	//	IssueEntity

	@SuppressWarnings("unchecked")
	public void getIssue(IssueDTO issueDTO, Principal principal){

		IssueEntity issueEntity=issue.get(issueDTO.getIssueId());

		if(issueEntity!=null){
			
			issueDTO.setIssue(
					mapper.map(
							issueEntity,				
							Issue.class
							)
					);

			if(principal.getName().equals(issueEntity.getOwnerId())){
				issueDTO.setNotFixedVersionsWrapper(
						mapper.map(
								new Wrapper<VersionEntity>(version.getNotFixed(
										issueEntity.getOwnerId(), issueEntity.getProjectId(), 
										issueEntity.getIssueId())
										), 
										Wrapper.class
								)
						);

				issueDTO.setNotAffectedVersionsWrapper(
						mapper.map(
								new Wrapper<VersionEntity>(version.getNotAffected(
										issueEntity.getOwnerId(), issueEntity.getProjectId(), 
										issueEntity.getIssueId())
										), 
										Wrapper.class
								)
						);	
			}	

		}else{
			issueDTO.setIssue(null);
		}
	}

	public void addIssue(IssueDTO issueDTO){
		issueDTO.setIssue(
				mapper.map(
						issue.add(
								mapper.map(
										issueDTO.getIssue(),
										IssueEntity.class
										)
								),
								Issue.class
						)
				);
	}

	public void modifyIssue(IssueDTO issueDTO){
		issueDTO.setIssue(
				mapper.map(
						issue.modify(
								mapper.map(
										issueDTO.getIssue(),
										IssueEntity.class
										)
								),
								Issue.class
						)
				);
	}

	public void deleteIssue(IssueDTO issueDTO){
		issue.delete(issueDTO.getIssue().getIssueId());
	}

	@SuppressWarnings("unchecked")
	public void getIssues(IssueDTO issueDTO){		 

		Page<IssueEntity> page=issue.getIssues(
				issueDTO.getIssue().getStatusId(),
				issueDTO.getIssue().getPriorityId(),
				issueDTO.getIssue().getIssueTypeId(),
				issueDTO.getIssue().getReporter(),
				issueDTO.getIssue().getAssignee(),
				issueDTO.getIssue().getOwnerId(),
				issueDTO.getIssue().getProjectId(),
				issueDTO.getPager().getPageable());			

		issueDTO.setIssuesWrapper(
				mapper.map(
						new Wrapper<IssueEntity>(page),
						Wrapper.class
						)			
				);		

		issueDTO.setPager(new Pager(page.getNumber(), page.getSize(), 
				page.getTotalPages(), page.getTotalElements(), page.getSort()));	
	}
}
