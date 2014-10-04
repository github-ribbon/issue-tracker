package com.company.issuetracker.provider.cfg;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;

/**
 * DozerCustomFieldMapper prevents from the org.hibernate.LazyInitializationException 
 * 
 * @author Andrzej Stążecki
 *
 */
public class DozerCustomFieldMapper implements CustomFieldMapper {
	public boolean mapField(Object source, Object destination, Object sourceFieldValue, 
			ClassMap classMap, FieldMap fieldMapping) {       
		return !Hibernate.isInitialized(sourceFieldValue);
	}   
}