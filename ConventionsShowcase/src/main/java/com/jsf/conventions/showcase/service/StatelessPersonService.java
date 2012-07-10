package com.jsf.conventions.showcase.service;

import com.jsf.conventions.service.BaseService;
import com.jsf.conventions.showcase.model.Person;
import java.util.List;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:33:35 PM
 */
public interface StatelessPersonService extends BaseService<Person, Long>{

    boolean alowDeletePerson(Person entity);

    public List<Person> findFriends(Long id);
}
