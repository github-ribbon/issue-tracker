package com.company.issuetracker.provider.util;

import static junit.framework.TestCase.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


/**
 * Using meta data to check correct database schema.
 * 
 * @author Andrzej Stążecki
 *
 */
public class JpaAssertions {

	//	private static final Logger logger = LoggerFactory.getLogger(JpaAssertions.class);

	/**
	 * Checks whether table has column.
	 *  
	 * @param manager
	 * @param tableName
	 * @param columnName
	 */
	public static void assertTableHasColumn(EntityManager manager, final String tableName, 
			final String columnName) {
		SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

		final Finder finder=new Finder();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ResultSet columns = connection.getMetaData().getColumns(null, null, tableName.toLowerCase(), null);

				while(columns.next()) {
					if (columns.getString(4).toLowerCase().equals(columnName.toLowerCase())) {
						finder.setFound(true);
					}
				}			
			}
		});

		if (!finder.isFound()) {
			fail("Column [" + columnName + "] not found on table : " + tableName);
		}
	}

	/**
	 * Checks whether table exists.
	 * 
	 * @param manager
	 * @param name
	 */
	public static void assertTableExists(EntityManager manager, final String name) {
		SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

		final Finder finder=new Finder();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ResultSet tables = connection.getMetaData().getTables(null, null, "%", null);
				while(tables.next()) {
					if (tables.getString(3).toUpperCase().equals(name.toUpperCase())) {
						finder.setFound(true);
					}
				}
			}
		});

		if (!finder.isFound()) {
			fail("Table ["+name+"] not found");
		}
	}

	/**
	 * Checks whether column has correct definition (data type, allowed null values).
	 * 
	 * @param manager
	 * @param tableName
	 * @param columnName
	 * @param nullable
	 * @param dataType
	 */
	public static void assertColumnDef(EntityManager manager, final String tableName, 
			final String columnName,final int nullable, final int dataType) {
		SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

		final Finder finder=new Finder();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ResultSet resultSet = connection.getMetaData().
						getColumns(null, null, tableName, columnName);

				while(resultSet.next()) {
					if(resultSet.getString(3).equals(tableName)
							&&resultSet.getString(4).equals(columnName)
							&&resultSet.getInt(5)==dataType
							&&resultSet.getInt(11)==nullable){
						finder.setFound(true);
					}				
				}
			}
		});

		if (!finder.isFound()) {
			fail("Incorrect definition for column [" + tableName+"."+columnName + "]");
		}
	}	

	/**
	 * Checks whether foreign key exists.
	 * 
	 * @param manager
	 * @param tableName
	 * @param parentTable
	 * @param updateRule
	 * @param deleteRule
	 * @param parentPrimaryKeyColumns
	 * @param foreignKeyColumns
	 */
	public static void assertForeignKey(EntityManager manager,
			final String tableName, final String parentTable,
			final int updateRule, final int deleteRule,
			final Set<String> parentPrimaryKeyColumns,
			final Set<String> foreignKeyColumns) {
		SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

		final Finder finder=new Finder();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ResultSet resultSet = connection.getMetaData().getCrossReference(null, null, parentTable, null, null, tableName);

				while(resultSet.next()) {

					if(resultSet.getString(3).equals(parentTable)
							&&parentPrimaryKeyColumns.contains(resultSet.getString(4))
							&&foreignKeyColumns.contains(resultSet.getString(8))
							&&resultSet.getString(7).equals(tableName)							
							&&resultSet.getInt(10)==updateRule
							&&resultSet.getInt(11)==deleteRule){
						finder.setFound(true);
					}else{
						finder.setFound(false);
						break;
					}

				}
			}
		});

		if (!finder.isFound()) {
			fail("Foreign key not found. Foreign key column(s): "
					+foreignKeyColumns.toString()+". Parent primary key column(s): "+parentPrimaryKeyColumns);
		}
	}	

	/**
	 * Checks whether primary key exists.
	 * 
	 * @param manager
	 * @param tableName
	 * @param primaryKeyColumns
	 */
	public static void assertPrimaryKey(EntityManager manager,
			final String tableName,
			final Set<String> primaryKeyColumns) {
		SessionImpl session = (SessionImpl) manager.unwrap(Session.class);

		final Finder finder=new Finder();

		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);

				while(resultSet.next()) {
					if(resultSet.getString(3).equals(tableName)
							&&primaryKeyColumns.contains(resultSet.getString(4))){
						finder.setFound(true);
					}else{
						finder.setFound(false);
						break;
					}
				}
			}
		});

		if (!finder.isFound()) {
			fail("Primary key not found. Primary key column(s): "+primaryKeyColumns);
		}
	}

	private static class Finder{
		private boolean found = false;

		public boolean isFound() {
			return found;
		}

		public void setFound(boolean isFound) {
			this.found = isFound;
		}		
	}
}
