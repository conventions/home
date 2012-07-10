/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.BaseMBean;
import com.jsf.conventions.qualifier.PersistentClass;
import com.jsf.conventions.qualifier.Service;
import com.jsf.conventions.showcase.model.Person;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */

@Named("simpleMBean")
@ViewAccessScoped
@PersistentClass(Person.class)//managed bean entity
@Service(name="statefulHibernateService",entity=Person.class)//same as commented setService method
public class SimpleMBean extends BaseMBean<Person> {
    
    
    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * or use @Service annotation to provide the name of an existing service @see PersonMBean
     * @param baseService
     */
//    @Inject 
//    public void setService(@StatefulService(entity=Person.class) BaseService service){
//        super.setBaseService(service);
//    }

    
}
