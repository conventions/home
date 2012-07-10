/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.qualifier.PersistentClass;
import com.jsf.conventions.qualifier.Service;
import com.jsf.conventions.qualifier.StatelessService;
import com.jsf.conventions.service.BaseService;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.util.Paginator;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */
@Named
@ViewAccessScoped
public class PaginatorMBean implements Serializable{
    
//    injection of paginator is also supported, you can inform the service at injection point
//    so you dont need to instantiate the Paginator and pass the service in the contructor like in initPaginator method    
//    @Inject @Service(name="personService") //uses the PersonServiceImpl as service, just uncomment 
//    @Inject @Service(name="statelessHibernateService",entity=Person.class) //also works, uses the BaseService as service, just uncomment 
    private Paginator paginator;

    
    @Inject @StatelessService(entity=Person.class)
    private BaseService baseService;
    
    
    public void initPaginator(){
        // paginator need a service to access DB and perform
        // true pagination, sort and filtering over a collection
        paginator = new Paginator(baseService);
    }
    

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Paginator getPaginator() {
        return paginator;
    }
    
}
