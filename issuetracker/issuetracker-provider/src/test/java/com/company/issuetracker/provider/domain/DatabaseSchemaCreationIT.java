package com.company.issuetracker.provider.domain;

import static com.company.issuetracker.provider.util.JpaAssertions.assertColumnDef;
import static com.company.issuetracker.provider.util.JpaAssertions.assertForeignKey;
import static com.company.issuetracker.provider.util.JpaAssertions.assertPrimaryKey;
import static com.company.issuetracker.provider.util.JpaAssertions.assertTableExists;
import static com.company.issuetracker.provider.util.JpaAssertions.assertTableHasColumn;

import java.sql.DatabaseMetaData;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.company.issuetracker.provider.cfg.JpaTestConfig;

/**
 * Integration tests for verifying database schema creation.
 * 
 * @author Andrzej Stążecki
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaTestConfig.class})
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DatabaseSchemaCreationIT {

	@PersistenceContext
	private EntityManager entityManager;

	public static Set<String> columns(String...values){

		Set<String> set=new HashSet<String>();

		for(int i=0;i<values.length;i++){
			set.add(values[i]);
		}

		return set;		
	}

	@Test
	public void userTableMappingTest()  {

		//		UserEntity

		assertTableExists(entityManager, "usr");

		assertTableHasColumn(entityManager, "usr", "user_id");
		assertTableHasColumn(entityManager, "usr", "name");
		assertTableHasColumn(entityManager, "usr", "surname");
		assertTableHasColumn(entityManager, "usr", "password");

		assertColumnDef(entityManager, "usr", "user_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "usr", "name", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "usr", "surname", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "usr", "password", DatabaseMetaData.columnNoNulls, Types.VARCHAR);		

		assertPrimaryKey(entityManager, "usr", columns("user_id"));
		//		
	}

	@Test
	public void issueTableMappingTest()  {

		//		IssueEntity

		assertTableExists(entityManager, "issue");

		assertTableHasColumn(entityManager, "issue", "issue_id");
		assertTableHasColumn(entityManager, "issue", "owner_id");
		assertTableHasColumn(entityManager, "issue", "project_id");
		assertTableHasColumn(entityManager, "issue", "reporter");
		assertTableHasColumn(entityManager, "issue", "assignee");
		assertTableHasColumn(entityManager, "issue", "status_id");
		assertTableHasColumn(entityManager, "issue", "priority_id");
		assertTableHasColumn(entityManager, "issue", "issue_type_id");
		assertTableHasColumn(entityManager, "issue", "content");
		assertTableHasColumn(entityManager, "issue", "created");
		assertTableHasColumn(entityManager, "issue", "modified");
		assertTableHasColumn(entityManager, "issue", "title");

		assertColumnDef(entityManager, "issue", "issue_id", DatabaseMetaData.columnNoNulls, Types.BIGINT);
		assertColumnDef(entityManager, "issue", "owner_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "project_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "reporter", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "assignee", DatabaseMetaData.columnNullable, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "status_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "priority_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "issue_type_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "content", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "issue", "created", DatabaseMetaData.columnNoNulls, Types.TIMESTAMP);
		assertColumnDef(entityManager, "issue", "modified", DatabaseMetaData.columnNoNulls, Types.TIMESTAMP);
		assertColumnDef(entityManager, "issue", "title", DatabaseMetaData.columnNoNulls, Types.VARCHAR);

		assertPrimaryKey(entityManager, "issue", columns("issue_id"));		

		assertForeignKey(entityManager, "issue", "project", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("project_id","owner_id"),
				columns("project_id","owner_id"));


		assertForeignKey(entityManager, "issue", "usr", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("user_id"),
				columns("assignee","reporter"));

		assertForeignKey(entityManager, "issue", "priority", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("priority_id"),
				columns("priority_id"));

		assertForeignKey(entityManager, "issue", "status", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("status_id"),
				columns("status_id"));

		assertForeignKey(entityManager, "issue", "issue_type", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("issue_type_id"),
				columns("issue_type_id"));
		//
	}	

	@Test
	public void issueTypeTableMappingTest()  {

		//		IssueTypeEntity

		assertTableExists(entityManager, "issue_type");		
		assertTableHasColumn(entityManager, "issue_type", "issue_type_id");				
		assertColumnDef(entityManager, "issue_type", "issue_type_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);		
		assertPrimaryKey(entityManager, "issue_type", columns("issue_type_id"));
		//	
	}

	@Test
	public void priorityTableMappingTest()  {

		//		PriorityEntity

		assertTableExists(entityManager, "priority");		
		assertTableHasColumn(entityManager, "priority", "priority_id");				
		assertColumnDef(entityManager, "priority", "priority_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);		
		assertPrimaryKey(entityManager, "priority", columns("priority_id"));
		//	
	}

	@Test
	public void statusTableMappingTest()  {

		//		StatusEntity

		assertTableExists(entityManager, "status");		
		assertTableHasColumn(entityManager, "status", "status_id");				
		assertColumnDef(entityManager, "status", "status_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);		
		assertPrimaryKey(entityManager, "status", columns("status_id"));
		//	
	}

	@Test
	public void projectTableMappingTest()  {

		//		ProjectEntity

		assertTableExists(entityManager, "project");

		assertTableHasColumn(entityManager, "project", "owner_id");
		assertTableHasColumn(entityManager, "project", "project_id");
		assertTableHasColumn(entityManager, "project", "description");

		assertColumnDef(entityManager, "project", "project_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "project", "owner_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "project", "description", DatabaseMetaData.columnNullable, Types.VARCHAR);

		assertPrimaryKey(entityManager, "project", columns("owner_id","project_id"));

		assertForeignKey(entityManager, "project", "usr", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("user_id"),
				columns("owner_id"));
		//		

	}

	@Test
	public void responseTableMappingTest()  {

		//		ResponseEntity

		assertTableExists(entityManager, "response");

		assertTableHasColumn(entityManager, "response", "response_id");
		assertTableHasColumn(entityManager, "response", "issue_id");
		assertTableHasColumn(entityManager, "response", "user_id");
		assertTableHasColumn(entityManager, "response", "content");
		assertTableHasColumn(entityManager, "response", "created");
		assertTableHasColumn(entityManager, "response", "modified");						

		assertColumnDef(entityManager, "response", "response_id", DatabaseMetaData.columnNoNulls, Types.BIGINT);
		assertColumnDef(entityManager, "response", "issue_id", DatabaseMetaData.columnNoNulls, Types.BIGINT);
		assertColumnDef(entityManager, "response", "user_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "response", "content", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "response", "created", DatabaseMetaData.columnNoNulls, Types.TIMESTAMP);
		assertColumnDef(entityManager, "response", "modified", DatabaseMetaData.columnNoNulls, Types.TIMESTAMP);

		assertPrimaryKey(entityManager, "response", columns("response_id"));

		assertForeignKey(entityManager, "response", "usr", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("user_id"),
				columns("user_id"));

		assertForeignKey(entityManager, "response", "issue", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("issue_id"),
				columns("issue_id"));

		//	
	}

	@Test
	public void versionTableMappingTest()  {

		//		VersionEntity		

		assertTableExists(entityManager, "version");

		assertTableHasColumn(entityManager,"version", "owner_id");
		assertTableHasColumn(entityManager,"version", "project_id");
		assertTableHasColumn(entityManager,"version", "version_id");

		assertColumnDef(entityManager, "version", "owner_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "version", "project_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "version", "version_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);

		assertPrimaryKey(entityManager, "version", columns("owner_id","project_id","version_id"));

		assertForeignKey(entityManager, "version", "project", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("owner_id","project_id"),
				columns("owner_id","project_id"));

		assertForeignKey(entityManager, "version", "project", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("owner_id","project_id"),
				columns("owner_id","project_id"));

		//		
	}

	@Test
	public void fixedVersionTableMappingTest()  {

		//		FixedVersionEntity

		assertTableExists(entityManager, "fixed_version");

		assertTableHasColumn(entityManager,"fixed_version", "owner_id");
		assertTableHasColumn(entityManager,"fixed_version", "project_id");
		assertTableHasColumn(entityManager,"fixed_version", "version_id");
		assertTableHasColumn(entityManager,"fixed_version", "issue_id");

		assertColumnDef(entityManager, "fixed_version", "owner_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "fixed_version", "project_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "fixed_version", "version_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "fixed_version", "issue_id", DatabaseMetaData.columnNoNulls, Types.BIGINT);

		assertPrimaryKey(entityManager, "fixed_version", 
				columns("owner_id","project_id","version_id","issue_id"));

		assertForeignKey(entityManager, "fixed_version", "version", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("owner_id","project_id","version_id"),
				columns("owner_id","project_id","version_id"));

		assertForeignKey(entityManager, "fixed_version", "issue", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("issue_id"),
				columns("issue_id"));
		//		
	}	

	@Test
	public void affectedVersionTableMappingTest()  {

		//		AffectedVersionEntity

		assertTableExists(entityManager, "affected_version");

		assertTableHasColumn(entityManager,"affected_version", "owner_id");
		assertTableHasColumn(entityManager,"affected_version", "project_id");
		assertTableHasColumn(entityManager,"affected_version", "version_id");
		assertTableHasColumn(entityManager,"affected_version", "issue_id");

		assertColumnDef(entityManager, "affected_version", "owner_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "affected_version", "project_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "affected_version", "version_id", DatabaseMetaData.columnNoNulls, Types.VARCHAR);
		assertColumnDef(entityManager, "affected_version", "issue_id", DatabaseMetaData.columnNoNulls, Types.BIGINT);

		assertPrimaryKey(entityManager, "affected_version", 
				columns("owner_id","project_id","version_id","issue_id"));

		assertForeignKey(entityManager, "affected_version", "version", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("owner_id","project_id","version_id"),
				columns("owner_id","project_id","version_id"));

		assertForeignKey(entityManager, "affected_version", "issue", DatabaseMetaData.importedKeyNoAction, 
				DatabaseMetaData.importedKeyNoAction, 
				columns("issue_id"),
				columns("issue_id"));
		//		
	}	
}

