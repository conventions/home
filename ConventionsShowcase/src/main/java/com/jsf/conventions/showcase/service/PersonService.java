package com.jsf.conventions.showcase.service;

import com.jsf.conventions.service.BaseService;
import com.jsf.conventions.showcase.model.Person;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:33:35 PM
 */
public interface PersonService extends BaseService<Person, Long>{

     boolean alowDeletePerson(Person entity);
}
