package com.company.issuetracker.provider.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;


public class ProjectTestUtils {	
	
	public static Set<ProjectPKEntity> createSet(ProjectPKEntity...buildingPKs){
		
		return new HashSet<ProjectPKEntity>(				
				Arrays.asList(buildingPKs));
	}
	
	public static List<ProjectPKEntity> getPrimaryKeysAsList(List<ProjectEntity> projectEntities){
	
		Iterator<ProjectEntity> iterator=projectEntities.iterator();
		
		List<ProjectPKEntity> list=new ArrayList<ProjectPKEntity>();
		
		while(iterator.hasNext()){
			list.add(iterator.next().getId());
		}
		
		return list;
	}
	
	public static Set<ProjectPKEntity> getPrimaryKeysAsSet(List<ProjectEntity> projectEntities){
				
		Iterator<ProjectEntity> iterator=projectEntities.iterator();
		
		Set<ProjectPKEntity> set=new HashSet<ProjectPKEntity>();
		
		while(iterator.hasNext()){
			set.add(iterator.next().getId());
		}
		
		return set;
	}
}
