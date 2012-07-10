/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.qualifier.PersistentClass;
import com.jsf.conventions.qualifier.StatelessService;
import com.jsf.conventions.service.BaseService;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.util.BeanManagerController;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael M. Pestano May 13, 2011 10:55:39 PM
 */
@Named(value="databaseHelper")
@SessionScoped
public class DatabaseHelper implements Serializable{
    
    @Inject @StatelessService(entity=Person.class)
    private BaseService hibernatePersonService;
    private boolean aplicattionInitialized;
    
    
    public void initDatabase(){
         /* populate database */
        try{
            
        if (hibernatePersonService.countAll() == 0) {
            for (int i = 0; i < 1000; i++) {
                Person p = new Person("Person " + i, "Lastname " + i, i % 100);
                hibernatePersonService.save(p);
            }
        }
        AccessCountMBean countBean = (AccessCountMBean) BeanManagerController.getBeanByName("accessCountMBean");
        countBean.increment();
        }finally{
             aplicattionInitialized = true;
        }
    }

    
    
    public boolean isAplicattionInitialized() {
        return aplicattionInitialized;
    }

    public void setAplicattionInitialized(boolean aplicattionInitialized) {
        this.aplicattionInitialized = aplicattionInitialized;
    }
    
}
