package com.company.issuetracker.provider.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.cfg.ReadOnlyJpaTestConfig;
import com.company.issuetracker.provider.domain.ProjectEntity;
import com.company.issuetracker.provider.domain.ProjectPKEntity;
import com.company.issuetracker.provider.util.ProjectTestUtils;

/**
 * Integration tests for the ProjectJpaRepository.class
 * 
 * @author Andrzej Stążecki
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReadOnlyJpaTestConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional(readOnly=true)
@TransactionConfiguration
public class ProjectJpaRepositoryIT {

	@Autowired
	private ProjectJpaRepository projectRepository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByOwnerId() {
		PageRequest pageable=new PageRequest(0, 2);	
		Page<ProjectEntity> page1=projectRepository.findProjects("user1", pageable);

		pageable=new PageRequest(1, 2);
		Page<ProjectEntity> page2=projectRepository.findProjects("user1", pageable);

		assertTrue(page1.getTotalElements()==3);
		assertTrue(page2.getTotalElements()==3);

		assertTrue(
				Arrays.asList(
						new ProjectPKEntity("user1","project1"),
						new ProjectPKEntity("user1","project2")).equals(							
								ProjectTestUtils.getPrimaryKeysAsList(page1.getContent())));

		assertTrue(
				Arrays.asList(
						new ProjectPKEntity("user1","project3")).equals(							
								ProjectTestUtils.getPrimaryKeysAsList(page2.getContent())));

	}

	@Test
	public void testGetNumOfIssues(){
		final int ROWS=3;		
		assertEquals(ROWS,projectRepository.getNumOfIssues("user1", "project1"));		
	}

	@Test
	public void testFindWithVersions(){
		ProjectEntity projectEntity=projectRepository.findWithVersions(new ProjectPKEntity("user1","project2"));
		assertNotNull(projectEntity);

		final int ROWS=3;
		assertEquals(ROWS,projectEntity.getVersions().size());
	}
}
